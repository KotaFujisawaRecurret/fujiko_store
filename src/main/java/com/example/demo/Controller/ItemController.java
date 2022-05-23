package com.example.demo.Controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Items;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.ItemsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.service.ItemService;

@Controller
public class ItemController {
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired 
	UsersRepository usersRepository;
	
	@Autowired
	ItemService itemService;
	
//	商品の一覧を表示
	@RequestMapping("/itemList")
	public ModelAndView showItem(ModelAndView mv) {
		
		
		
//		データの商品情報をリストに入れる
		List<Items> item = itemService.getAllItems();
		if(itemService.getAllItems() == null) {
//			データの商品情報がないときのメッセージを表示
			mv.addObject("message", "現在販売されている商品はありません");
		} else {
//			データ内の商品情報をすべて表示
			mv.addObject("itemList", item);
		}
		mv.setViewName("showItem");
		
		
//		参考演算子でNULL対策
		boolean addItemResult = (session.getAttribute("addItemResult") == null ) ? false : (boolean)session.getAttribute("addItemResult");
		if(addItemResult) {
			mv.addObject("addItemResult", addItemResult);
			session.setAttribute("addItemResult", false);
		}
		boolean deleteItemResult = (session.getAttribute("deleteItemResult") == null ) ? false : (boolean)session.getAttribute("deleteItemResult");
		if(deleteItemResult) {
			mv.addObject("deleteItemResult", addItemResult);
			session.setAttribute("deleteItemResult", false);
		}

		return mv;
	}
	
	
//	商品の詳細情報を表示
	@RequestMapping("/itemDetail/{code}")
	public ModelAndView itemDetail(
			@PathVariable("code") int itemCode,
			ModelAndView mv) {
		mv.addObject("item", itemsRepository.findById(itemCode).get());
		mv.setViewName("itemDetail");

		return mv;
	}
	
//	商品を出品する
	@RequestMapping("/addItem")
	public String addItem() {
		return "addItem";
	}
//	商品を出品する処理
	@RequestMapping(value="/addItem", method=RequestMethod.POST)
	public ModelAndView addItem(
			@RequestParam(name="name", defaultValue="") String name,
			@RequestParam(name="price", defaultValue="0")Integer price,
			@RequestParam(name="picture", defaultValue="") String picture,
			@RequestParam(name="stock", defaultValue="") Integer stock,
			@RequestParam(name="categoryKey", defaultValue="0") Integer categoryKey,
			@RequestParam(name="delivaryDays", defaultValue="0") Integer delivaryDays,
			
			ModelAndView mv) {
		
		if(	isNull(name) == true ||
			isNull(price) == true ||
			isNull(picture) == true ||
			isNull(stock) == true ||
			isNull(categoryKey) == true ||
			isNull(delivaryDays) == true
			) {
			
			mv.addObject("message", "すべての項目に入力をしてください");
			mv.setViewName("addItem");
		}else {
		
//		商品が登録して、メッセージと商品一覧を表示
			Users user = (Users) session.getAttribute("userInfo");
			Items item = new Items(name, price, picture, stock, 
					categoryKey, delivaryDays, user.getCode());
			itemsRepository.saveAndFlush(item);
//			商品登録が行われたとき、完了メッセージを表示させるための準備
			session.setAttribute("addItemResult", true);


			mv.setViewName("redirect:/itemList");
		}
		
		return mv;
	}
	
	
//	商品を更新する
	
	@RequestMapping("/item/edit/{code}")
	public ModelAndView Edit(@PathVariable(name = "code") int code, ModelAndView mv) {

//		アイテムのデータを取得
		mv.addObject("item", itemsRepository.findById(code).get());

		mv.setViewName("editItem");

		return mv;
	}

	@RequestMapping(value = "item/edit/{code}", method = RequestMethod.POST)
	public ModelAndView EditItem(
			@PathVariable(name="code") Integer code,
			@RequestParam(name="name", defaultValue="") String name,
			@RequestParam(name="price", defaultValue="0")Integer price,
			@RequestParam(name="picture", defaultValue="") String picture,
			@RequestParam(name="stock", defaultValue="") Integer stock,
			@RequestParam(name="categoryKey", defaultValue="0") Integer categoryKey,
			@RequestParam(name="delivaryDays", defaultValue="0") Integer delivaryDays,
			
			ModelAndView mv) {

//		アイテムのデータを取得
		if(	isNull(name) == true ||
				isNull(price) == true ||
				isNull(picture) == true ||
				isNull(stock) == true ||
				isNull(categoryKey) == true ||
				isNull(delivaryDays) == true
				) {
				
				mv.addObject("message", "すべての項目に入力をしてください");
				mv.setViewName("editItem");
		}

//		入力されている場合、データを更新してアイテム一覧戻る
		else {
			
			// findbyidでデータを取得
			Items item = itemsRepository.findById(code).get();
			
			
			// 更新項目に値をセット
			item.setName(name);
			item.setPrice(price);
			item.setPicture(picture);
			item.setStock(stock);
			item.setCategoryKey(categoryKey);
			item.setDelivaryDays(delivaryDays);
			
		
			itemsRepository.saveAndFlush(item);

			mv.addObject("items", itemsRepository.findAll());

			mv.setViewName("redirect:/itemList");
		}
		return mv;
	}
	
	

//	商品を削除する処理
	@RequestMapping("/delete/{code}")
	public ModelAndView deleteItem(
			@PathVariable("code") int code,
			ModelAndView mv
			) {
		itemsRepository.deleteById(code);
		
		itemsRepository.flush();
		
		session.setAttribute("deleteItemResult", true);
			
//		カートの中身を表示させる
		mv.setViewName("redirect:/itemList");
		return mv;
	}
	
	
	
	public boolean isNull(String insert) {
		if (insert == null || insert.length() == 0) {
			return true;
		}
		return false;
	}
	public boolean isNull(Integer insert) {
		if (insert == null || insert <= 0) {
			return true;
		}
		return false;
	}
	
	public Items getItems(){
//		カートの情報を取得
		Items item =(Items) session.getAttribute("item");
//		カート情報がない場合、カート情報の初期化
		if (item==null) {
			item= new Items();
			session.setAttribute("item", item);
		}
		
		return item;
	}
	
	

}


