package com.zhch.example.guava.collections;

import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import static org.testng.Assert.*;

public class UseCollections {

	Person person1;
	Person person2;
	Person person3;
	Person person4;
	List<Person> personList;

	public void setUp() {
		person1 = new Person("Wilma", "Flintstone", 30, "F");
		person2 = new Person("Fred", "Flintstone", 32, "M");
		person3 = new Person("Betty", "Rubble", 31, "F");
		person4 = new Person("Barney", "Rubble", 33, "M");
		// 创建一个 List
		personList = Lists.newArrayList(person1, person2, person3, person4);
		// 创建一个 string List
		List<String> strList = Lists.newArrayList("a", "b", "c");
		System.out.println(strList);
	}

	public void testFilter() throws Exception {
		setUp();
		Iterable<Person> filtered = FluentIterable.from(personList).filter(new Predicate<Person>() {
			@Override
			public boolean apply(Person input) {
				return input.getAge() > 31;
			}
		});
		assertTrue(Iterables.contains(filtered, person2));
		assertTrue(Iterables.contains(filtered, person4));
		assertFalse(Iterables.contains(filtered, person1));
		assertFalse(Iterables.contains(filtered, person3));
	}
	
	public void useLists(){
		setUp();
		// 分隔 列表
		List<List<Person>> subList = Lists.partition(personList,2); // [[person1,person2],[person3,person4]]
	}
	
	public static void main(String[] args) throws Exception {
		UseCollections t = new UseCollections();
		t.testFilter();
		System.out.println("over!!");
	}
}

class Person {
	String firstName;

	String lastName;
	int age;
	String sex;

	public Person(String fn, String ln, int age, String sex) {
		firstName = fn;
		lastName = ln;
		this.age = age;
		this.sex = sex;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

}
