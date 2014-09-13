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
	private int     iEstado;
	private String sUbicacion;

	//Métodos públicos
	
	//Constructor por defecto
	public Usuario()
	{
		this.iId 		= 0;
		this.sAlias 	= "";
		this.sEmail 	= "";
		this.sFoto 		= "";
		this.sPassword 	= "";
		this.iEstado 	= 0;
		this.sUbicacion = "";
	}
	//Constructor con datos de usuario
	public Usuario(int iId, String sAlias, String sEmail, String sFoto, String sPassword, int iEstado)
    {
		this.iId 		= iId;
		this.sAlias 	= sAlias;
	    this.sEmail 	= sEmail;
	    this.sFoto 		= sFoto;
	    this.sPassword	= sPassword;
	    this.iEstado	= iEstado;		
    }
	//Constructor de copia
	public Usuario(Usuario cUsuarioOrigen)
    {
		this.iId 		= cUsuarioOrigen.getId();
		this.sAlias 	= cUsuarioOrigen.getAlias();
	    this.sEmail 	= cUsuarioOrigen.getEmail();
	    this.sFoto 		= cUsuarioOrigen.getFoto();
	    this.sPassword 	= cUsuarioOrigen.getPassword();
	    this.iEstado 	= cUsuarioOrigen.getEstado();		
    }
    
    //Setters 
	public void setId(int iId)
	{
		this.iId = iId;
	}	
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
    public void setUbicacion(String sUbicacion)
    {
      this.sUbicacion = sUbicacion;
    }    
    
    //Getters 
    public int getId()
    {
    	return iId;
    }
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
    public String getUbicacion()
    {
    	return this.sUbicacion;
    }
    
    //Constructor de copia
  	public void vCopiar(Usuario cUsuarioOrigen)
  	{
		this.iId 		= cUsuarioOrigen.getId();
		this.sAlias 	= cUsuarioOrigen.getAlias();
	    this.sEmail 	= cUsuarioOrigen.getEmail();
	    this.sFoto 		= cUsuarioOrigen.getFoto();
	    this.sPassword 	= cUsuarioOrigen.getPassword();
	    this.iEstado 	= cUsuarioOrigen.getEstado();	
  	}
    
}//Fin Usuario
