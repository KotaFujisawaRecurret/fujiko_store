package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.Items;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ItemsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.service.ItemService;

@Controller
public class CartController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired 
	UsersRepository usersRepository;
	
	@Autowired
	CategoriesRepository categoriesRepository;
	
	@Autowired
	ItemService itemService;

	@RequestMapping("/cart/add/{code}")
	public ModelAndView addCart(
			@PathVariable("code") int code,
			ModelAndView mv) {
//		カートの情報を取得
		Cart cart=getCart(); 

//		商品コードをもとにアイテム情報を取得
		Items item = itemsRepository.getById(code);
//		カート情報にアイテム情報を追加
		cart.addCart(item, 1);
		
		mv.setViewName("addComp");
		return mv;
	}

	
//		カート内の商品を表示する
	@RequestMapping("/confirmCart")
	public ModelAndView confirmCart(

			ModelAndView mv) {
//		カートの情報を取得
		Cart cart=getCart(); 
		

//		ページに表示したい情報を設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.addObject("categories", categoriesRepository.findAll());

		
//		カートの中身を表示させる
		mv.setViewName("confirmCart");
		return mv;
		}
	
//	カート内を削除する処理
	@RequestMapping("/cart/delete/{code}")
	public ModelAndView deleteCart(
			@PathVariable("code") int code,
			ModelAndView mv
			) {
//		カートの情報を取得
		Cart cart =getCart();
//TODO 個数を減らすように調整
//		カートの中からコードの一致するアイテムを削除
		cart.deleteCart(code);
		
//		ページに表示したい情報を設定
		mv.addObject("items", cart.getItems());
		mv.addObject("total", cart.getTotal());
		mv.addObject("categories", categoriesRepository.findAll());
		mv.addObject("message", "選択した商品を削除しました");

		
//		カートの中身を表示させる
		mv.setViewName("confirmCart");
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






