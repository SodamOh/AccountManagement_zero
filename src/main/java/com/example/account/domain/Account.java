package com.example.account.domain;

import com.example.account.exception.AccountException;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/*
3. 리팩토링
 : 동작은 변경하지 않으면서 내부 구조를 개선하는 작업

목적
  1) 가독성 향상
  2) 유지보수성 향상
   a. 중복코드 제거
   b. 복잡한 구조의 개선 -> 추후에 유지보수 할 때 걸리는 시간을 대폭 축소
 3) 기타
   a. 안티패턴(나쁜 냄새) 제거
   b. 개발 능력의 향상 및 역량의 향상

➜ 생산성의 향상, 개인 역량의 향상에 도움을 준다!

리팩토링에 필요한 것?
 1) 잘 짜여진 탄탄한 테스트 코드. 없으면 코드 짜고 테스트하고를 반복해야 한다. 효율성 떨어짐.
 2) 우리 프로젝트 코드에 대한 관심과 성장하고자 하는 열정 +@ (좋은 동료에게 도움을 받을 수 있어용)
 - 꼭 시간이 있어야만 리팩토링이 가능한 것은 아니다. 열정 열정 열정~!

 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
// @EntityListeners(EntityListeners.class) // 이거는 어노테이션 붙인다고 바로 쓸 수 있는 건 아니고 별도의 설정이 필요로 해요 -> config 패키지에 추가해놓자
// baseEntity 만들어 주었기 때문에 삭제
public class Account extends BaseEntity{

    @ManyToOne //연관관계
    private AccountUser accountUser; //accountUser라고 하는 이유 그냥 User라고 하면 db관련User 테이블과 충돌 발생할 수 있어서
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;


    public void useBalance(Long amount){
        if(amount > balance){
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
        balance -= amount;
    }

    public void cancelBalance(Long amount){
        if(amount < 0){
            throw new AccountException(ErrorCode.INVALID_REQUEST);
        }
        balance += amount;
    }

}
