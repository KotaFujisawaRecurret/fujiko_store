package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")
	private int code;

	@Column(name="name")
	private String name;
	@Column(name="address")
	private String address;
	@Column(name="tel")
	private String tel;
	@Column(name="email")
	private String email;
	@Column(name="pass")
	private String pass;
	@Column(name="delete_flag")
	int deleteFlag;

//	デフォルトコンストラクタ
	public Users() {
		super();

	}
	
	/**
	 * 登録用コンストラクタ
	 * @param name
	 * @param address
	 * @param tel
	 * @param email
	 * @param pass
	 */
	public Users(String name, String address, String tel, String email) {
		super();
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
	}
	
	public Users(String name, String address, String tel, String email, String pass) {
		super();
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.pass = pass;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getDeleteCode() {
		return deleteFlag;
	}
	public void setDeleteCode(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}