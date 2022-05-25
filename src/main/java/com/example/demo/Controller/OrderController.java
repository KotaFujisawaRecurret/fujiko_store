package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Entity.Order;
import com.example.demo.Entity.OrderDetail;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ItemsRepository;
import com.example.demo.Repository.OrderDetailRepository;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.service.ItemService;

@Controller
public class OrderController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	CategoriesRepository categoriesRepository;
	
	@Autowired
	UsersRepository usersRepository;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	ItemService itemService;
	
	
	
//	購入する商品のリザルトとユーザー情報の表示
	@RequestMapping("/purchaseCart")
	public ModelAndView purchaseCart(ModelAndView mv) {
		Cart cart =getCart();		
//		ページに表示したい情報を設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.addObject("categories", categoriesRepository.findAll());
		mv.addObject("users", usersRepository.findAll());
		
		mv.setViewName("purchaseCart");
		return mv;
	}
	
	
//	カートの商品を購入完了させる
	@RequestMapping(value="/purchaseCart/result", method=RequestMethod.POST)
	public ModelAndView doOrder(ModelAndView mv) {
//		カートの情報を取得
		Cart cart= getCart();
		Users user = (Users) session.getAttribute("userInfo");
		int user_code = user.getCode();
//		オーダー情報の登録（orderへの登録）
		Order order = new Order(user_code, new Date(), cart.getTotal());
		int order_code = orderRepository.saveAndFlush(order).getCode();
		
//		オーダー詳細情報の登録（orderDetailへの登録）カートのアイテム一覧を登録
		Map<Integer, Items> items= cart.getItems();
		List<OrderDetail> orderDetails = new ArrayList<>();
		List<Items> updateitems = new ArrayList<>();
		for (Items item:items.values()) {
			orderDetails.add(new OrderDetail(order_code, item.getCode(), item.getStock()));
			item.setStock(item.getStock() - item.getQuantity());
			updateitems.add(item);
		}
		orderDetailRepository.saveAllAndFlush(orderDetails);
		itemsRepository.saveAllAndFlush(updateitems);
		
		session.removeAttribute("cart");

		mv.setViewName("purchaseCartResult");
		
		return mv;
	}
	

	
	public Cart getCart() {
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


