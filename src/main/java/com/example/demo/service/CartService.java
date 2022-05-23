package com.example.demo.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Cart;

@Service
public class CartService {
	
	public Cart getCart(HttpSession session) {
//		カートの情報を取得
		Cart cart =(Cart) session.getAttribute("cart");
		
//		カート情報がない場合、カート情報の初期処理
		if (cart==null) {
			cart= new Cart();
			session.setAttribute("cart", cart);
		}
		
		return cart;
	}

}


