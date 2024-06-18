package com.example.account.dto;

import com.example.account.domain.Account;
import com.example.account.domain.Transaction;
import com.example.account.type.TransactionResultType;
import com.example.account.type.TransactionType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private String accountNumber; // Account 엔티티를 쓰는게 아니라 그냥 계좌번호만 가져와서 쓴다.
    private TransactionType transactionType;
    private TransactionResultType transactionResultType;
    private Account account;
    private Long amount;
    private Long balanceSnapshot;
    private String transactionId;
    private LocalDateTime transactedAt; // 거래 스냅샷 용도


    // transaction이랑 유사한 구조로 맵핑해준다.
    // 잘 맵핑이 되었는지 test 코드로 테스트 해주면 좋다.
    public static TransactionDto fromEntity(Transaction transaction){
        return TransactionDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .transactionResultType(transaction.getTransactionResultType())
                .amount(transaction.getAmount())
                .balanceSnapshot(transaction.getBalanceSnapshot())
                .transactionId(transaction.getTransactionId())
                .transactedAt(transaction.getTransactedAt())
                .build();
    }



}
