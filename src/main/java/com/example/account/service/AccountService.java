package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.account.type.AccountStatus.IN_USE;
import static com.example.account.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    /**
     * //@어노테이션, 메소드명, 파라미터 먼저 체크
     * // 사용자가 있는지 조회하고
     * // 계좌의 번호를 생성하고
     * // 계좌를 저장하고, 그 정보를 넘긴다.
     *
     */

    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) {
        // user를 찾고
        AccountUser accountUser = getAccountUser(userId);
        // userId가 없으면 예외로 처리

        // user가 있는걸 확인했다면
        // account를 생성할 계좌번호 받아오자 -> 현재까지 존재하는 계좌번호의 가장 마지막번호 +1 해서 생성하게
        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc()
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "")
                .orElse("1000000000"); // 기존에 생성된 계좌가 없을 경우

        //validation 코드들은 별도의 프라임 메소드로 추출해놔요
        validateCreateAccount(accountUser);

        // 빌더를 통한 계좌 생성
        return AccountDto.fromEntity(
                accountRepository.save(
                        Account.builder()
                                .accountUser(accountUser)
                                .accountStatus(IN_USE)
                                .accountNumber(newAccountNumber)
                                .balance(initialBalance)
                                .registeredAt(LocalDateTime.now())
                                .build()));
    }

    private AccountUser getAccountUser(Long userId) {
        AccountUser accountUser = accountUserRepository.findById(userId)
                .orElseThrow(() -> new AccountException(USER_NOT_FOUND));
        return accountUser;
    }

    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(MAX_ACCOUNT_PER_USER_10);
        }
    }


    /*
    생성된 계좌를 다시 컨트롤러로 넘겨주는 작업을 한건데, Entity 클래스의 경우 일반적인 클래스와
    성격이 다르기 때문에, 레이어 간에 주고 받고 하는 과정에서 entity에서 rage? range? 로딩하거나
    추가적인 쿼리를 날릴려고 하면 오류가 발생할 수 있고, 컨트롤러로 다시 응답해주는 데이터들이
    account외에 정보가 필요하다거나 account의 정보 중 일부만 필요하다거나
    하는 식으로 요구사항이 바뀔 수 있는데 그런 경우 db와 1:1로 매칭되어야 하는 account클래스를
    그대로 사용하는건 부적합해.
    그렇기 때문에 컨트롤러와 서비스간의 트랜잭션을 도와줄 별도의 dto를 만들어 사용하는게 좋다.
     */



    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("Minus");
        }
        return accountRepository.findById(id).get();
    }

    @Transactional
    public AccountDto deleteAccount(Long userId, String accountNumber) {
        // 프로젝트 설계시 지정했던 5가지 계좌 해지 실패 정책상 위반되는게 없으면,
        // 계좌 해지가 되었다고 성공응답 : 사용자 아이디, 계좌 번호, 해지 일시
        AccountUser accountUser = getAccountUser(userId);

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountException(ACCOUNT_NOT_FOUND));

        validateDeleteAccount(accountUser, account);

        account.setAccountStatus(AccountStatus.UNREGISTERED);
        account.setUnRegisteredAt(LocalDateTime.now());

        accountRepository.save(account);
        //원하는 동작을 수행하는데에는 불필요한 동작이지만, test에 활용하기 위해 넣어준 코드
        //제약 정책 관련된 테스트를 수행하기 위함.
        //안티패턴에 가까운 방식으로 이런 방법을 활용하는걸 추천하진 않으신대요
        return AccountDto.fromEntity(account);

    }

    private void validateDeleteAccount(AccountUser accountUser, Account account) {
        if (!Objects.equals(accountUser.getId(), account.getAccountUser().getId())) {
            throw new AccountException(USER_ACCOUNT_UN_MATCH);

        }


        if (account.getAccountStatus() == AccountStatus.UNREGISTERED) {
            throw new AccountException(ACCOUNT_ALREADY_UNREGISTERED);
        }

        if (account.getBalance() > 0) {
            throw new AccountException(BALANCE_NOT_EMPTY);
        }

    }
    @Transactional
    public List<AccountDto> getAccountsByUserId(Long userId) {
        AccountUser accountUser = getAccountUser(userId);

        List<Account> accounts = accountRepository.findByAccountUser(accountUser);

        return accounts.stream().map(AccountDto::fromEntity) //또는 .map(account -> AccountDto.fromEntity(account))
                .collect(Collectors.toList());

    }
}

