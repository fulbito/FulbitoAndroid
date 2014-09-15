/* ----------------------------------------------------------------------------- 
Nombre: 		Usuario
Descripción:	Clase que contiene los datos de un usuario de la aplicación
				Fulbito

Log de modificaciones:

Fecha		Autor		Descripción
16/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.clases;

public class Usuario 
{
	//Atributos privados
	private int		iId;
	private String  sAlias;
	private String  sEmail;
	private String  sFoto;
	private String  sPassword;
	private String 	sUbicacion;
	private String 	sFechaNacimiento;
	private String 	sUbicacionLatitud;
	private String 	sUbicacionLongitud;
	private String 	sSexo;
	private String 	sTelefono;
	private float	fRadioBusqueda;

	//Métodos públicos
	
	//Constructor por defecto
	public Usuario()
	{
		this.iId 				= 0;
		this.sAlias 			= "";
		this.sEmail 			= "";
		this.sFoto 				= "";
		this.sPassword 			= "";
		this.sUbicacion 		= "";
		this.sFechaNacimiento 	= "";
		this.sUbicacionLatitud 	= "";
		this.sUbicacionLongitud = "";
		this.sSexo 				= "";
		this.sTelefono 			= "";
		this.fRadioBusqueda 	= 0;
	}
	//Constructor de copia
	public Usuario(Usuario cUsuarioOrigen)
    {
		this.iId 				= cUsuarioOrigen.getId();
		this.sAlias 			= cUsuarioOrigen.getAlias();
	    this.sEmail 			= cUsuarioOrigen.getEmail();
	    this.sFoto 				= cUsuarioOrigen.getFoto();
	    this.sPassword 			= cUsuarioOrigen.getPassword();
	    this.sUbicacion			= cUsuarioOrigen.getUbicacion();
	    this.sFechaNacimiento 	= cUsuarioOrigen.getFechaNacimiento();
		this.sUbicacionLatitud 	= cUsuarioOrigen.getUbicacionLatitud();
		this.sUbicacionLongitud = cUsuarioOrigen.getUbicacionLongitud();
		this.sSexo 				= cUsuarioOrigen.getSexo();
		this.sTelefono 			= cUsuarioOrigen.getTelefono();
		this.fRadioBusqueda 	= cUsuarioOrigen.getRadioBusqueda();
    }
    
    //Setters 
	public void setId(int iId) 									{this.iId = iId;}	
    public void setAlias(String sAlias) 						{this.sAlias = sAlias;}
    public void setEmail(String sEmail) 						{this.sEmail = sEmail;}    
    public void setFoto(String sFoto) 							{this.sFoto = sFoto;}    
    public void setPassword(String sPassword) 					{this.sPassword = sPassword;}    
    public void setUbicacion(String sUbicacion) 				{this.sUbicacion = sUbicacion;}        
    public void setUbicacionLatitud(String sUbicacionLatitud) 	{this.sUbicacionLatitud = sUbicacionLatitud;}
    public void setUbicacionLongitud(String sUbicacionLongitud) {this.sUbicacionLongitud = sUbicacionLongitud;}
    public void setFechaNacimiento(String sFechaNacimiento) 	{this.sFechaNacimiento = sFechaNacimiento;}
    public void setSexo(String sSexo) 							{this.sSexo = sSexo;}
    public void setTelefono(String sTelefono) 					{this.sTelefono = sTelefono;}
    public void setRadioBusqueda(float fRadioBusqueda) 			{this.fRadioBusqueda = fRadioBusqueda;}
    
    //Getters 
    public int getId() 											{return this.iId;}
    public String getAlias()									{return this.sAlias;}
    public String getEmail()									{return this.sEmail;}
    public String getFoto()										{return this.sFoto;}
    public String getPassword()									{return this.sPassword;}
    public String getUbicacion()								{return this.sUbicacion;}    
    public String getUbicacionLatitud()							{return this.sUbicacionLatitud;}
    public String getUbicacionLongitud()						{return this.sUbicacionLongitud;}
    public String getFechaNacimiento()							{return this.sFechaNacimiento;}
    public String getSexo()										{return this.sSexo;}
    public String getTelefono()									{return this.sTelefono;}	
    public float getRadioBusqueda()								{return this.fRadioBusqueda;}
    
    //Constructor de copia
  	public void vCopiar(Usuario cUsuarioOrigen)
  	{
		this.iId 				= cUsuarioOrigen.getId();
		this.sAlias 			= cUsuarioOrigen.getAlias();
	    this.sEmail 			= cUsuarioOrigen.getEmail();
	    this.sFoto 				= cUsuarioOrigen.getFoto();
	    this.sPassword 			= cUsuarioOrigen.getPassword();
	    this.sUbicacion 		= cUsuarioOrigen.getUbicacion();
	    this.sUbicacionLatitud 	= cUsuarioOrigen.getUbicacionLatitud();
	    this.sUbicacionLongitud = cUsuarioOrigen.getUbicacionLongitud();
	    this.sFechaNacimiento	= cUsuarioOrigen.getFechaNacimiento();
	    this.sSexo 				= cUsuarioOrigen.getSexo();
	    this.sTelefono 			= cUsuarioOrigen.getTelefono();
	    this.fRadioBusqueda 	= cUsuarioOrigen.getRadioBusqueda();
  	}
    
}//Fin Usuario
