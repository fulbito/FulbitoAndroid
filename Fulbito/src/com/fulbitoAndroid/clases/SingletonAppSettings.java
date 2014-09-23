package com.fulbitoAndroid.clases;

import java.io.File;

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
	
	public static void initInstance(Context pContext)
	{
		if (appSettings == null)
		{
			//Creamos la instancia
			context = pContext;
			appSettings = new AppSettings();
			verifyDirectoryTree();
		}
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
	    		String estado = Environment.getExternalStorageState();
		    	if (Environment.MEDIA_MOUNTED.equals(estado)) 
		    	{
		    		String sExternalPath = Environment.getExternalStorageDirectory().toString();
		    		appSettings.setsBasePath(sExternalPath);
		    		File mPathMediaPerfil;
		    		File mPathMediaCache;
		    		mPathMediaPerfil = new File(sExternalPath+appSettings.getsMediaCachePath());
		    		mPathMediaPerfil.mkdirs();
		    		mPathMediaCache = new File(sExternalPath+appSettings.getsMediaPerfilPath());
		    		mPathMediaCache.mkdirs();	    		
		    	}	
	    	}
	    	catch (RuntimeException rte)
	    	{
	    		Log.d(TAG,rte.getMessage());
	    	}
		}//En el else habria que checkear que los directorios esten realmente creados		
	}

}
