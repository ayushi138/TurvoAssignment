package com.turvo.flash.sale.ws.shared;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Component;

@Component
public class SingleLockObject {
	
	private Lock lock ;
	
	public SingleLockObject() {
		lock = new ReentrantLock();
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

}
