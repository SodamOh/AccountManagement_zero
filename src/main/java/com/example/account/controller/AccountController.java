package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request //@Valid 어노테이션을 사용하기 위해서는 request 쪽에 어떻게 밸리드 할 것인지 달아줘야 함
            ) {
        return CreateAccount.Response.from(
                accountService.createAccount(
                        request.getUserId(),
                        request.getInitialBalance()
                )
        );
    }

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ){
        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
        @RequestParam("user_id") Long userId) {
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance()).build())
                .collect(Collectors.toList());

    }
    /*
    처음에 너무 성능을 위주로 코드를 짜다보면,
    복잡도는 올라가고 유지보수가 어려워진다.

    성능은 문제될 때 까지 문제가 아니다~ 성능이 중요한 상황까지 가야 유의미할 수 있다.
    체감되지도 않을 성능의 향상을 위해서 더 중요한 안정성이나 유지보수성이 떨어질 수 있다.

    코드를 짤 때는 안정성과 유지보수성을 우선으로 하라.
     */



    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id){
        return accountService.getAccount(id);
    }
}
