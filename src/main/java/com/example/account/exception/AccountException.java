package com.example.account.exception;


import com.example.account.type.ErrorCode;
import lombok.*;
// custom exception 만들기
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;
    // 요즘엔 커스텀 인셉션 짤 때 런타임 exception을 extends 해서 많이 짜요
    // 근데 runtimeException 자체는 기능이 별로 없어.
    // 강사님은 에러 코드를 넣어서 활용하신대요


    public AccountException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
