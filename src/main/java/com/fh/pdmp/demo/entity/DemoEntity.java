package com.fh.pdmp.demo.entity;

/**
 * 实体类 - 理解为普通Bean，如用户类，车辆类等
 */
public class DemoEntity {

	private String name;

	private int age;

	private String gender;

	public DemoEntity(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "DemoEntity{" +
				"name='" + name + '\'' +
				", age=" + age +
				", gender='" + gender + '\'' +
				'}';
	}
}
