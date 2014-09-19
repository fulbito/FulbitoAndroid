package com.fulbitoAndroid.herramientas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fulbitoAndroid.fulbito.FulbitoException;

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
	
	//metodo para invocar a un WebService POST con parametros JSON
	public String sWebPost(String sNombreMetodo, JSONObject jsonParametro) throws FulbitoException
	{	
		httpPost = new HttpPost(sWebServiceUrl + sNombreMetodo);
		response = null;
		
		try 
		{
			/*
			StringEntity s = new StringEntity(jsonParametro.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			httpPost.setEntity(s);
			*/
			httpPost.setEntity(new StringEntity(jsonParametro.toString()));
		} 
		catch (UnsupportedEncodingException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebPost", e.getMessage());
		}
		
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
		
		try 
		{
			response = httpClient.execute(httpPost, localContext);
			if (response != null) 
			{
				ret = EntityUtils.toString(response.getEntity());
			}			
		} 
		catch (Exception e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebPost", e.getMessage());
		}
		
		return ret;
	}
	
	//metodo para invocar a un WebService POST con parametros NameValuePair
	public String sWebPost(String sNombreMetodo, List<NameValuePair> nvpParametros) throws FulbitoException
	{	
		httpPost = new HttpPost(sWebServiceUrl + sNombreMetodo);
		response = null;		
		
		try 
		{
			httpPost.setEntity(new UrlEncodedFormEntity(nvpParametros));
		} 
		catch(UnsupportedEncodingException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebPost", e.getMessage());
		}
		
		try 
		{
			response = httpClient.execute(httpPost, localContext);
			
			if(response != null) 
			{
				ret = EntityUtils.toString(response.getEntity());
			}
		}
		catch (Exception e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebPost", e.getMessage());
		}
		
		return ret;
	}

	//metodo para invocar a un WebService GET con parametros almacenados en un
	public String sWebGet(String sNombreMetodo, List<NameValuePair> nvpParametros) throws FulbitoException
	{
		String getUrl = sWebServiceUrl + sNombreMetodo;
		
		for(int i = 0; i < nvpParametros.size(); i++)
		{
			/*if(i == 0){
				getUrl += "?";
			}
			else{
				getUrl += "&";
			}*/
			
			getUrl += "/";
			
			try 
			{
				getUrl += nvpParametros.get(i).getName() + "=" + URLEncoder.encode(nvpParametros.get(i).getValue(), "UTF-8");
			} 
			catch (UnsupportedEncodingException e)
			{
				//Lanzamos una excepcion
				throw new FulbitoException("WebService::sWebGet", e.getMessage());
			}
		}
		
		httpGet = new HttpGet(getUrl);

		try 
		{
			response = httpClient.execute(httpGet);
		} 
		catch (Exception e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebGet", e.getMessage());
		}
		 
		try 
		{
			ret = EntityUtils.toString(response.getEntity());
		} 
		catch (IOException e) 
		{
			//Lanzamos una excepcion
			throw new FulbitoException("WebService::sWebGet", e.getMessage());
		}

		return ret;
	}
}

