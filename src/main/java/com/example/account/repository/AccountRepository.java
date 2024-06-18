package com.example.account.repository;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    // 순서만 맞게 작성해주면 알아서 쿼리를 작성해줘요
    //Id순으로 desc 정렬해서 가장 첫번째를 찾아줘~
    Optional<Account> findFirstByOrderByIdDesc();

    Integer countByAccountUser(AccountUser accountUser);

    Optional<Account> findByAccountNumber(String AccountNumber);

    List<Account> findByAccountUser(AccountUser accountUser);
    // jpa 관련 기능으로 account에 연관관계로 포함된 accountUser가 있기 때문에
    // 메소드가 accountRepository안에서 자동으로 생성됨
    // accountUser를 주면 account에 해당되는 리스트를 가져옴
}
