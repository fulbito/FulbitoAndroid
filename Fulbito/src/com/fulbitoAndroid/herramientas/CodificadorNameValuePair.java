package com.fulbitoAndroid.herramientas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fulbitoAndroid.clases.Usuario;

public class CodificadorNameValuePair {
	//constructor
	public CodificadorNameValuePair(){}
		
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Login
	public List<NameValuePair> CodificarNVP_Login(Usuario cUsrLogin)
	{
		List<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
		listaParametros.add(new BasicNameValuePair("correo", cUsrLogin.getEmail()));
		listaParametros.add(new BasicNameValuePair("clave", cUsrLogin.getPassword()));
					
		return listaParametros;
	}
}
