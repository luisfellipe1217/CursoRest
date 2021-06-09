package br.com.luisfellipe.rest;

public class User {

	private String name;
	private Integer age;
	private Double salary;
	private Long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User(String name, Integer age, Double salary) {
		super();
		this.name = name;
		this.age = age;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "User [ id: " + id +", name: "+ name +", age: "+ age + ", salary: "+ salary+ " ]";
	}

}
