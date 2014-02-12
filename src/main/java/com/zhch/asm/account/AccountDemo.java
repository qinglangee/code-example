package com.zhch.asm.account;

public class AccountDemo {
	public void test() {
		Account acc = new Account();
		acc.showMoney();
	}

	public static void main(String[] args) {
		AccountDemo t = new AccountDemo();
		t.test();
	}
}
