package edu.umb.cs681.hw19;

@FunctionalInterface
public interface Observer<T> {
	abstract void update(Observable<T> sender, T event);

}
