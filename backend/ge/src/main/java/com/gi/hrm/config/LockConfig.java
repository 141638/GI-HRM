package com.gi.hrm.config;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockConfig {
	@Bean
	public Lock reentrantLock() {
		return new ReentrantLock();
	}

	@Bean
	public StampedLock stampedLock() {
		return new StampedLock();
	}

	@Bean
	public AtomicBoolean isProcessing() {
		return new AtomicBoolean(false);
	}

	@Bean
	public Semaphore semaphore() {
		return new Semaphore(1);
	}
}
