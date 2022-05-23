package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ordered")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="code")
	private Integer code;
	@Column(name="user_code")
	private Integer userCode;
	@Column(name="ordered_date")
	private Date orderedDate;
	@Column(name="total_price")
	private Integer totalPrice;
	@Column(name="delete_flag")
	private Integer deleteFlag;
	
	
	
	public Order() {
		super();
	}
	public Order(Integer userCode, Date orderedDate, Integer totalPrice) {
		super();
		this.userCode = userCode;
		this.orderedDate = orderedDate;
		this.totalPrice = totalPrice;

	}
	public Order(Integer code, Integer userCode, Date orderedDate, Integer totalPrice) {
		super();
		this.code = code;
		this.userCode = userCode;
		this.orderedDate = orderedDate;
		this.totalPrice = totalPrice;

	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getUserCode() {
		return userCode;
	}
	public void setUserCode(Integer userCode) {
		this.userCode = userCode;
	}
	public Date getOrderedDate() {
		return orderedDate;
	}
	public void setOrderedDate(Date orderedDate) {
		this.orderedDate = orderedDate;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	

}


