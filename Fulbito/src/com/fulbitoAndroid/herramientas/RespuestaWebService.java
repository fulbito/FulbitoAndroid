package com.fulbitoAndroid.herramientas;

public class RespuestaWebService {
	private String sError;
	private String sData;
	
	public RespuestaWebService(){
		this.sError = "";
		this.sData = "";
	}
	
	public void vSetError(String sError){
		this.sError = sError;
	}
	
	public void vSetData(String sData){
		this.sData = sData;
	}
	
	public String sGetError(){
		return this.sError;
	}
	
	public String sGetData(){
		return this.sData;
	}

}
