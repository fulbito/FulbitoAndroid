package com.fulbitoAndroid.clases;

/* ----------------------------------------------------------------------------- 
Nombre: 		Usuario
Descripción:	Clase que contiene los datos de un usuario de la aplicación
				Fulbito

Log de modificaciones:

Fecha		Autor		Descripción
16/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

public class Usuario 
{
	//Atributos privados
	private String  sAlias;
	private String  sEmail;
	private String  sFoto;
	private String  sPassword;
	private int     iEstado;

	//Métodos públicos
	
	//Constructor por defecto
	public Usuario()
	{
		this.sAlias    = "";
		this.sEmail    = "";
		this.sFoto     = "";
		this.sPassword = "";
		this.iEstado   = 0;
	}
	//Constructor con datos de usuario
	public Usuario(String sAlias, String sEmail, String sFoto, String sPassword, int iEstado)
    {
		this.sAlias    = sAlias;
	    this.sEmail    = sEmail;
	    this.sFoto     = sFoto;
	    this.sPassword = sPassword;
	    this.iEstado   = iEstado;		
    }
	//Constructor de copia
	public Usuario(Usuario cUsuarioOrigen)
    {
		this.sAlias    = cUsuarioOrigen.sAlias;
	    this.sEmail    = cUsuarioOrigen.sEmail;
	    this.sFoto     = cUsuarioOrigen.sFoto;
	    this.sPassword = cUsuarioOrigen.sPassword;
	    this.iEstado   = cUsuarioOrigen.iEstado;		
    }
    
    //Setters 
    public void setAlias(String sAlias)
    {
    	this.sAlias = sAlias;
    }
    public void setEmail(String sEmail)
    {
    	this.sEmail = sEmail;
    }    
    public void setFoto(String sFoto)
    {
      this.sFoto = sFoto;
    }    
    public void setPassword(String sPassword)
    {
      this.sPassword = sPassword;
    }    
    public void setEstado(int iEstado)
    {
      this.iEstado = iEstado;
    }
    
    //Getters 
    public String getAlias()
    {
      return this.sAlias;
    }
    public String getEmail()
    {
      return this.sEmail;
    }
    public String getFoto()
    {
      return this.sFoto;
    }
    public String getPassword()
    {
      return this.sPassword;
    }
    public int getEstado()
    {
      return this.iEstado;
    }	
}//Fin Usuario
