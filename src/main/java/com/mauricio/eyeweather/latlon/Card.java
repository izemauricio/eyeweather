package com.mauricio.eyeweather.latlon;

import java.util.Date;

public class Card {

	private String cardid;
	private String userid;
	private Weather weather;
	private Address address;
	private String dateString;
	private Date dateDate;
	private String status;

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String id) {
		this.cardid = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String id) {
		this.userid = id;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public Date getDateDate() {
		return dateDate;
	}

	public void setDateDate(Date dateDate) {
		this.dateDate = dateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		if (((Card) obj).getCardid() == this.cardid)
			return true;

		return false;
	}

}