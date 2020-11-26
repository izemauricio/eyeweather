package com.mauricio.eyeweather.latlon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@Service
public class WeatherService {
	
	class MetaLocation {
		public String distance;
		public String title;
		public String location_type;
		public int woeid;
		public String latt_long;
	}
	
	class ConsolidatedWeather {
		String id;
		String weather_state_name;
		float min_temp;
		float max_temp;
		float the_temp;
		float air_pressure;
		float humidity;
		float wind_speed;
		float wind_direction;
		String applicable_date;
		String wind_direction_compass;
	}
	
	class MetaWeather {
		public List<ConsolidatedWeather> consolidated_weather;
	}

	
	private static String fetchJson(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        InputStream is = url.openStream();
	        reader = new BufferedReader(new InputStreamReader(is)); // new InputStreamReader(is, Charset.forName("UTF-8")
	        StringBuffer buffer = new StringBuffer(); // or StringBuilder sb = new StringBuilder();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read);  // sb.append((char) cp);
	        return buffer.toString(); // sb.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}

	@Deprecated
	public String getWOID(String lat, String lon) {
		try {
			String WOID = "";

			URI uri = new URIBuilder().setScheme("https").setHost("www.metaweather.com/api/location/search/?lattlong=")
					.setPath(lat + "," + lon).build();

			System.out.println("Weather URI: " + uri.toString());

			HttpGet httpget = new HttpGet(uri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
			httpget.setConfig(requestConfig);
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			HttpEntity result = response1.getEntity();
			InputStream stream = result.getContent();

			stream.close();
			httpclient.close();

			return WOID;
		} catch (JsonGenerationException e) {
			System.out.println("ERRRO JsonGenerationException " + e.getMessage());
		} catch (JsonMappingException e) {
			System.out.println("ERRRO JsonMappingException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("ERRRO IOException " + e.getMessage());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";

	}
	
	@Deprecated
	private String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
		}
		return sb.toString();
	  }
	
	@Deprecated
	  public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		  String jsonText = this.readAll(rd);
		  JSONObject json = new JSONObject(jsonText);
		  return json;
		} finally {
		  is.close();
		}
	  }

	public Weather getWeather(String lat, String lon) throws URISyntaxException, ClientProtocolException, IOException, Exception {
		System.out.println("WeatherService.getWeather -----------------------------");
	
		Weather weather = new Weather();
		MetaLocation[] locations = null;
		try {
			URI uri = new URIBuilder().setScheme("https").setHost("www.metaweather.com/api/location/search/?lattlong=").setPath(lat+","+lon).build();
			String json = WeatherService.fetchJson(uri.toString());
			Gson gson = new Gson();
			locations = gson.fromJson(json, MetaLocation[].class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			if(locations != null && locations.length>0) {
				int woeid = locations[0].woeid;
				URI uri = new URIBuilder().setScheme("https").setHost("www.metaweather.com/api/location/").setPath(Integer.toString(woeid)).build();
				String json = WeatherService.fetchJson(uri.toString());
				Gson gson = new Gson();
				MetaWeather weathers = gson.fromJson(json, MetaWeather.class);
				if(weathers.consolidated_weather.size() > 0) {
					ConsolidatedWeather cw = weathers.consolidated_weather.get(0);
					System.out.println("the_temp = "+cw.the_temp);
					weather.status = "OK";
					weather.temp =String.format("%.2f", cw.the_temp) ;  
					weather.humi = String.format("%.2f", cw.humidity) ;
					weather.pres = String.format("%.2f", cw.air_pressure) ;
					weather.minTemp = String.format("%.2f", cw.min_temp) ; 
					weather.maxTemp = String.format("%.2f", cw.max_temp) ; 
					weather.windSpeed = String.format("%.2f", cw.wind_speed) ;
					weather.lat = lat;
					weather.lon = lon;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
            
		
		/*
		 Weather weathers = null;
		//URI uri = new URIBuilder().setScheme("http").setHost("forecast.weather.gov/MapClick.php?").setPath("lat=" + lat + "&lon=" + lon + "&FcstType=json").build();
		//URI uri = new URIBuilder().setScheme("https").setHost("api.weather.gov/points/").setPath(lat+","+lon).build();
		URI uri = new URIBuilder().setScheme("https").setHost("www.metaweather.com/api/location/search/?lattlong=").setPath(lat+","+lon).build();
		System.out.println("WEATHER URI: "+uri.toString());
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget);
		HttpEntity result = response1.getEntity();
		InputStream inputStream = result.getContent();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = bufferedReader.readLine()) != null) {
		    content.append(inputLine);
		    System.out.println("LINE: "+inputLine);
		}
		
		bufferedReader.close();
		httpclient.close();
		 */
		
		
		// faz fetch no server
		// pega primeiro item do array de cidades
		// pega woid da cidade
		// pega temp da cidade
		// monta objeto weather
		// retorna weather

		return weather;
	}
}
