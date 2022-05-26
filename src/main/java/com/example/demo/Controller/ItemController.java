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

import com.example.demo.Entity.Categories;
import com.example.demo.Entity.Items;
import com.example.demo.Entity.Users;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ItemsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.service.CategoriesService;
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

	@Autowired
	CategoriesService categoriesService;

	@Autowired
	CategoriesRepository categoriesRepository;

//	商品の一覧を表示
	@RequestMapping("/itemList")
	public ModelAndView showItem(ModelAndView mv) {

//		データの商品情報をリストに入れる
		List<Items> item = itemService.getAllItems();
//		カテゴリー情報をリスト化する

		mv.addObject("categoriesList", categoriesRepository.findAll());

		if (itemService.getAllItems() == null) {
//			データの商品情報がないときのメッセージを表示
			mv.addObject("message", "現在販売されている商品はありません");
		} else {
//			データ内の商品情報をすべて表示
			mv.addObject("itemList", item);
		}
		mv.setViewName("showItem");

//		三項演算子でNULL対策
		boolean addItemResult = (session.getAttribute("addItemResult") == null) ? false
				: (boolean) session.getAttribute("addItemResult");
		if (addItemResult) {
			mv.addObject("addItemResult", addItemResult);
			session.setAttribute("addItemResult", false);
		}
		boolean deleteItemResult = (session.getAttribute("deleteItemResult") == null) ? false
				: (boolean) session.getAttribute("deleteItemResult");
		if (deleteItemResult) {
			mv.addObject("deleteItemResult", addItemResult);
			session.setAttribute("deleteItemResult", false);
		}

		return mv;
	}
//	検索したカテゴリーと一致する商品の一覧を表示
	@RequestMapping(value = "/showItem")
	public ModelAndView showItemByCategory(
			@RequestParam(name = "categoryCode") Integer categoriesCode,
			ModelAndView mv) {
//		カテゴリー情報を取得
		mv.addObject("categoriesList", categoriesRepository.findAll());

//		アイテムのカテゴリーキーと検索したキーが一致するものをリストに入れる
		List<Items> item = itemsRepository.findByCategoryKey(categoriesCode);
		mv.addObject("itemList", item);
		mv.setViewName("showItem");
		return mv;
	}

//	検索ボックスを使用して、商品を検索して一覧表示
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView showItemBySearch(
			@RequestParam(name = "search") String searchStr, 
			ModelAndView mv) {
		
//		カテゴリー情報を取得
		mv.addObject("categoriesList", categoriesRepository.findAll());

//		検索ボックスに入れた情報が含まれる商品をItemsリストから取得し表示
		List<Items> item = itemsRepository.findByNameContains(searchStr);
		
		mv.addObject("itemList", item);
		mv.setViewName("showItem");

		return mv;
	}

//	商品の詳細情報を表示
	@RequestMapping("/itemDetail/{code}")
	public ModelAndView itemDetail(@PathVariable("code") int itemCode, ModelAndView mv) {
//		カテゴリーの情報を取得
		Items item = itemsRepository.findById(itemCode).get();
		mv.addObject("category", categoriesRepository.findById(item.getCategoryKey()).get());
		mv.addObject("item", item);
		mv.setViewName("itemDetail");

		return mv;
	}

//	商品を出品する

	@RequestMapping("/addItem")
	public ModelAndView addItem(ModelAndView mv) {
		mv.addObject("categoriesList", categoriesRepository.findAll());
		mv.setViewName("addItem");
		return mv;
	}

//	商品を出品する処理
	@RequestMapping(value = "/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "0") Integer price,
			@RequestParam(name = "picture", defaultValue = "") String picture,
			@RequestParam(name = "stock", defaultValue = "") Integer stock,
			@RequestParam(name = "categoryKey", defaultValue = "0") Integer categoryKey,
			@RequestParam(name = "newCategory", defaultValue = "") String categoryName,
			@RequestParam(name = "delivaryDays", defaultValue = "0") Integer delivaryDays,

			ModelAndView mv) {

		mv.addObject("categoriesList", categoriesRepository.findAll());

//		未入力チェック処理
		if (isNull(name) == true || isNull(price) == true || isNull(picture) == true || isNull(stock) == true
				|| categoryKey < 0 || isNull(delivaryDays) == true) {

			mv.addObject("message", "すべての項目に入力をしてください");
			mv.setViewName("addItem");
		} else {

//		商品が登録して、メッセージと商品一覧を表示
			Users user = (Users) session.getAttribute("userInfo");
//			新規カテゴリーが入力された場合
			if(!isNull(categoryName)) {
				Categories category = new Categories(categoryName);
				categoryKey = categoriesRepository.saveAndFlush(category).getCode();
			}
			Items item = new Items(name, price, picture, stock, categoryKey, delivaryDays, user.getCode());
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
//		カテゴリーの情報を取得
		mv.addObject("categoriesList", categoriesRepository.findAll());


		mv.setViewName("editItem");

		return mv;
	}

	@RequestMapping(value = "item/edit/{code}", method = RequestMethod.POST)
	public ModelAndView EditItem(@PathVariable(name = "code") Integer code,
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "price", defaultValue = "0") Integer price,
			@RequestParam(name = "picture", defaultValue = "") String picture,
			@RequestParam(name = "stock", defaultValue = "") Integer stock,
			@RequestParam(name = "newCategory", defaultValue = "") String categoryName,
			@RequestParam(name = "categoryKey", defaultValue = "-1") Integer categoryKey,
			@RequestParam(name = "delivaryDays", defaultValue = "0") Integer delivaryDays,

			ModelAndView mv) {

		mv.addObject("categoriesList", categoriesRepository.findAll());
//		未入力チェック
		if (isNull(name) == true ||
			isNull(price) == true || 
			isNull(picture) == true || 
			isNull(stock) == true || 
			categoryKey < 0 || 
			isNull(delivaryDays) == true) {
			mv.addObject("item", itemsRepository.findById(code).get());
			mv.addObject("message", "すべての項目に入力をしてください");
			mv.setViewName("editItem");
		}

//		入力されている場合、データを更新してアイテム一覧戻る
		else {
			
			// findbyidでデータを取得
			Items item = itemsRepository.findById(code).get();
			
//			新規カテゴリーが入力された場合categoriesRepositoryに保存してcategoryKeyを更新
			if(!isNull(categoryName)) {
				Categories category = new Categories(categoryName);
				categoryKey = categoriesRepository.saveAndFlush(category).getCode();
			}


			// 更新項目に値をセット
			item.setName(name);
			item.setPrice(price);
			item.setPicture(picture);
			item.setStock(stock);
			item.setCategoryKey(categoryKey);
			item.setDelivaryDays(delivaryDays);

//		セットした値を保存
			itemsRepository.saveAndFlush(item);

			mv.addObject("items", itemsRepository.findAll());

			mv.setViewName("redirect:/itemList");
		}
		return mv;
	}

//	商品を削除する処理
	@RequestMapping("/delete/{code}")
	public ModelAndView deleteItem(@PathVariable("code") int code, ModelAndView mv) {
//		選択した商品を削除
		itemsRepository.deleteById(code);
		itemsRepository.flush();
//	処理完了メッセージを表示させるトリガー
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

	public Items getItems() {
//		カートの情報を取得
		Items item = (Items) session.getAttribute("item");
//		カート情報がない場合、カート情報の初期化
		if (item == null) {
			item = new Items();
			session.setAttribute("item", item);
		}

		return item;
	}
	
//	public void setItems(ModelAndView mv) {
////		アイテムのデータを取得
//		mv.addObject("item", itemsRepository.findById(code).get());
////		カテゴリーの情報を取得
//		mv.addObject("categoriesList", categoriesRepository.findAll());
//	}

}
