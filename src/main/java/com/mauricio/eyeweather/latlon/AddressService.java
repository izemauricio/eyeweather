package com.mauricio.eyeweather.latlon;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddressService {

	public Address getAddress(String lat, String lon) throws URISyntaxException, ClientProtocolException, IOException {
		Address address = null;

		URI uri = new URIBuilder().setScheme("http").setHost("maps.googleapis.com/maps/api/geocode/json?").setPath("latlng=" + lat + "," + lon + "&sensor=false").build();
		HttpGet httpget = new HttpGet(uri);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget);
		HttpEntity result = response1.getEntity();
		InputStream stream = result.getContent();

		try {
			ObjectMapper mapper = new ObjectMapper();
			address = mapper.readValue(stream, new TypeReference<Address>() {
			});
		} catch (JsonGenerationException e) {
		} catch (JsonMappingException e) {
		} catch (IOException e) {
		}

		stream.close();
		httpclient.close();

		return address;
	}
}