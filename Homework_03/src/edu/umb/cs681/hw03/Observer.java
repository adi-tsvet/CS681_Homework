package edu.umb.cs681.hw03;

@FunctionalInterface
public interface Observer<T> {
	abstract void update(Observable<T> sender, T event);

}
