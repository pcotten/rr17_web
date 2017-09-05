package com.pcotten.rr17.controllers;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pcotten.rr17.model.User;
import com.pcotten.rr17.service.UserService;
import com.pcotten.rr17.storage.service.DatabaseManager;

@Controller
public class DemoController {
	
	@Inject
	DatabaseManager manager;
	@Inject
	UserService userService;
	Boolean loginFail = false;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLogin(@ModelAttribute ("login") User user) {
		if (loginFail) {
			System.out.println("Failed login attempt");
		}
		return "login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String doLogin(@ModelAttribute ("login") User user, Model model) {
		if (user.getUsername() != null && user.getPassword() != null) {
			user = manager.login(user);
		}
		if (user != null) {

			return doUserHome(user, model);
			
		}
		
		else {
			loginFail = true;
			return "login";
		}
	}
	
	@RequestMapping(value="/userHome")
	public String doUserHome(@ModelAttribute("login") User user, Model model) {
		model.addAttribute("user", user);
		return "userHome";
	}
	
	// http://localhost:8080/rr17/newAccount.html
	@RequestMapping(value="/newAccount")
	public String newAccount(@ModelAttribute ("newAcct") User account) {
			
		return "newAccount";
	}
	
	@RequestMapping(value="/accountCreated", method=RequestMethod.POST )
	public String accountCreated(@Valid @ModelAttribute ("newAcct") User account, BindingResult result) {
		
		if(result.hasErrors()) {
			System.out.println("Form has errors");
			return "newAccount";
		}
		
		User user = null;
//		try {
//			user = userService.createUser(account);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		if (user != null && user.getId() > 0) {
			return "accountCreated";
		}
		
		else {
			System.out.println("Unable to create new user");
			return "newAccount";
		}
	}
	
	@RequestMapping(value="/urecovery")
	public String accountRecovery(@ModelAttribute ("login") User user) {
		return "urecovery";	
	}
	
	@RequestMapping(value="/acctrecovery", method=RequestMethod.POST)
	public String recoverUsername(@ModelAttribute ("login") User user, Model model) {
		
		if (user.getEmail() != null) {
			String username = manager.recoverUsername(user.getEmail());
			model.addAttribute("username", username);
		}
		return "urecovery";
		
	}
}
