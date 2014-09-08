package com.fulbitoAndroid.clases;

public class SingletonUsuarioLogueado {
	private static Usuario usrLogueado;     
	   
	public static void initInstance()
	{
		if (usrLogueado == null)
		{
			//Creamos la instancia
			usrLogueado = new Usuario();		
		}
	}

	public static Usuario getInstance()
	{
		//Retornamos la instancia
		return usrLogueado;
	}
}
