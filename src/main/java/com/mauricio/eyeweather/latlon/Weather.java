package com.mauricio.eyeweather.latlon;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

	@JsonProperty("creationDate")
	private String creationDate;

	@JsonProperty("productionCenter")
	private String productionCenter;

	@JsonProperty("latitude")
	private String latitude;

	@JsonProperty("location")
	private Location location;

	@JsonProperty("data")
	Data data;

	@JsonProperty("currentobservation")
	Measure currentobservation;

	public Measure getMeasure() {
		return currentobservation;
	}

	public void setMeasure(Measure measure) {
		this.currentobservation = measure;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getProductionCenter() {
		return productionCenter;
	}

	public void setProductionCenter(String productionCenter) {
		this.productionCenter = productionCenter;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Location {

	private String latitude;
	private String longitude;

	@JsonCreator
	public Location(@JsonProperty("latitude") String latitude, @JsonProperty("longitude") String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Data {

	private ArrayList<String> temperature;
	private ArrayList<String> forecast;

	public ArrayList<String> getForecast() {
		return forecast;
	}

	public void setForecast(ArrayList<String> forecast) {
		this.forecast = forecast;
	}

	@JsonCreator
	public Data(@JsonProperty("temperature") ArrayList<String> temperature, @JsonProperty("text") ArrayList<String> forecast) {
		this.temperature = temperature;
		this.forecast = forecast;
	}

	public ArrayList<String> getTemperature() {
		return temperature;
	}

	public void setTemperature(ArrayList<String> temperature) {
		this.temperature = temperature;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Measure {

	String date;
	String temp;
	String wind;
	String relh;
	String weat;

	public String getWeat() {
		return weat;
	}

	public void setWeat(String weat) {
		this.weat = weat;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getRelh() {
		return relh;
	}

	public void setRelh(String relh) {
		this.relh = relh;
	}

	@JsonCreator
	public Measure(@JsonProperty("Date") String date, @JsonProperty("Temp") String temp, @JsonProperty("Winds") String wind, @JsonProperty("Relh") String relh, @JsonProperty("Weather") String weat) {
		this.date = date;
		this.temp = temp;
		this.wind = wind;
		this.relh = relh;
		this.weat = weat;
	}
}
