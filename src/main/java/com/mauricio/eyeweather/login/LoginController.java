package com.mauricio.eyeweather.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mauricio.eyeweather.users.User;
import com.mauricio.eyeweather.users.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@PostConstruct
	public void init() {
		userService.load();
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "password", required = true) String password, HttpSession session) {
		User user = userService.getUser(username, password);

		if (user != null) {
			session.setAttribute("user", user);

			return "redirect:/latlon";
		} else {
			return "redirect:/login?msg=1";
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.setAttribute("user", null);
		return "redirect:/login?msg=2";
	}

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}
}