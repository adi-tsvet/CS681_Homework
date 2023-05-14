package edu.umb.cs681.hw15;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Observable<T> {
	public LinkedList<Observer<T>> observers = new LinkedList<>();

	private ReentrantLock lockObs = new ReentrantLock();

	public void addObserver(Observer<T> o) {
		observers.add(o);
	}

	public void clearObservers() {
		observers.clear();
		
	}
	public List<Observer<T>> getObservers(){
		return observers;
	}
	
	public int countObservers() {
		return observers.size();
	}
	public void removeObserver(Observer<T> o) {
		observers.remove(o);
	}

	public void notifyObservers(T event) {
		LinkedList<Observer<T>> localObservers;
		lockObs.lock();
		try {
			localObservers = new LinkedList<>(observers);
		} finally {
			lockObs.unlock();
		}
		localObservers.forEach(observer -> observer.update(this, event)); //Open Call
	}
	
}
