package edu.umb.cs681.hw19;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Observable<T> {
	public ConcurrentLinkedQueue<Observer<T>> observers = new ConcurrentLinkedQueue<>();

	public void addObserver(Observer<T> o) {
		observers.add(o);
	}

	public void clearObservers() {
		observers.clear();
	}

	public List<Observer<T>> getObservers() {
		return List.copyOf(observers);
	}

	public int countObservers() {
		return observers.size();
	}

	public void removeObserver(Observer<T> o) {
		observers.remove(o);
	}

	public void notifyObservers(T event) {
		observers.forEach(observer -> observer.update(this, event));
	}
}
