package com.lonar.master.a2zmaster.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.lonar.master.a2zmaster.model.LtMastEmailToken;

@Service
//@PropertySource({ "classpath:persistence.properties" })
public class MailSendServiceCall {

	@Autowired
	private Environment env;

	static {
		disableSslVerification();
	}

	private static void disableSslVerification() {
		try {
			// Create a trust manager that does not validate certificate chains
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {
					// TODO Auto-generated method stub

				}
			} };

			// Install the all-trusting trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}
	}

	/*
	 * private static Properties prop;
	 * 
	 * static { InputStream is = null; try { prop = new Properties(); is =
	 * ClassLoader.class.getResourceAsStream("/persistence.properties");
	 * prop.load(is); } catch (FileNotFoundException e) { e.printStackTrace(); }
	 * catch (IOException e) { e.printStackTrace(); } }
	 */

	public void callToMailService(LtMastEmailToken ltMastEmailToken) {

		try {
			String emailUrl = env.getProperty("mail_url");
			URL url = new URL(null,
					emailUrl + ltMastEmailToken.getTransactionId() + "" + "/" + ltMastEmailToken.getSupplierId());
			System.out.println("url " + url);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			print_content(con);
		} catch (Exception e) {
			System.out.println("call to email service failed");
		}

	}

	public void callToSMSService(Long transId, Long supplierId) {
		try {
			System.out.println(transId + " " + supplierId);
			try {
				System.out.println("env "+env);
				String smsUrl = env.getProperty("sms_url");
				// URL url = new URL(null, "https://192.168.1.220:9095/a2z-sms/sms/sendSms/" +
				// transId+"" + "/" + supplierId);
				URL url = new URL(null, smsUrl + transId + "" + "/" + supplierId);
				System.out.println("url " + url);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				print_content(con);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

			System.out.println("call to sms service failed");
			e.printStackTrace();
		}

	}
	
	public void refrestSysVarialbeData() {
		String serverUrl = env.getProperty("MAP_URL");
		System.out.println(" MAP_URL " + serverUrl);
		 try {			                                    
			URL url = new URL(null, serverUrl + "a2z-master/refreshMasterManagement");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			this.print_content(con);
		}catch(Exception e) {
			e.printStackTrace();
		}

		try {
			URL url = new URL(null, serverUrl + "a2z-sms/refreshSmsSrvice");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			this.print_content(con);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			URL url = new URL(null, serverUrl + "a2z-mail/refreshMailService");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			this.print_content(con);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static void print_content(HttpURLConnection con) {
		if (con != null) {

			try {

				System.out.println("****** Content of the URL ********");
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

				String input;

				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
