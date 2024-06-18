package com.example.account.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountInfo {
    //계좌의 특정 정보만 따로 추출하여 사용자에게 응답하기 위한 목적
    //client-controller와 응답을 주고 받는 목적
    private String accountNumber;
    private Long balance;

    /*
    DTO 내에 클래스들이 구성이 비슷해서 여러개의
    디티오를 쓰는게 비효율 적이지 않나? 라는 생각이 들 수 있음
    그러나 디티오의 용도가 너무 복잡해지면 장애가 발생함.
    DTO 클래스들은 각자의 목적에 맞게, 특화된 용도로 사용하자(최적화)

     */



}
