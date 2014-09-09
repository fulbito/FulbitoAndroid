/* ----------------------------------------------------------------------------- 
Nombre: 		CodificadorNameValuePair
Descripci�n:	Clase que recibe objetos de la aplicaci�n y los codifica dentro
				de Listas de NameValuePair para poder enviarlos como par�emtros
				a los WebServices.

Log de modificaciones:

Fecha		Autor		Descripci�n
06/09/2014	MAC			Creaci�n
----------------------------------------------------------------------------- */
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
	
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Registrar
	public List<NameValuePair> CodificarNVP_Registrar(Usuario cUsrRegistrar)
	{
		List<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
		listaParametros.add(new BasicNameValuePair("alias", cUsrRegistrar.getAlias()));
		listaParametros.add(new BasicNameValuePair("correo", cUsrRegistrar.getEmail()));
		listaParametros.add(new BasicNameValuePair("password", cUsrRegistrar.getPassword()));
					
		return listaParametros;
	}
}