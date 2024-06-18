package com.example.account.dto;

import com.example.account.domain.Account;
import lombok.*;

import java.time.LocalDateTime;
/*
많이 쓰이는 기법 중 하나로, 엔티티 클래스와 구조는 비슷한데,
딱 필요한 요소만 넣어서 클래스를 구성
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
    // 응답에 필요한 데이터는
    // 응답에 필요한 데이터가 무엇이냐는 처음 기획할 때 요청/응답 구조 짜고 그에 맞는 변수들을 지정
    private Long userId;
    private String accountNumber;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;


    //특정 엔티티에서 특정 Dto로 변환해줄 때 자주 쓰는 방법
    public static AccountDto fromEntity(Account account){
        return AccountDto.builder()
                .userId(account.getAccountUser().getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .registeredAt(account.getRegisteredAt())
                .unRegisteredAt(account.getUnRegisteredAt())
                .build();
    }
}
