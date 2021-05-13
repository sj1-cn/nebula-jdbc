package cn.sj1.nebula.data.basic;

import java.util.List;

public interface PageList<E> extends List<E> {
	int getStart();

	int getMax();

	int getTotalSize();

	void start(int start);

	void max(int max);

	void totalSize(int totalSize);
}
