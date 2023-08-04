package me.devtec.amazingfishing.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Pagination<T> extends ArrayList<T> {
	private static final long serialVersionUID = 1L;
	
	private int pageSize;

	public Pagination(int pageSize) {
		this(pageSize, new ArrayList<>());
	}

	@SafeVarargs
	public Pagination(int pageSize, T... objects) {
		this(pageSize, Arrays.asList(objects));
	}

	public Pagination(int pageSize, Collection<T> objects) {
		this.pageSize = pageSize;
		addAll(objects);
	}

	public int pageSize() {
		return pageSize;
	}

	public int totalPages() {
		return (int) Math.ceil((double) size() / pageSize);
	}

	public boolean exists(int page) {
		return (page >= 0) && page < totalPages();
	}

	public List<T> getPage(int page) {
		if (page < 0 || page >= totalPages())
			throw new IndexOutOfBoundsException("Index: " + page + ", Size: " + totalPages());

		int min = page * pageSize;
		int max = ((page * pageSize) + pageSize);

		if (max > size())
			max = size();

		return subList(min, max);
	}
}
