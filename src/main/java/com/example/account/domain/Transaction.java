package com.example.account.domain;


import com.example.account.type.AccountStatus;
import com.example.account.type.TransactionResultType;
import com.example.account.type.TransactionType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

/*
builder는 상속 구조에서 쓸 때 까다로움이 좀 있어요.
부모 클래스에도 있고 자식 클래스에도 있으면
뭘 써야 하는지에서 오는 오류가 발생하는 듯?
 */

public class Transaction extends BaseEntity{
//    @Id
//    @GeneratedValue
//    private Long id;
//    // 얘는 보안상 노출되면 안됨

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType;

    @ManyToOne
    private Account account;
    private Long amount;
    private Long balanceSnapshot;

    private String transactionId;
    private LocalDateTime transactedAt; // 거래 스냅샷 용도


}
