package com.example.account.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // 클래스 자체가 어플리케이션에 뜰 때 autoscan이 되는 타입이 되고
@EnableJpaAuditing // 자동으로 bean으로 등록이 되는 타입.JPA auditing이 켜진상태로 어플리케이션이 뜸
// db에 데이터가 저장, 수정 등을 할 때 어노테이션이 붙은 위치의 값들을 자동으로 저장
public class JpaAuditingConfiguration {
}
