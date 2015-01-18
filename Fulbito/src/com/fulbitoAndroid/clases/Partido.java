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
	//private int  	iHora;
	private String 	sHora;
	private int  	iCantJugadores;
	private String 	sLugar;
	private Usuario	uUsuarioAdm;
	private int 	iTipoVisibilidad;
	private int 	iTipoPartido;
	
	/*
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
		//this.iHora 				= 0;
		this.sHora 				= "";
		this.iCantJugadores 	= 0;
		this.sLugar 			= "";
		this.uUsuarioAdm 		= new Usuario();
		this.iTipoPartido 		= 0;
		this.iTipoVisibilidad 	= 0;
	}
	
	//Constructor de copia	
	public Partido(Partido cPartidoOrigen)
    {
		this.iId 				= cPartidoOrigen.getId();
		this.sNombre 			= cPartidoOrigen.getNombre();
	    this.sFecha 			= cPartidoOrigen.getFecha();
	    //this.iHora 				= cPartidoOrigen.getHora();
	    this.sHora 				= cPartidoOrigen.getHora();
	    this.iCantJugadores		= cPartidoOrigen.getCantJugadores();
	    this.sLugar				= cPartidoOrigen.getLugar();
	    this.uUsuarioAdm		= cPartidoOrigen.getUsuarioAdm();
	    this.iTipoPartido		= cPartidoOrigen.getTipoPartido();
	    this.iTipoVisibilidad	= cPartidoOrigen.getTipoVisibilidad();
    }

	public int getId() {
		return iId;
	}
	public void setId(int iId) {
		this.iId = iId;
	}
	public String getNombre() {
		return sNombre;
	}
	public void setNombre(String sNombre) {
		this.sNombre = sNombre;
	}
	public String getFecha() {
		return sFecha;
	}
	public void setFecha(String sFecha) {
		this.sFecha = sFecha;
	}
	/*
	public int getHora() {
		return iHora;
	}
	*/
	public String getHora() {
		return sHora;
	}
	/*
	public void setHora(int iHora) {
		this.iHora = iHora;
	}
	*/
	public void setHora(String sHora) {
		this.sHora = sHora;
	}
	public int getCantJugadores() {
		return iCantJugadores;
	}
	public void setCantJugadores(int iCantJugadores) {
		this.iCantJugadores = iCantJugadores;
	}
	public String getLugar() {
		return sLugar;
	}
	public void setLugar(String sLugar) {
		this.sLugar = sLugar;
	}
	public Usuario getUsuarioAdm() {
		return uUsuarioAdm;
	}
	public void setUsuarioAdm(Usuario uUsuarioAdm) {
		this.uUsuarioAdm = uUsuarioAdm;
	}
	public int getTipoPartido() {
		return iTipoPartido;
	}
	public void setTipoPartido(int iTipoPartido) {
		this.iTipoPartido = iTipoPartido;
	}
	public int getTipoVisibilidad() {
		return iTipoVisibilidad;
	}
	public void setTipoVisibilidad(int iTipoVisibilidad) {
		this.iTipoVisibilidad = iTipoVisibilidad;
	}
	
	//metodo de copia
  	public void vCopiar(Partido cPartidoOrigen)
  	{
  		this.iId 				= cPartidoOrigen.getId();
		this.sNombre 			= cPartidoOrigen.getNombre();
	    this.sFecha 			= cPartidoOrigen.getFecha();
	    //this.iHora 				= cPartidoOrigen.getHora();
	    this.sHora 				= cPartidoOrigen.getHora();
	    this.iCantJugadores		= cPartidoOrigen.getCantJugadores();
	    this.sLugar				= cPartidoOrigen.getLugar();
	    this.uUsuarioAdm		= cPartidoOrigen.getUsuarioAdm();
	    this.iTipoPartido		= cPartidoOrigen.getTipoPartido();
	    this.iTipoVisibilidad	= cPartidoOrigen.getTipoVisibilidad();
	}
}
