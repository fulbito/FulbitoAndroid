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
	
	//Recibe un objeto Usuario y codifica el JSON que necesita el WebService de Registrar
		public List<NameValuePair> CodificarNVP_ModDatosUsr(Usuario cUsrRegistrar)
		{
			List<NameValuePair> listaParametros = new ArrayList<NameValuePair>();
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_ID, Integer.toString(cUsrRegistrar.getId())));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_REGISTRAR_PAR_ALIAS, cUsrRegistrar.getAlias()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_CORREO, cUsrRegistrar.getEmail()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_CLAVE, cUsrRegistrar.getPassword()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_NACIM, cUsrRegistrar.getFechaNacimiento()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_UBIC, cUsrRegistrar.getUbicacion()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_LAT, cUsrRegistrar.getUbicacionLatitud()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_LONG, cUsrRegistrar.getUbicacionLongitud()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_SEXO, cUsrRegistrar.getSexo()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_TEL, cUsrRegistrar.getTelefono()));
			listaParametros.add(new BasicNameValuePair(WebServiceUsuario.S_WS_MOD_DATOS_PAR_RADIO, Float.toString(cUsrRegistrar.getRadioBusqueda())));
						
			return listaParametros;
		}
}
