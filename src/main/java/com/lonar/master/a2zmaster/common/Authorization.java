package com.lonar.master.a2zmaster.common;

import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lonar.master.a2zmaster.model.AuthTokenInfo;



@Component
//@PropertySource({ "classpath:persistence.properties" })
public class Authorization {

	@Autowired
	private Environment env;
	
	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}
	
	private HttpHeaders getHeadersWithClientCredentials() {
		//String plainClientCredentials = "my-trusted-client:secret";
		String plainClientCredentials = "mobile:pin";
		String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
		HttpHeaders headers = getHeaders();
		headers.add("Authorization", "Basic " + base64ClientCredentials);
		return headers;
	}
	
	public  boolean  checkIsValidateToken(String token ) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
			String AUTH_SERVER_URI = env.getProperty("authorization_url");
			ResponseEntity<Object> response = restTemplate.exchange(
					AUTH_SERVER_URI+"/oauth/check_token?token="+token ,
					HttpMethod.GET, request, Object.class);
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println( "MAP = " +map  );
			return (Boolean) map.get("active");
		} catch(Exception e ) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String QPM_PASSWORD_GRANT = "?grant_type=password&";
	
	public  AuthTokenInfo  getNewToken(String username, String password ) {
		try {
			
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
			// AUTH_SERVER_URI + QPM_PASSWORD_GRANT + "username=" + Username + "&password=" + Password,
			String AUTH_SERVER_URI = env.getProperty("authorization_url");
			ResponseEntity<Object> response = restTemplate.exchange(
					AUTH_SERVER_URI+"/oauth/token"+QPM_PASSWORD_GRANT + "username=" + username + "&password=" + password  ,
					HttpMethod.POST, request, Object.class);
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println( "MAP = " +map  );
			
			AuthTokenInfo tokenInfo = null;

			if (map != null) {
				tokenInfo = new AuthTokenInfo();
				tokenInfo.setAccess_token((String) map.get("access_token"));
				tokenInfo.setToken_type((String) map.get("token_type"));
				//tokenInfo.setRefresh_token((String) map.get("refresh_token"));
				// tokenInfo.setExpires_in((int) map.get("expires_in"));
				tokenInfo.setScope((String) map.get("scope"));
			} else {
				// System.out.println("No user exist----------");
			}
			return tokenInfo;
			
		} catch(Exception e ) {
			e.printStackTrace();
		}
		return null;
	} 
	
	
}
