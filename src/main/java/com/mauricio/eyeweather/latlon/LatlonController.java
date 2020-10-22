package com.mauricio.eyeweather.latlon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mauricio.eyeweather.users.User;

@SessionAttributes(value = "user", types = User.class)
@Controller
public class LatlonController {

	@Autowired
	private CardService cardService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private WeatherService weatherService;

	@RequestMapping(value = { "/latlon" }, method = RequestMethod.GET)
	public String oi(HttpServletRequest request, HttpServletResponse response) {
		return "request";
	}

	@ExceptionHandler(org.springframework.web.HttpSessionRequiredException.class)
	public String HttpSessionRequiredException() {
		return "redirect:/login";
	}

	@RequestMapping(value = "/users/{userid}/locations", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Card> get(@ModelAttribute User user, @PathVariable String userid) {
		if (user.getId() == null) {
			return new ArrayList<Card>();
		} else {
			if (!user.getId().equals(userid)) {
				return new ArrayList<Card>();
			}
		}

		return cardService.get20FirstCards(userid);
	}

	@RequestMapping(value = "/users/{userid}/locations", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<Card> post(@ModelAttribute User user, @PathVariable String userid, @RequestParam String lat, @RequestParam String lon) {
		Card card = new Card();

		if (user.getId() == null) {
			return new ArrayList<Card>();
		} else {
			if (!user.getId().equals(userid)) {
				return new ArrayList<Card>();
			}
		}

		Address address = null;
		Weather weather = null;

		try {
			weather = weatherService.getWeather(lat, lon);
		} catch (Exception e) {
		}

		try {
			address = addressService.getAddress(lat, lon);
		} catch (Exception e) {
		}

		if (weather == null) {
			card.setStatus("weather server error, please try again. Please try again.");
			System.out.println("Weather Server Error!");
		} else if (address == null) {
			card.setStatus("google server error, please try again.");
			System.out.println("Google Server Error! Please try again.");
		} else {
			Date dateDate = Calendar.getInstance().getTime();

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String dateString = df.format(dateDate);

			card.setCardid(UUID.randomUUID().toString());
			card.setUserid(userid);
			card.setWeather(weather);
			card.setAddress(address);
			card.setDateDate(dateDate);
			card.setDateString(dateString);
			card.setStatus("ok");

			cardService.addCard(userid, card);
		}

		return cardService.get20FirstCards(userid);
	}

	@RequestMapping(value = "/users/{userid}/locations/{weatherid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
	@ResponseBody
	public ArrayList<Card> delete(@ModelAttribute User user, @PathVariable String userid, @PathVariable String weatherid) {
		if (user.getId() == null) {
			return new ArrayList<Card>();
		} else {
			if (!user.getId().equals(userid)) {
				return new ArrayList<Card>();
			}
		}

		cardService.delCard(userid, weatherid);
		return cardService.get20FirstCards(userid);
	}
}
