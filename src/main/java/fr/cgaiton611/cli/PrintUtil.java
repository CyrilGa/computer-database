package fr.cgaiton611.cli;

import java.util.List;

public class PrintUtil {
	public void print(Object o) {
		System.out.print(o);
	}

	public void printn(Object o) {
		System.out.println(o);
	}

	public <T> void printEntities(List<T> entities) {
		for (T entity : entities) {
			System.out.println(entity.toString());
		}
	}
}
