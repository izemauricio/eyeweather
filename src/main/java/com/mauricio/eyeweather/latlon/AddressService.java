package com.mauricio.eyeweather.latlon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class AddressService {
	
	class MetaAddress {
		public String countryName;
		public String countryCode;
		public String principalSubdivision;
		public String city;
		public String locality;
	}
	
	@Deprecated
	public void getAddressOld() {
		/*
		 Address address = null;
		 // mount uri
		//URI uri = new URIBuilder().setScheme("http").setHost("maps.googleapis.com/maps/api/geocode/json?").setPath("latlng=" + lat + "," + lon + "&sensor=false").build();
		URI uri = new URIBuilder().setScheme("https").setHost("nominatim.openstreetmap.org/reverse?").setPath("lat=" + lat + "&lon=" + lon + "&format=json").build();
		System.out.println("URI construido"+uri.toString());
		
		// make request
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget);
		HttpEntity result = response1.getEntity();
		InputStream inputStream = result.getContent();
		
		// json string extraction using fasterxml deserialization
		try {
			ObjectMapper mapper = new ObjectMapper();
			address = mapper.readValue(inputStream, new TypeReference<Address>() {});
		} catch (JsonGenerationException e) {
			System.out.println("ERRRO JsonGenerationException "+e.getMessage());
		} catch (JsonMappingException e) {
			System.out.println("ERRRO JsonMappingException "+e.getMessage());
		} catch (IOException e) {
			System.out.println("ERRRO IOException "+e.getMessage());
		}
		

		inputStream.close();
		httpclient.close();
		 * */
		
		/*
		 // input stream to string para jsonparser
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")); 
		StringBuilder responseStrBuilder = new StringBuilder();
		String inputStr;
		while ((inputStr = streamReader.readLine()) != null) {
			System.out.println(inputStr);
			responseStrBuilder.append(inputStr);
		}
		System.out.println("RAW JSON:"+responseStrBuilder.toString());
		String road = JsonPath.parse(responseStrBuilder.toString()).read("$.address.road");
		System.out.println("ROAAAAAD: "+road);
		 */
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
	
	public Address getAddress(String lat, String lon) throws URISyntaxException, ClientProtocolException, IOException {
		System.out.println("AddressService.getAddress -----------------------------");

		
		Address address = new Address();
		try {
			String uri = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude="+lat+"&longitude="+lon+"&localityLanguage=en";

			String json = AddressService.fetchJson(uri);
			Gson gson = new Gson();
			MetaAddress addr = gson.fromJson(json, MetaAddress.class);
			address.status = "OK";
			address.fulladdr = "" + addr.locality + ", " + addr.city +  ", " +addr.principalSubdivision + ", " + addr.countryName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return address;
	}
}
