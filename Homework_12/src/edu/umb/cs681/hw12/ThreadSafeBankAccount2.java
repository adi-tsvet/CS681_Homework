package edu.umb.cs681.hw12;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2 implements BankAccount{
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();
	private volatile boolean done = false;

	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() +
					" (d): current balance: " + balance);
			while(balance >= 300 && !done){
				System.out.println(Thread.currentThread().getId() +
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			if (!done) {
				balance += amount;
				System.out.println(Thread.currentThread().getId() +
						" (d): new balance: " + balance);
				sufficientFundsCondition.signalAll();
			}
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void withdraw(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() +
					" (w): current balance: " + balance);
			while(balance <= 0 && !done){
				System.out.println(Thread.currentThread().getId() +
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			if (!done) {
				balance -= amount;
				System.out.println(Thread.currentThread().getId() +
						" (w): new balance: " + balance);
				belowUpperLimitFundsCondition.signalAll();
			}
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void setDone() {
		done = true;
	}

	public double getBalance() { return this.balance; }

	public static void main(String[] args) throws InterruptedException{
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		Thread[] threads = new Thread[10];

		for(int i = 0; i < 5; i++){
			threads[i] = new Thread( new DepositRunnable(bankAccount) );
			threads[i + 5] = new Thread( new WithdrawRunnable(bankAccount) );
			threads[i].start();
			threads[i + 5].start();
		}

		Thread.sleep(1000);
		bankAccount.setDone();
		for(Thread thread : threads) {
			thread.join();
		}
	}
}
