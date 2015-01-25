package com.fulbitoAndroid.fulbito;

import com.fulbitoAndroid.gestionDB.FulbitoSQLiteHelper;

import android.content.ContentValues;
import android.content.Context;

public class DatosConfiguracion {
	
	//TIPOS DE VISIBILIDAD
    public static final int TV_ID_PRIVADO 	= 1;
    public static final int TV_ID_PUBLICO  	= 2;    
    //TIPOS DE PARTIDO
    public static final int TP_ID_AMISTOSO 		= 1;
    public static final int TP_ID_DESAFIO_USR 	= 2;
    public static final int TP_ID_DESAFIO_EQ 	= 3;
    //TIPOS DE SELECCION PARTIDO AMISTOSO
    public static final int SELEC_ID_YO_LOS_ARMO	= 1;
    public static final int SELEC_ID_PAN_Y_QUESO 	= 2;
    public static final int SELEC_ID_DOS_JUGADORES 	= 3;
    public static final int SELEC_ID_ALEATORIO	 	= 4;

    public static String sObtenerDescTipoVisibilidad(Context context, int iIdTipoVisibilidad){
    	
    	String sDesc = "";
    	
    	switch(iIdTipoVisibilidad)
        {
        	case TV_ID_PUBLICO:
        		sDesc = context.getResources().getString(R.string.txtPartidoPublico);
        		break;
        	case TV_ID_PRIVADO:
        		sDesc = context.getResources().getString(R.string.txtPartidoPrivado);
        		break;
        }
    	
    	return sDesc;
    }
    
    public static String sObtenerDescTipoPartido(Context context, int iIdTipoPartido){
    	
    	String sDesc = "";
    	String[] arrTiposPartidos = context.getResources().getStringArray(R.array.spTipoPartidoItems);
    	
    	switch(iIdTipoPartido)
        {
        	case TP_ID_AMISTOSO:
        		sDesc = arrTiposPartidos[TP_ID_AMISTOSO - 1];
        		break;
        	case TP_ID_DESAFIO_USR:
        		sDesc = arrTiposPartidos[TP_ID_DESAFIO_USR - 1];
        		break;
        	case TP_ID_DESAFIO_EQ:
        		sDesc = arrTiposPartidos[TP_ID_DESAFIO_EQ - 1];
        		break;
        }
    	
    	return sDesc;
    }

    public static String sObtenerDescTipoSeleccion(Context context, int iIdTipoSeleccion){
    	
    	String sDesc = "";
    	
    	switch(iIdTipoSeleccion)
        {
        	case SELEC_ID_YO_LOS_ARMO:
        		sDesc = context.getResources().getString(R.string.txtYolosArmo);
        		break;
        	case SELEC_ID_PAN_Y_QUESO:
        		sDesc = context.getResources().getString(R.string.txtPanQueso);
        		break;
        	case SELEC_ID_DOS_JUGADORES:
        		sDesc = context.getResources().getString(R.string.txtDosJugadores);
        		break;
        	case SELEC_ID_ALEATORIO:
        		sDesc = context.getResources().getString(R.string.txtAleatorio);
        		break;
        }
    	
    	return sDesc;
    }
}
