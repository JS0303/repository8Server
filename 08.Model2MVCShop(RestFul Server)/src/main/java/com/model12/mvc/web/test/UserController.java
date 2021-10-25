package com.model12.mvc.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.service.domain.User;

@Controller
@RequestMapping("/user/*")
public class UserController {
	///Field
	///Constructor
	public UserController() {
		System.out.println("::UserController default Constructor call");
	}
	
	
	@RequestMapping("testView")
	public ModelAndView testView() {
		return new ModelAndView("/test/testView.jsp");
	}
	
	@RequestMapping(value = "getUser/{userId}", method = RequestMethod.GET)
	public ModelAndView getUser(@PathVariable String userId) {
		System.out.println();
		System.out.println(userId);
		User user = new User();
		user.setUserId(userId);
		
		return new ModelAndView("/test/testView.jsp", "user", user);
	}
}
