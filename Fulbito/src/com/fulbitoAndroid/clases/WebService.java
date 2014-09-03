package com.fulbitoAndroid.clases;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class WebService {
	DefaultHttpClient httpClient;
	HttpContext localContext;
	private String ret;
	HttpResponse response = null;
	HttpPost httpPost = null;
	HttpGet httpGet = null;
	String sWebServiceUrl;
	
	public WebService(String sServiceName){
		
		HttpParams myParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(myParams, 10000);
		HttpConnectionParams.setSoTimeout(myParams, 10000);
		
		httpClient = new DefaultHttpClient(myParams);
		localContext = new BasicHttpContext();
		sWebServiceUrl = sServiceName;
	}
	
	//metodo para invocar a un WebService POST con parametros
	public String sWebPost(String methodName, Map<String, String> mParametros){
		/*
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	     
  		for(Map.Entry<String, String> param : mParametros.entrySet()){
  				nameValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));	  				  			
  		}
	  		
  		httpPost = new HttpPost(sWebServiceUrl + methodName);
  		response = null;
  		
  		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				
  		
  		//Log.d("Groshie", webServiceUrl + "?" + data);
  		
  		try {
  			response = httpClient.execute(httpPost, localContext);
  			
  			if (response != null) {
  				ret = EntityUtils.toString(response.getEntity());
  			}
  			
  		} catch (Exception e) {
  			//Log.e("Groshie", "HttpUtils: " + e);
  		}
  		
  		return ret;
  		*/
		
		JSONObject jsonObject = new JSONObject();
		//Agregamos los parametros a un objeto JSON
		for(Map.Entry<String, String> param : mParametros.entrySet()){
			try{
				jsonObject.put(param.getKey(), param.getValue());
			}
			catch(JSONException e){
				//Log.e("sWebPost", "JSONException : " + e);
			}			
		}
		
		httpPost = new HttpPost(sWebServiceUrl + methodName);
		response = null;
		StringEntity tmp = null;
		
		try {
			tmp = new StringEntity(jsonObject.toString(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Log.e("Groshie", "HttpUtils : UnsupportedEncodingException : "+e);
		}
		
		tmp.setContentType("application/json");
		
		httpPost.setEntity(tmp);				
		
		//Log.d("Groshie", webServiceUrl + "?" + data);
		
		try {
			response = httpClient.execute(httpPost, localContext);
			
			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());
			}
			
		} catch (Exception e) {
			//Log.e("Groshie", "HttpUtils: " + e);
		}
		
		return ret;
		//return sWebPost(methodName, jsonObject.toString(), "application/json");
	}
	/*
	private String sWebPost(String methodName, String data, String contentType){
		ret = null;
		
		//httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);
		
		httpPost = new HttpPost(sWebServiceUrl + methodName);
		response = null;
		StringEntity tmp = null;
		
		//httpPost.setHeader("User-Agent", "SET YOUR USER AGENT STRING HERE");
		httpPost.setHeader("Accept", 
				"text/html,application/xml,application/xhtml+xml,text/html;q=0.9,text/plain;q=0.8,image/png,**;q=0.5");
		
		if (contentType != null) {
			httpPost.setHeader("Content-Type", contentType);
		} else {
			httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		}
		
		try {
			tmp = new StringEntity(data,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			//Log.e("Groshie", "HttpUtils : UnsupportedEncodingException : "+e);
		}
		
		httpPost.setEntity(tmp);
		
		//Log.d("Groshie", webServiceUrl + "?" + data);
		
		try {
			response = httpClient.execute(httpPost, localContext);
			
			if (response != null) {
				ret = EntityUtils.toString(response.getEntity());
			}
			
		} catch (Exception e) {
			//Log.e("Groshie", "HttpUtils: " + e);
		}
		
		return ret;
	}
	*/
	public String sWebGet(String methodName, Map<String, String> mParametros){
		String getUrl = sWebServiceUrl + methodName;
		
		int i = 0;
		for (Map.Entry<String, String> param : mParametros.entrySet())
		{
			if(i == 0){
				getUrl += "?";
			}
			else{
				getUrl += "&";
			}
			
			try {
				getUrl += param.getKey() + "=" + URLEncoder.encode(param.getValue(),"UTF-8");
			} catch (UnsupportedEncodingException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i++;
		}
		
		httpGet = new HttpGet(getUrl);
		//Log.e("WebGetURL: ",getUrl);

		try {
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			//Log.e("Groshie:", e.getMessage());
		}
		
		// we assume that the response body contains the error message  
		try {
			ret = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			//Log.e("Groshie:", e.getMessage());
		}

		return ret;
	}
}
