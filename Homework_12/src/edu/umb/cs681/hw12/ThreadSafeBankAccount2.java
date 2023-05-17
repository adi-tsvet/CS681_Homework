package edu.umb.cs681.hw12;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ThreadSafeBankAccount2 implements BankAccount {
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	private Condition sufficientFundsCondition = lock.newCondition();
	private Condition belowUpperLimitFundsCondition = lock.newCondition();

	public void deposit(double amount){
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().threadId() +
					" (d): current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().threadId() +
						" (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().threadId() +
					" (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
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
			System.out.println(Thread.currentThread().threadId() +
					" (w): current balance: " + balance);
			while(balance <= 0){
				System.out.println(Thread.currentThread().threadId() +
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().threadId() +
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public double getBalance() { return this.balance; }

	public static void main(String[] args) throws InterruptedException {
		ThreadSafeBankAccount2 bankAccount = new ThreadSafeBankAccount2();
		Thread[] threads = new Thread[10];

		DepositRunnable[] depositRunnables = new DepositRunnable[5];
		WithdrawRunnable[] withdrawRunnables = new WithdrawRunnable[5];

		for (int i = 0; i < 5; i++) {
			depositRunnables[i] = new DepositRunnable(bankAccount);
			withdrawRunnables[i] = new WithdrawRunnable(bankAccount);

			threads[i] = new Thread(depositRunnables[i]);
			threads[i + 5] = new Thread(withdrawRunnables[i]);

			threads[i].start();
			System.out.println("Thread #"+threads[i].getId() + " started !");
			threads[i + 5].start();
			System.out.println("Thread #"+threads[i+5].getId() + " started !");

		}

		for (DepositRunnable runnable : depositRunnables) {
			runnable.setDone();
		}

		for (WithdrawRunnable runnable : withdrawRunnables) {
			runnable.setDone();
		}

		for (Thread thread : threads) {
			thread.interrupt();
		}

	}
}
