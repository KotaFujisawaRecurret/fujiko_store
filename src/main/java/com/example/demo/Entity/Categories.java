package com.example.demo.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Categories {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")	
	private Integer code;
	@Column(name="name")
	private String name;
	@Column(name="delete_flag")
	private Integer deleteFlag;
	
	Categories(){
		super();
	}

	public Categories(String name, Integer deleteFlag) {
		super();
		this.name = name;
		this.deleteFlag = deleteFlag;
	}
	
	public Categories(Integer code, String name, Integer deleteFlag) {
		super();
		this.code = code;
		this.name = name;
		this.deleteFlag = deleteFlag;
	}
	
	
	
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	

}


