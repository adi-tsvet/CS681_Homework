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
		try{
			for(int i = 0; i < 10; i++){
				if(done){
					System.out.println("Terminating Withdraw Thread");
					break;
				}
				account.withdraw(100);
				Thread.sleep( Duration.ofSeconds(2) );
			}
		}catch(InterruptedException exception){}
	}
}
