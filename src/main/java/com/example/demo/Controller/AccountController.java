package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.Users;
import com.example.demo.Repository.ItemsRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.service.ItemService;

@Controller
public class AccountController {

	@Autowired
	HttpSession session;
	
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	ItemsRepository itemsRepository;
	
	@Autowired
	ItemService itemService;

//	トップページを表示
	@RequestMapping("/")
	public String Top() {
		return "index";
	}

//	新規登録画面を表示
	@RequestMapping("/newAccount")
	public String newAccount() {
		return "newAccount";
	}

//	新規登録処理
	@RequestMapping(value = "/newAccount", method = RequestMethod.POST)
	public ModelAndView newAccount(@RequestParam("name") String name, @RequestParam("address") String address,
			@RequestParam("tel") String tel, @RequestParam("email") String email, @RequestParam("pass") String pass,
			ModelAndView mv) {
//		すべてン項目に対して未入力チェック
		if (isNull(name) == true || 
			isNull(address) == true || 
			isNull(tel) == true || 
			isNull(email) == true
			|| isNull(pass) == true) {
			
			mv.addObject("message", "すべての項目に入力をしてください");
			mv.setViewName("newAccount");

		} else {
//		Usersテーブルに入力したデータを登録
			Users user = new Users(name, address, tel, email, pass);
			usersRepository.saveAndFlush(user);

			mv.addObject("name", name);
			mv.setViewName("showItem");
		}

		return mv;
	}
	
//	ログイン画面の表示
	@RequestMapping("/login")
	public String login() {
		session.invalidate();
		return "login";
	}

//	ログイン処理
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(
			@RequestParam("name") String name, 
			@RequestParam("pass") String pass,
			ModelAndView mv
			) {
//		すべてン項目に対して未入力チェック
		if (isNull(name) == true || isNull(pass) == true) {
			
			mv.addObject("message", "すべての項目に入力をしてください");
			mv.setViewName("login");

		} else {
//		Usersテーブルに一致するか確認しログインし、商品一覧表示
			Users user = usersRepository.findByNameAndPass(name, pass);
			
//		入力内容がUsersテーブルのデータと一致しなかった場合再入力させる
			
			if(user==null) {
				mv.addObject("message", "入力されたユーザー情報は登録されていません。");
				mv.setViewName("login");
			}else {
//		入力内容が登録されていた場合ログインし、商品一覧表示
				if(itemService.getAllItems() == null) {
					mv.addObject("itemList", itemService.getAllItems());
				} else {
					mv.addObject("message", "現在販売されている商品はありません");
					
				}
				session.setAttribute("userInfo", user);
				
				mv.setViewName("showItem");
			}
	
		}

		return mv;
	}

	

//	商品一覧画面を表示
//	@RequestMapping("/showItem")
//	public String showItem() {
//		return "showItem";
//	}


	public boolean isNull(String insert) {
		if (insert == null || insert.length() == 0) {
			return true;
		}
		return false;
	}
	
	
	
	@RequestMapping("/logout")
	public String logout() {
		return login();
	}
}
