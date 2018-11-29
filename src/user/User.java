package user;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable
{
	private String name;
	private String id;
	private String password;
	private String qianm;
	private String sex;
	
	
	
	public User(String name, String id) {
		this.name = name;
		this.id = id;
	}
	public User() {
	}
	public User(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getQianm() {
		return qianm;
	}
	public void setQianm(String qianm) {
		this.qianm = qianm;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", id=" + id + ", password=" + password + ", qianm=" + qianm + ", sex=" + sex
				+ "]";
	}
	
	
}
