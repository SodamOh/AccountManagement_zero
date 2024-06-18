package com.example.account.controller;

import com.example.account.dto.AccountDto;
import com.example.account.dto.DeleteAccount;
import com.example.account.exception.AccountException;
import com.example.account.service.AccountService;
import com.example.account.type.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest2 {
    @MockBean
    private AccountService accountService;

    @MockBean

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // json - object 간의 상호 변경을 도와줌

    // Aop 실습 전 정상 작동하던 테스트들입니다:)
//
//    @Test
//    void SuccessAccountDelete() throws Exception {
//        // given
//        given(accountService.deleteAccount(anyLong(), anyString()))
//                .willReturn(AccountDto.builder()
//                        .userId(1L)
//                        .accountNumber("1234567890")
//                        .registeredAt(LocalDateTime.now())
//                        .unRegisteredAt(LocalDateTime.now())
//                        .build());
//        // when
//        //then
//        mockMvc.perform(delete("/account")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(
//                                new DeleteAccount.Request(3333L, "0987654321")
//                        )))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userId").value(1))
//                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
//                .andDo(print());
//
//
//    }
//
//    @Test
//    void successGetAccountByUserId() throws Exception {
//        // given
//        List<AccountDto> accountDtos =
//                Arrays.asList(AccountDto.builder()
//                                .accountNumber("1234567890")
//                                .balance(1000L).build(),
//                        AccountDto.builder()
//                                .accountNumber("3456789012")
//                                .balance(2000L).build()
//                        , AccountDto.builder()
//                                .accountNumber("5678901234")
//                                .balance(3000L).build());
//        given(accountService.getAccountsByUserId(anyLong()))
//                .willReturn(accountDtos);
//
//        // when
//        // then
//        mockMvc.perform(get("/account?user_id=1"))
//                .andDo(print())
//                .andExpect(jsonPath("$[0].accountNumber")
//                        .value("1234567890"))
//                .andExpect(jsonPath("$[0].balance")
//                        .value("1000"))
//                .andExpect(jsonPath("$[1].accountNumber")
//                        .value("3456789012"))
//                .andExpect(jsonPath("$[1].balance")
//                        .value("2000"))
//                .andExpect(jsonPath("$[2].accountNumber")
//                        .value("5678901234"))
//                .andExpect(jsonPath("$[2].balance")
//                        .value("3000"));
//        //github.com/json-path/JsonPath 참고하면 코드짤 때 도움이 될거에요
//    }
//
//    @Test
//     void failGetAccount() throws Exception {
//        // given
//        given(accountService.getAccount(anyLong()))
//                .willThrow(new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));
//
//        // when
//        // then
//        mockMvc.perform(get("/account/876"))
//                .andDo(print())
//                .andExpect(jsonPath("$.errorCode").value("ACCOUNT_NOT_FOUND"))
//                .andExpect(jsonPath("$.errorMessage").value("해당 계좌가 없습니다."))
//                .andExpect(status().isOk());
//
//    }

}
