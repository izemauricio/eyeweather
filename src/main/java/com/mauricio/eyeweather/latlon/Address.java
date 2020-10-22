package com.mauricio.eyeweather.latlon;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	@JsonProperty("results")
	ArrayList<Result> results;

	@JsonProperty("status")
	String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Result {

	private String formatted_address;

	@JsonCreator
	public Result(@JsonProperty("formatted_address") String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

}
