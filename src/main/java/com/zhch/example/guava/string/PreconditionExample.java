package com.zhch.example.guava.string;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class PreconditionExample {
	private String label;
	private int[] values = new int[5];
	private int currentIndex;

	public PreconditionExample(String label) {
		// 检查空值
		this.label = checkNotNull(label, "Label can''t be null");
	}

	public void updateCurrentIndexValue(int index, int valueToSet) {
		// 检查 索引值
		this.currentIndex = checkElementIndex(index, values.length, "Index out of bounds for values");
		// 检查参数范围
		checkArgument(valueToSet <= 100, "Value can't be more than100");
		values[this.currentIndex] = valueToSet;
	}

	public void doOperation() {
		// 抛出java自带异常
		checkState(validateObjectState(), "Can't perform operation");
	}

	private boolean validateObjectState() {
		return this.label.equalsIgnoreCase("open") && values[this.currentIndex] == 10;
	}
}
