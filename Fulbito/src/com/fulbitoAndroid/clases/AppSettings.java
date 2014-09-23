package com.fulbitoAndroid.clases;

public class AppSettings {

	private String sMediaPerfilPath;
	private String sMediaCachePath;
	private String sBasePath;
	private static final String PATH_MEDIA_PERFIL = "/Fulbito/Media/Perfil";
	private static final String PATH_MEDIA_CACHE = "/Fulbito/Media/Cache";
	
	//Constructor Generico
	public AppSettings ()
	{
		sMediaPerfilPath=PATH_MEDIA_PERFIL;
		sMediaCachePath=PATH_MEDIA_CACHE;
	}
	//Constructor por copia
	public AppSettings (AppSettings ap)
	{
		sMediaPerfilPath=ap.getsMediaPerfilPath();
		sMediaCachePath=ap.getsMediaCachePath();
	}
	//Getter y Setters
	public String getsMediaPerfilPath() {
		return sMediaPerfilPath;
	}
	public void setsMediaPerfilPath(String sMediaPerfilPath) {
		this.sMediaPerfilPath = sMediaPerfilPath;
	}
	public String getsMediaCachePath() {
		return sMediaCachePath;
	}
	public void setsMediaCachePath(String sMediaCachePath) {
		this.sMediaCachePath = sMediaCachePath;
	}
	public String getsBasePath() {
		return sBasePath;
	}
	public void setsBasePath(String sBasePath) {
		this.sBasePath = sBasePath;
	}
	
	
	
}
