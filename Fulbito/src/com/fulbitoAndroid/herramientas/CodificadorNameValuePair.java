/* ----------------------------------------------------------------------------- 
Nombre: 		CodificadorNameValuePair
Descripción:	Clase que recibe objetos de la aplicación y los codifica dentro
				de Listas de NameValuePair para poder enviarlos como paráemtros
				a los WebServices.

Log de modificaciones:

Fecha		Autor		Descripción
06/09/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.herramientas;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fulbitoAndroid.admUsuario.WebServiceUsuario;
import com.fulbitoAndroid.clases.Usuario;

public class CodificadorNameValuePair {
	//constructor
	public CodificadorNameValuePair(){}
		
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Login
	public List<NameValuePair> CodificarNVP_Login(Usuario cUsrLogin)
	{
		List<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
		listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_LOGIN_PAR_CORREO, cUsrLogin.getEmail()));
		listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_LOGIN_PAR_CLAVE, cUsrLogin.getPassword()));
					
		return listaParametros;
	}
	
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Registrar
	public List<NameValuePair> CodificarNVP_Registrar(Usuario cUsrRegistrar)
	{
		List<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
		listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_REGISTRAR_PAR_ALIAS, cUsrRegistrar.getAlias()));
		listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_REGISTRAR_PAR_CORREO, cUsrRegistrar.getEmail()));
		listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_REGISTRAR_PAR_CLAVE, cUsrRegistrar.getPassword()));
					
		return listaParametros;
	}
}
