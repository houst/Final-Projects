package com.cinema.tags;

import java.util.List;
import java.util.stream.Collectors;

public class ListFunc {
	
	public static boolean contains(List<?> list, Object o) {
		return list.contains(o);
	}
	
	public static String join(List<?> list, String delimiter) {
		return list.stream().map((obj) -> obj.toString()).collect(Collectors.joining(delimiter));
	}
}
