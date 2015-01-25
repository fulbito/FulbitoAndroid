package com.fulbitoAndroid.clases;

public class PartidoAmistoso extends Partido {

	private int iTipoSeleccion;
	
	//Constructor por defecto
	public PartidoAmistoso()
	{
		super();
		this.iTipoSeleccion = 0;
	}
	
	//Constructor de copia	
	public PartidoAmistoso(PartidoAmistoso cPartidoOrigen)
    {
		super(cPartidoOrigen);
	    this.iTipoSeleccion	= cPartidoOrigen.getTipoSeleccion();
    }
	
	//Setters y Getters
	public int getTipoSeleccion() {
		return iTipoSeleccion;
	}
	public void setTipoSeleccion(int iTipoSeleccion) {
		this.iTipoSeleccion = iTipoSeleccion;
	}
	
	//metodo de copia
  	public void vCopiar(PartidoAmistoso cPartidoOrigen)
  	{
  		this.vCopiar(cPartidoOrigen);
	    this.iTipoSeleccion	= cPartidoOrigen.getTipoSeleccion();
	}
}
