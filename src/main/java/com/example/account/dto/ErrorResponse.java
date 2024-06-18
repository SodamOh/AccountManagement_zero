package com.example.account.dto;

import com.example.account.type.ErrorCode;
import lombok.*;

/*
[일관성 있는 예외처리]
  "timestamp": "2024-06-18T17:44:09.589+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/account"

  이런 에러 메세지의 경우, 실제로 USER_NOT_FOUND에 해당하는 에러라고 가정할 때
  500, Internal Server Error만 보고서는 정확히 어디에 해당하는지 찾기 어려워

  편리한 에러 응답을 통해 client가 처리하기 편리하도록 바꿔주자
  최근엔 에러코드와 에러메세지를 활용하는 방법이 대세.

 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse {
    private ErrorCode errorCode;
    private String errorMessage;

}
