package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="items")
public class Items {
	@Id
	@Column(name="name")
	private String name;
	@Column(name="price")
	private Integer price;
	@Column(name="picture")
	private String picture;
	@Column(name="stock")
	private Integer stock;
	@Column(name="category_key")
	private Integer categoryKey;
	@Column(name="delivary_days")
	private Integer delivaryDays;
	@Column(name="seller_user_code")
	private Integer sellerUserCode;
	@Column(name="date")
	private Date date;
	@Column(name="delete_flag")
	private Integer deleteFlag;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getCategoryKey() {
		return categoryKey;
	}
	public void setCategoryKey(Integer categoryKey) {
		this.categoryKey = categoryKey;
	}
	public Integer getDelivaryDays() {
		return delivaryDays;
	}
	public void setDelivaryDays(Integer delivaryDays) {
		this.delivaryDays = delivaryDays;
	}
	public Integer getSellerUserCode() {
		return sellerUserCode;
	}
	public void setSellerUserCode(Integer sellerUserCode) {
		this.sellerUserCode = sellerUserCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

}


