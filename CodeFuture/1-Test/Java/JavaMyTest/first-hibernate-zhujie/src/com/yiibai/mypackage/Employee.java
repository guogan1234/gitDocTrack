package com.yiibai.mypackage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_employee")
public class Employee {
	@Id
	private int id;
	
	//����OK.
	@Column(name="firstName")
	private String testName;
	private String lastName;//Ĭ������£�����������ȥ�������ݿ�������
	
	private Double salary;
	
	//����OK.������ע�⣬���Բ����Լ�ʵ��setter��getter����
	
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}/*
	public String getFirstName() {
		return testName;
	}*/
	public void setFirstName(String firstName) {
		this.testName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", testName=" + testName + ", lastName=" + lastName + "]";
	}
}