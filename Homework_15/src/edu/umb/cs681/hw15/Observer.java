package edu.umb.cs681.hw15;

@FunctionalInterface
public interface Observer<T> {
	abstract void update(Observable<T> sender, T event);

}
