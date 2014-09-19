package com.fulbitoAndroid.fulbito;

import android.util.Log;

public class FulbitoException extends Exception{
	public FulbitoException(String tag, String message) {
        super(message);
        if(message.length() != 0) {
        	 Log.e(tag, message);
        }       
    }
}
