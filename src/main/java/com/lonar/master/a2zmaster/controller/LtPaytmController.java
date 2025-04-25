//package com.lonar.paytm;
package com.lonar.master.a2zmaster.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.Status;
import com.paytm.pg.merchant.PaytmChecksum;


@RestController
@RequestMapping("/api/paytm")
public class LtPaytmController implements CodeMaster {

	//final String  m_id = "srrEMy96162474961972"; //Arnish  //   prod = "exABPI69807989087428"  // dev ="srrEMy96162474961972"
	 //final String  m_key = "VK#PtOwh0JR1JMUM";  //Arnish
	
	@Autowired
	private Environment env;
	
	
	@PostMapping(value = "/checksum", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Status getTransactionDetails(@RequestBody LtPaytmRequest paytmRequest) {
	
		String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
		String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
		
		  try {
			  Status status = new Status();
			  
			  JSONObject paytmParams = new JSONObject();

			  JSONObject body = new JSONObject();
			  body.put("requestType", "Payment");
			  body.put("mid", m_id );
			  body.put("websiteName", "DEFAULT");
			  body.put("orderId", paytmRequest.getOrderNumber());
			  body.put("callbackUrl", paytmRequest.getCallbackUrl() );
			  body.put("requestTimestamp",new Date());
			  
			  JSONObject txnAmount = new JSONObject();
			  txnAmount.put("value", paytmRequest.getAmount());
			  txnAmount.put("currency", "INR");

			  JSONObject userInfo = new JSONObject();
			  userInfo.put("custId", paytmRequest.getCustomerId()); 
			  body.put("txnAmount", txnAmount);
			  body.put("userInfo", userInfo);

			  String checksum = PaytmChecksum.generateSignature(body.toString(), m_key);

			  JSONObject head = new JSONObject();
			  head.put("signature", checksum);

			  paytmParams.put("body", body);
			  paytmParams.put("head", head);

			  String post_data = paytmParams.toString();

			  System.out.println("postData"+post_data);
			  /* for Staging */
			  
			  //URL url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid="+m_id+"&orderId="+paytmRequest.getOrderNumber());

			  /* for Production */
			  URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid="+m_id+"&orderId="+paytmRequest.getOrderNumber());

			 // System.out.println("url"+url);
			  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			  connection.setRequestMethod("POST");
			  connection.setRequestProperty("Content-Type", "application/json");
			  connection.setDoOutput(true);
			  DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
			  requestWriter.writeBytes(post_data);
			  requestWriter.close();
			  String responseData = "";
			  InputStream is = connection.getInputStream();
			  BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
			  StringBuilder jsonString = new StringBuilder();
			  
			  if ((responseData = responseReader.readLine()) != null) {
				    System.out.append("Response: " + responseData);
				    jsonString.append(responseData);
				  System.out.println("Response afetr"+responseData.contains("Success") +"  ..."+responseData.indexOf("Success"));
			  }
			  
			  System.out.println("Response: " + responseData);
			  JSONObject jsonObj = new JSONObject(jsonString.toString());
			 // System.out.println("jsonobject"+jsonObj);
			 jsonObj.remove("head");
			 JSONObject jsonObj1 = (JSONObject) jsonObj.get("body");
			  responseReader.close();
			if(responseData.contains("Success")) {
				status.setCode(1);
				status.setMessage("SUCCESS");
				status.setData(jsonObj1.get("txnToken"));
			}else {
				status.setCode(0);
				status.setMessage("Fail");
				status.setData(null);
			}
			  
			  return status;
	        	//OkHttpClient httpClient = new OkHttpClient();
	            //StringBuffer GET_URL = new StringBuffer("https://api.msg91.com/api/v5/otp?authkey=331742AH2d0nK8dp85fce5917P1");
	            
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		return null;
	}
	
	
	@PostMapping(value = "/checksumWeb", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Status getCheckSum(@RequestBody LtPaytmRequest paytmRequest) {
		
		
		  try {
			  String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
			  String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
				//System.out.println("m_id = " + m_id );
				//System.out.println("m_id = " + m_key );
			  Status status = new Status();
			  
			 // JSONObject paytmParams = new JSONObject(); //#RQAGfZD87KeTejg

			  
			  
			//  {"head":{"signature":"vrwcWjABVEapWY84AaMFPjExw5bqG0hszz6rWMNVzPIN+sU9LphPYyrxXJC8sj0"
			 // 		+ "nXmvuQZsIIXXrP89zO/DhWLftBO4k4ovmdTGM9bvd++M="},
			//	  "body":{"requestType":"Payment","
			//	  		+ ""mid":"RUhezf05193204406731"
			       //,"orderId":"ORD_PAYTM_20211221105711","
			//	  				+ ""websiteName":"DEFAULT","
			//	  						+ ""txnAmount":{"value":"1","currency":"INR"},"
			//	  								+ ""userInfo":{"custId":"CUST001"},"
		     //    + ""callbackUrl":"http://10.142.51.55/PaytmTestFlow/NativeFlow/pgResponse.php"}
			  
			  
			  JSONObject body = new JSONObject();
			  body.put("requestType", "Payment");
			  body.put("mid", m_id);
			  body.put("websiteName", "DEFAULT");
			  body.put("orderId", paytmRequest.getOrderNumber());
			  body.put("callbackUrl", paytmRequest.getCallbackUrl() );
			 // body.put("requestTimestamp",new Date());

			  
			  JSONObject txnAmount = new JSONObject();
			  txnAmount.put("value", paytmRequest.getAmount());
			  txnAmount.put("currency", "INR");
 
			  JSONObject userInfo = new JSONObject();
			  userInfo.put("custId", paytmRequest.getCustomerId()); 
			  body.put("txnAmount", txnAmount);
			  body.put("userInfo", userInfo);

			  String checksum = "";
			  try {
				   checksum = PaytmChecksum.generateSignature(body.toString(), m_key);
				  //checksum = PaytmChecksum.t (body.toString(), m_key);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
					
			if(!checksum.isEmpty()) {
				status.setCode(1);
				status.setMessage("SUCCESS");
				status.setData(checksum);
			}else {
				status.setCode(0);
				status.setMessage("Fail");
				status.setData(null);
			}
			  
			 return status;
	        	//OkHttpClient httpClient = new OkHttpClient();
	            //StringBuffer GET_URL = new StringBuffer("https://api.msg91.com/api/v5/otp?authkey=331742AH2d0nK8dp85fce5917P1");
	            
		  }catch(Exception e) {
			  e.printStackTrace();
		  }
		return null;
	}

	
	
	//@PostMapping(value = "/refund", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RefundResponseDto refundAgainstCancleOrder(String orderNumber,String txnId, String payAmount) {
		try {
			String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
			String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
			
			
			String refId =  new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
		//Status status = new Status();
		JSONObject paytmParams = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("mid", m_id);
		body.put("txnType", "REFUND");
		body.put("orderId", orderNumber);
		body.put("txnId",txnId);
		//body.put("refId", new Date());
		body.put("refId", refId);
		body.put("refundAmount",payAmount);

		/*
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
		*/
		String checksum = PaytmChecksum.generateSignature(body.toString(), m_key);

		JSONObject head = new JSONObject();
		head.put("signature", checksum);

		paytmParams.put("body", body);
		paytmParams.put("head", head);

		String post_data = paytmParams.toString();

		System.out.println("post_data"+post_data);
		
		/* for Staging */
	//	URL url = new URL("https://securegw-stage.paytm.in/refund/apply");

		/* for Production */
		// URL url = new URL("https://securegw.paytm.in/refund/apply?mid=NUTSNF46016776613052&orderId="+paytmRequest.getOrderNumber());
			URL url = new URL("https://securegw.paytm.in/refund/apply");
		
		try {
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);

		    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		    requestWriter.writeBytes(post_data);
		    requestWriter.close();
		    String responseData = "";
		    InputStream is = connection.getInputStream();
		    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder jsonString = new StringBuilder();
		    
		    if ((responseData = responseReader.readLine()) != null) {
		        System.out.append("Response: " + responseData);
		        jsonString.append(responseData);
		    }
		    JSONObject jsonObj = new JSONObject(jsonString.toString());
			 // System.out.println("jsonobject"+jsonObj);
			 jsonObj.remove("head");
			 JSONObject jsonObj1 = (JSONObject) jsonObj.get("body");
			 JSONObject jsonObj2 = (JSONObject) jsonObj1.get("resultInfo");
			 RefundResponseDto response = new RefundResponseDto();
			 response.setOrderId(jsonObj1.get("orderId").toString());
			 if(jsonObj1.has("refId")) {
			 response.setRefId(jsonObj1.get("refId").toString());
			 }
			 if(jsonObj1.has("refundId")) {
			 response.setRefundId(jsonObj1.get("refundId").toString());
			 }
			 response.setTxnId(jsonObj1.get("txnId").toString());
			 response.setRefundStatus(jsonObj2.get("resultStatus").toString());
			 response.setRemark(jsonObj2.get("resultMsg").toString());
			// System.out.println("response"+response);
		    responseReader.close();
		    return response;
		} catch (Exception exception) {
		    exception.printStackTrace();
		}
		}catch (Exception exception) {
		    exception.printStackTrace();
		}
		return null;
	}
	
	//@PostMapping(value = "/refundStatusApi", consumes = MediaType.APPLICATION_JSON_VALUE)
	public RefundResponseDto refundStatusApi(String orderNumber, String refId) {
	//public String refundStatusApi(@RequestBody LtPaytmRequest paytmRequest) {
		JSONObject paytmParams = new JSONObject();
try {
	String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
	String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
	
		JSONObject body = new JSONObject();
		
		body.put("mid", m_id);
		body.put("orderId", orderNumber);
		body.put("refId", refId);
		
		//body.put("orderId", paytmRequest.getOrderNumber());
		//body.put("refId", paytmRequest.getRefId());
		
		/*
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
		*/
		
		String checksum = PaytmChecksum.generateSignature(body.toString(), m_key);
		
		JSONObject head = new JSONObject();
		head.put("signature", checksum);

		paytmParams.put("body", body);
		paytmParams.put("head", head);

		String post_data = paytmParams.toString();

		/* for Staging */
		//URL url = new URL("https://securegw-stage.paytm.in/v2/refund/status");

		/* for Production */
		 URL url = new URL("https://securegw.paytm.in/v2/refund/status");

		try {
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);

		    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		    requestWriter.writeBytes(post_data);
		    requestWriter.close();
		    String responseData = "";
		    InputStream is = connection.getInputStream();
		    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder jsonString = new StringBuilder();
		    
		    if ((responseData = responseReader.readLine()) != null) {
		        System.out.append("Response: " + responseData);
		        jsonString.append(responseData);
		    }
		    JSONObject jsonObj = new JSONObject(jsonString.toString());
			 // System.out.println("jsonobject"+jsonObj);
			 jsonObj.remove("head");
			 JSONObject jsonObj1 = (JSONObject) jsonObj.get("body");
			 JSONObject jsonObj2 = (JSONObject) jsonObj1.get("resultInfo");
			 RefundResponseDto response = new RefundResponseDto();
		
			 response.setRefundStatus(jsonObj2.get("resultStatus").toString());
			 response.setRemark(jsonObj2.get("resultMsg").toString());
			// System.out.println("response"+response);
		    responseReader.close();
		    return response;
		} catch (Exception exception) {
		    exception.printStackTrace();
		}
}catch (Exception exception) {
    exception.printStackTrace();
}
return null;
	
	}
	
	@PostMapping(value = "/refundListApi", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String refundListApi (@RequestBody LtPaytmRequest paytmRequest) {
		try {
			String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
			String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
			
		JSONObject paytmParams = new JSONObject();

		JSONObject body = new JSONObject();
		body.put("mid", m_id);
		body.put("isSort", "true");
		body.put("startDate", "2021-03-01T00:34:00+05:30");
		body.put("endDate", "2021-03-12T17:35:24+05:30");
		body.put("pageSize", 10);
		body.put("pageNum", 1);

		/*
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
		*/
		String checksum = PaytmChecksum.generateSignature(body.toString(),m_key);

		JSONObject head = new JSONObject();
		head.put("tokenType", "CHECKSUM");
		head.put("clientId", "C11");
		head.put("signature", checksum);

		paytmParams.put("body", body);
		paytmParams.put("head", head);

		String post_data = paytmParams.toString();

		/* for Staging */
		//URL url = new URL("https://securegw-stage.paytm.in/merchant-passbook/api/v1/refundList");

		/* for Production */
		 URL url = new URL("https://securegw.paytm.in/merchant-passbook/api/v1/refundList");

		try {
		    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setDoOutput(true);

		    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		    requestWriter.writeBytes(post_data);
		    requestWriter.close();
		    String responseData = "";
		    InputStream is = connection.getInputStream();
		    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		    if ((responseData = responseReader.readLine()) != null) {
		        System.out.append("Response: " + responseData);
		        
		    }
		    responseReader.close();
		    return responseData;
		} 
		
		catch (Exception exception) {
		    exception.printStackTrace();
		}
		}catch (Exception exception) {
		    exception.printStackTrace();
		}
		return null;
	}
	
	@PostMapping(value = "/refundApiSync", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String refundApiSync (@RequestBody LtPaytmRequest paytmRequest) {
		try {
			String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
			String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
			
			String refId =  new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
	JSONObject paytmParams = new JSONObject();

	JSONObject body = new JSONObject();
	
	body.put("mid", m_id);
	body.put("txnType", "REFUND");
	body.put("orderId", paytmRequest.getOrderNumber());
	body.put("txnId",paytmRequest.getTxnId() );
	//body.put("refId", new Date());
	body.put("refId", refId);
	body.put("refundAmount",paytmRequest.getAmount());

	/*
	* Generate checksum by parameters we have in body
	* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
	* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys 
	*/
	String checksum = PaytmChecksum.generateSignature(body.toString(), m_key);

	JSONObject head = new JSONObject();
	head.put("signature", checksum);

	paytmParams.put("body", body);
	paytmParams.put("head", head);

	String post_data = paytmParams.toString();

	/* for Staging */
	//URL url = new URL("https://securegw-stage.paytm.in/refund/api/v1/refund/apply/sync");

	/* for Production */
	 URL url = new URL("https://securegw.paytm.in/refund/api/v1/refund/apply/sync");

	try {
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "application/json");
	    connection.setDoOutput(true);

	    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
	    requestWriter.writeBytes(post_data);
	    requestWriter.close();
	    String responseData = "";
	    InputStream is = connection.getInputStream();
	    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
	    if ((responseData = responseReader.readLine()) != null) {
	        System.out.append("Response: " + responseData);
	    }
	    responseReader.close();
	    return responseData;
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
		}catch (Exception exception) {
		    exception.printStackTrace();
		}
		return null;
		}

	

	@PostMapping(value = "/refundvalidateasset", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String refundValidateAsset (@RequestBody LtPaytmRequest paytmRequest) {
		try {
			String m_id = env.getProperty("paytm.m_id");// "NUTSNF46016776613052";
			String m_key = env.getProperty("paytm.m_key"); //"cG&JsEGg@IY6H#@C";
			
		/* initialize an object */
		JSONObject paytmParams = new JSONObject();

		/* body parameters */
		JSONObject body = new JSONObject();

		/* Find your MID in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys */
		body.put("mid", m_id);

		/* Enter numeric or alphanumeric unique request id */
		String refId =  new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(Calendar.getInstance().getTime());
		body.put("requestId", refId);

		/* Enter customer's Bank Account Number */
		body.put("accountNumber", "CUSTOMER_BANK_ACCOUNT_NUMBER");

		/* Enter customer's Bank IFSC Code */
		body.put("ifscCode", "CUSTOMER_BANK_IFSC_CODE");

		/* initialize an object for customer's name */
		JSONObject customerName = new JSONObject();

		/* Enter customer's first name */
		customerName.put("firstName", "CUSTOMER_FIRST_NAME");

		/* Enter customer's last name */
		customerName.put("lastName", "CUSTOMER_LAST_NAME");

		/* put customerName object in body */
		body.put("name", customerName);

		/* Enter customer's mobile number */
		body.put("mobileNo", "CUSTOMER_MOBILE_NUMBER");

		/**
		* Generate checksum by parameters we have in body
		* You can get Checksum JAR from https://developer.paytm.com/docs/checksum/
		* Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
		*/
		//String checksum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum("cG&JsEGg@IY6H#@C", body.toString());

		String checksum = PaytmChecksum.generateSignature(body.toString(), m_key);
		/* head parameters */
		JSONObject head = new JSONObject();

		/* put generated checksum value here */
		head.put("signature", checksum);

		/* prepare JSON string for request */
		paytmParams.put("body", body);
		paytmParams.put("head", head);
		String post_data = paytmParams.toString();

		/* for Staging */
		URL url = new URL("https://securegw-stage.paytm.in/userAsset/token/create?mid=YOUR_MID_HERE&requestId=UNIQUE_REQUEST_ID");

		/* for Production */
		// URL url = new URL("https://securegw.paytm.in/userAsset/token/create?mid=YOUR_MID_HERE&requestId=UNIQUE_REQUEST_ID");

		try {
		   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		   connection.setRequestMethod("POST");
		   connection.setRequestProperty("Content-Type", "application/json");
		   connection.setDoOutput(true);

		    DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
		    requestWriter.writeBytes(post_data);
		    requestWriter.close();
		    String responseData = "";
		    InputStream is = connection.getInputStream();
		    BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
		    if ((responseData = responseReader.readLine()) != null) {
		        System.out.append("Response: " + responseData);
		    }
		    // System.out.append("Request: " + post_data);
		    responseReader.close();
		    return responseData;
		} catch (Exception exception) {
		    exception.printStackTrace();
		} 
		}catch (Exception exception) {
		    exception.printStackTrace();
		}
		return null;
	}
}
