package edu.umb.cs681.hw12;

import java.time.Duration;

public class WithdrawRunnable implements Runnable{
	private BankAccount account;
	private volatile boolean done = false;
	public void setDone() {
		done = true;
	}
	public WithdrawRunnable(BankAccount account) {
		this.account = account;
	}
	
	public void run(){
		while(!done){
			account.withdraw(100);
		}
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException exception){
			System.out.println("Thread #"+Thread.currentThread().getId() + " Interrupted");
		}
	}
}
