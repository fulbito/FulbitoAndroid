/* ----------------------------------------------------------------------------- 
Nombre: 		Partido
Descripción:	Clase que contiene los datos de un partido

Log de modificaciones:

Fecha		Autor		Descripción
13/11/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.clases;

public class Partido {
	
	//Atributos privados
	private int		iId;
	private String  sNombre;
	private String  sFecha;
	private int  	iHora;
	private int  	iCantJugadores;
	private String 	sLugar;
	private Usuario	uUsuarioAdm;
	/*
	private String 	sTipoVisibilidad;
	private String 	sTipoPartido;
	private String 	sTipoPeriodicidad;
	private String 	sEstado;
	*/
	
	//Métodos públicos
	
	//Constructor por defecto
	public Partido()
	{
		this.iId 				= 0;
		this.sNombre 			= "";
		this.sFecha 			= "";
		this.iHora 				= 0;
		this.iCantJugadores 	= 0;
		this.sLugar 			= "";
		this.uUsuarioAdm 		= new Usuario();
	}
}
