package com.fulbitoAndroid.clases;

public class AppSettings {
	
	private static final String PATH_MEDIA_PERFIL = "/Fulbito/Media/Perfil";
	private static final String PATH_MEDIA_CACHE = "/Fulbito/Media/Cache";
	private String sMediaPerfilPath;
	private String sMediaCachePath;
	private String sBasePath;
	private String sLocalAvatarPath;
	private String sRemoteAvatarPath;
	
	//Constructor Generico
	public AppSettings ()
	{
		sMediaPerfilPath=PATH_MEDIA_PERFIL;
		sMediaCachePath=PATH_MEDIA_CACHE;
		sLocalAvatarPath = "";
		sRemoteAvatarPath = "";
	}
	//Constructor por copia
	public AppSettings (AppSettings ap)
	{
		sMediaPerfilPath=ap.getsMediaPerfilPath();
		sMediaCachePath=ap.getsMediaCachePath();
		sLocalAvatarPath=ap.getsLocalAvatarPath();
		sRemoteAvatarPath=ap.getsRemoteAvatarPath();
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
	public String getsLocalAvatarPath() {
		return sLocalAvatarPath;
	}
	public void setsLocalAvatarPath(String sLocalAvatarPath) {
		this.sLocalAvatarPath = sLocalAvatarPath;
	}
	public String getsRemoteAvatarPath() {
		return sRemoteAvatarPath;
	}
	public void setsRemoteAvatarPath(String sRemoteAvatarPath) {
		this.sRemoteAvatarPath = sRemoteAvatarPath;
	}
	
	
	
}
