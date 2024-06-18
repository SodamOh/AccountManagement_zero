package com.example.account.service;


import com.example.account.aop.AccountLockIdInterface;
import com.example.account.dto.UseBalance;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {
    private final LockService lockService;

    @Around("@annotation(com.example.account.aop.AccountLock) && args(request)") // 어떤 경우에 이 aspect를 적용할 것인가 정의해줌
    public Object aroundMethod(
            ProceedingJoinPoint pjp,
            AccountLockIdInterface request
    )throws Throwable{ // 예외가 발생할 수 있기 때문에 붙여줘요
        //lock 취득 시도
        lockService.lock(request.getAccountNumber());
        try{
            // before
            return pjp.proceed();
            // after 동작을 붙여줄 수 있기 때문에 around
        }finally {
            //lock 해제(진행이 정상적으로 되었던 또는 그렇지 않던간에)
            lockService.unlock(request.getAccountNumber());
        }

    }
}
