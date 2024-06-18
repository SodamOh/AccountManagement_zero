package com.example.account.service;

import com.example.account.dto.UseBalance;
import com.example.account.exception.AccountException;
import com.example.account.type.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LockAopAspectTest {
    @Mock
    private LockService lockService;

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @InjectMocks
    private LockAopAspect lockAopAspect;

    // 한번 더 복습
    @Test
    void lockAndUnlock() throws Throwable {
        // given
        ArgumentCaptor<String> lockArgumentCaptor =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> unlockArgumentCaptor =
                ArgumentCaptor.forClass(String.class);
        UseBalance.Request request = new UseBalance.Request(123L, "1234", 1000L);

        // when
        lockAopAspect.aroundMethod(proceedingJoinPoint, request);

        // then
        verify(lockService, times(1)) // lock service에서 lock을 한번 수행하고
                .lock(lockArgumentCaptor.capture());
        verify(lockService, times(1)) // unlock을 한번 수행했는지 확인하는 테스트
                .unlock(unlockArgumentCaptor.capture());
        assertEquals("1234", lockArgumentCaptor.getValue());
        assertEquals("1234", unlockArgumentCaptor.getValue());
    }

    //exception이 발생하더라도 lock, unlock이 잘 동작하는지 테스트
    @Test
    void lockAndUnlock_evenIfThrow() throws Throwable {
        // given
        ArgumentCaptor<String> lockArgumentCaptor =
                ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> unlockArgumentCaptor =
                ArgumentCaptor.forClass(String.class);
        UseBalance.Request request = new UseBalance.Request(123L, "54321", 1000L);
        given(proceedingJoinPoint.proceed())
                .willThrow(new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));


        // when
        assertThrows(AccountException.class, () ->
                lockAopAspect.aroundMethod(proceedingJoinPoint, request));

        // then
        verify(lockService, times(1)) // lock service에서 lock을 한번 수행하고
                .lock(lockArgumentCaptor.capture());
        verify(lockService, times(1)) // unlock을 한번 수행했는지 확인하는 테스트
                .unlock(unlockArgumentCaptor.capture());
        assertEquals("54321", lockArgumentCaptor.getValue());
        assertEquals("54321", unlockArgumentCaptor.getValue());
    }
}