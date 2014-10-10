package com.fulbitoAndroid.clases;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

public class SingletonAppSettings {
	private static AppSettings appSettings;
	private static Context context;
	
	static final String TAG = "SingletonAppSettings";
	//KEYS para sharedPreferences
	static final String SH_APP_PREF ="InfoApp";
	static final String KEY_FIRST_RUN = "FirstRun";
	static final String KEY_LOCAL_AVATAR = "LocalAvatarPath";
	static final String KEY_REMOTE_AVATAR = "RemoteAvatarPath";
	static final String NO_MEDIA = ".nomedia";
	
	public static AppSettings getInstance(Context pContext)
	{
		if (appSettings == null)
		{
			//Creamos la instancia
			context = pContext;
			appSettings = new AppSettings();
			//Obtenemos los datos persistidos
			SharedPreferences sp = context.getSharedPreferences(SH_APP_PREF, Context.MODE_PRIVATE);
			appSettings.setsLocalAvatarPath(sp.getString(KEY_LOCAL_AVATAR, ""));
			appSettings.setsRemoteAvatarPath(sp.getString(KEY_REMOTE_AVATAR, ""));
			verifyDirectoryTree();
			
		}
		return appSettings;
	}
	//Para cuando se llama de un fragment hay que validar el null pointer
	public static AppSettings getAppSettings()
	{
		return appSettings;		
	}
	public static File getFilesDir()
	{
		return context.getFilesDir();
	}
	
	public static void setLocalAvatarPath(String sLocalAvatarPath)
	{
		SharedPreferences sp = context.getSharedPreferences(SH_APP_PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putString(KEY_LOCAL_AVATAR, sLocalAvatarPath);
		spEditor.commit();
		appSettings.setsLocalAvatarPath(sLocalAvatarPath);
	}
	
	public static void setRemoteAvatarPath(String sRemoteAvatarPath)
	{
		SharedPreferences sp = context.getSharedPreferences(SH_APP_PREF, Context.MODE_PRIVATE);
		SharedPreferences.Editor spEditor = sp.edit();
		spEditor.putString(KEY_REMOTE_AVATAR, sRemoteAvatarPath);
		spEditor.commit();
		appSettings.setsRemoteAvatarPath(sRemoteAvatarPath);
	}
	
	private static void verifyDirectoryTree()
	{		
		SharedPreferences prefs = context.getSharedPreferences(SH_APP_PREF, Context.MODE_PRIVATE);
		
		//verifico si es la primer corrida
		boolean bFirstRun = prefs.getBoolean(KEY_FIRST_RUN, true);
		
		if(bFirstRun)
		{
	    	try
	    	{
	    		//QUIZAS PREGUNTAR SI HAY O NO UNA MEMORIA EXTERNA LA PRIMER EJECUCION
	    		String estado = Environment.getExternalStorageState();
	    		String sBasePath = "";
		    	if (Environment.MEDIA_MOUNTED.equals(estado)) 
		    	{
		    		//SI HAY UNA MEMORIA EXTERNA MONTADA
		    		sBasePath = Environment.getExternalStorageDirectory().toString();		    				    	
		    	}
		    	else
		    	{
		    		//NO HAY UNA MEMORIA EXTERNA MONTADA
		    		sBasePath = getFilesDir().getAbsolutePath();
		    	}
		    	
		    	appSettings.setsBasePath(sBasePath);
	    		File mPathMediaPerfil;
	    		File mPathMediaCache;
	    		File mNoMedia;
	    		
	    		mPathMediaPerfil = new File(sBasePath+appSettings.getsMediaCachePath());
	    		if(!mPathMediaPerfil.exists()){
	    			mPathMediaPerfil.mkdirs();
	    		}
	    		mPathMediaCache = new File(sBasePath+appSettings.getsMediaPerfilPath());
	    		if(!mPathMediaCache.exists()){
	    			mPathMediaCache.mkdirs();
		    		mNoMedia = new File(sBasePath+appSettings.getsMediaPerfilPath(),NO_MEDIA);//Excluyo la galeria
		    		mNoMedia.createNewFile();
	    		}		    	
	    	}
	    	catch (RuntimeException rte)
	    	{
	    		Log.d(TAG,rte.getMessage());
	    	}
	    	catch (FileNotFoundException fnf)
	    	{
	    		Log.d(TAG,fnf.getMessage());
	    	}
	    	catch (IOException ioe)
	    	{
	    		Log.d(TAG,ioe.getMessage());
	    	}	    	
	    	//Setear primer ejecución en FALSE	    	
		}//En el else habria que checkear que los directorios esten realmente creados		
	}

}
