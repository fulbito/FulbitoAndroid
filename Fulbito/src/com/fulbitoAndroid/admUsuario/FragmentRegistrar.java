/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentInicio
Descripción:	Clase que controla la interfaz de regitración de usuario 
				implementada mediante Fragment y que es invocada a través
				del FragmentInicio.java

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.R;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class FragmentRegistrar extends Fragment {
 
	//Atributos privados de la clase
	private static final String URL_REGISTRAR_USR = "http://www.fulbitoweb.com.ar/procesaRegistracion.php?alias=%1$s&email=%2$s&password=%3$s&registrar=true";
	private EditText 	edtTextAlias;
	private EditText 	edtTextCorreo;
	private EditText 	edtTextContrasena;
	private EditText 	edtTextConfirmarContrasena;
	private Button 		btnRegistrar;
	
	int iLargoAlias = 0;
	int iLargoCorreo = 0;
	int iLargoContrasena = 0;
	int iLargoContrasenaConfirmada = 0;
	
	private boolean bAliasCorrecto = false;
	private boolean bCorreoCorrecto = false;
	private boolean bContrasenaCorrecto = false;
	private boolean bContrasenaConfirmadaCorrecto = false;
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registrar, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
 
        //Obtenemos los controles EditText
        edtTextAlias = (EditText) getView().findViewById(R.id.edtTxtAlias);
    	edtTextCorreo = (EditText) getView().findViewById(R.id.edtTxtEmailRegistrar);
    	edtTextContrasena = (EditText) getView().findViewById(R.id.edtTxtContrasenaRegistrar);
    	edtTextConfirmarContrasena = (EditText) getView().findViewById(R.id.edtTxtConfirmarContrasena);
    	
    	//Obtenemos el control del botón Registrar
    	btnRegistrar = (Button) getView().findViewById(R.id.btnRegistrar);
    	
    	//Obtenemos el control del TextView Registrar 
    	TextView txtVwRegistrar = (TextView) getView().findViewById(R.id.txtVwRegistrar);
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");                       
        txtVwRegistrar.setTypeface(typeFace);
    	
    	//Agregamos las validaciones correspondientes a los campos texto
    	vAgregarValidacionAlias();    	
    	vAgregarValidacionCorreo();    	    	
    	vAgregarValidacionContrasena();    	    	
    	vAgregarValidacionContrasenaConfirmada(); 
    	
    	//Agregamos el evento OnClick al botón Registrar
    	vAgregarEventoBotónRegistrar();                        
    }
    
    //Métodos de validaciones de campos texto
    private void vAgregarValidacionAlias(){
    	edtTextAlias.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		//Se pierde foco sobre el campo Alias, se procede a realizar las validaciones
            		iLargoAlias = edtTextAlias.getText().length();
            		//Validamos que el campo Alias no este vacio
                	if(iLargoAlias == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtAliasVacio, Toast.LENGTH_LONG).show();
                		bAliasCorrecto = false;
                		edtTextAlias.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}
                	//Validamos que el campo Alias cumpla con el largo permitido
            		if(iLargoAlias < 6 || iLargoAlias > 20)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtLargoAlias, Toast.LENGTH_LONG).show();
                		bAliasCorrecto = false;
                		edtTextAlias.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}
            		//El campo Alias pasa todas las validaciones
            		bAliasCorrecto = true;
            		edtTextAlias.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            	else
            	{
            		//Se hace foco sobre el campo Alias
            		edtTextAlias.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            }
        });
    }
    private void vAgregarValidacionCorreo(){
    	edtTextCorreo.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		//Se pierde foco sobre el campo Correo, se procede a realizar las validaciones
            		iLargoCorreo = edtTextCorreo.getText().length();
            		//Validamos que el campo Correo no este vacio
                	if(iLargoCorreo == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtCorreoVacio, Toast.LENGTH_LONG).show();
                		bCorreoCorrecto = false;
                		edtTextCorreo.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}
                	//Validamos que el campo Correo sea válido
            		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtTextCorreo.getText()).matches())
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                				R.string.txtCorreoInvalido, Toast.LENGTH_LONG).show();
                		bCorreoCorrecto = false;
                		edtTextCorreo.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}          
            		//El campo Correo pasa las validaciones
            		bCorreoCorrecto = true;
            		edtTextCorreo.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            	else
            	{
            		//Se hace foco sobre el campo Correo
            		edtTextCorreo.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            }
        });
    }
    private void vAgregarValidacionContrasena(){
    	edtTextContrasena.setOnFocusChangeListener(new OnFocusChangeListener() {    		    		
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		//Se pierde foco sobre el campo Correo, se procede a realizar las validaciones
            		iLargoContrasena = edtTextContrasena.getText().length();
                	//Validamos que el campo Contraseña no este vacio
            		if(iLargoContrasena == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtContrasenaVacio, Toast.LENGTH_LONG).show();
                		bContrasenaCorrecto = false;
                		edtTextContrasena.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}
                	//Validamos que el campo Contraseña respete los largos permitidos					
            		if(iLargoContrasena < 6 || iLargoContrasena > 15)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                				R.string.txtLargoContrasena, Toast.LENGTH_LONG).show();
                		bContrasenaCorrecto = false;
                		edtTextContrasena.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}
                	//El campo Contraseña pasa todas las validaciones
            		bContrasenaCorrecto = true;
            		edtTextContrasena.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            	else
            	{
            		//Se hace foco sobre el campo Contraseña
            		edtTextContrasena.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            }
        });
    }
    private void vAgregarValidacionContrasenaConfirmada(){
    	edtTextConfirmarContrasena.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		//Se pierde foco sobre el campo Correo, se procede a realizar las validaciones
            		/*
            		iLargoContrasenaConfirmada = edtTextConfirmarContrasena.getText().length();
                	
            		if(iLargoContrasenaConfirmada == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtContrasenaConfirmadaVacia, Toast.LENGTH_LONG).show();
                		bContrasenaConfirmadaCorrecto = false;
                		return;
                	}

                	if(iLargoContrasenaConfirmada < 6 || iLargoContrasenaConfirmada > 15)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtLargoContrasenaConfirmada, Toast.LENGTH_LONG).show();
                		bContrasenaConfirmadaCorrecto = false;
                		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.campo_editable_error);
                		return;
                	}*/

            		//Validamos que el campo ConfirmarContrasena sea igual al campo Contrasena
            		if(edtTextConfirmarContrasena.getText().toString() != edtTextContrasena.getText().toString())
        			{
            			Toast.makeText(getActivity().getApplicationContext(), 
             	               R.string.txtContrasenasDistintas, Toast.LENGTH_LONG).show();
		         		bContrasenaConfirmadaCorrecto = false;
		         		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.campo_editable_error);
		         		return;
        			}
            		//El campo ConfirmarContrasena paso las validaciones
            		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            		bContrasenaConfirmadaCorrecto = true;
            	}
            	else
            	{
            		//Se hace foco sobre el campo ConfirmarContrasena
            		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.resaltar_campo_on_focus);
            	}
            }
        });
    }

    //Metodo para agregar evento OnClick al botón Registrar
    private void vAgregarEventoBotónRegistrar(){
        btnRegistrar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
	        	if(bAliasCorrecto == true && bCorreoCorrecto == true && bContrasenaCorrecto == true && bContrasenaConfirmadaCorrecto == true)
	        	{
	        		//Todos los campos son correctos, entonces podemos invocar al WebService para registrar el Usuario
	        		/*HttpPost httpPost = new HttpPost(String.format(URL_REGISTRAR_USR,
        			edtTextAlias.getText().toString(),
        			edtTextCorreo.getText().toString(),
        			edtTextContrasena.getText().toString()));*/
        	
		        	HttpGet httpget = new HttpGet(String.format(URL_REGISTRAR_USR,
		        			edtTextAlias.getText().toString(),
		        			edtTextCorreo.getText().toString(),
		        			edtTextContrasena.getText().toString()));
		        	
		        	HttpResponse response;
		        	HttpClient httpClient = new DefaultHttpClient();
		        	
		    		try {
						response = httpClient.execute(httpget);
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}   
	        	else
	        	{
	        		//Hay campos incorrectos
	        		if(iLargoAlias == 0 || iLargoCorreo == 0 || iLargoContrasena == 0 || iLargoContrasenaConfirmada == 0)
	        		{
	        			if(iLargoAlias == 0)
	        				edtTextAlias.setBackgroundResource(R.drawable.campo_editable_error);
	        			if(iLargoCorreo == 0)
	        				edtTextCorreo.setBackgroundResource(R.drawable.campo_editable_error);
	        			if(iLargoContrasena == 0)
	        				edtTextContrasena.setBackgroundResource(R.drawable.campo_editable_error);
	        			if(iLargoContrasenaConfirmada == 0)
	        				edtTextConfirmarContrasena.setBackgroundResource(R.drawable.campo_editable_error);
	        			Toast.makeText(getActivity().getApplicationContext(), 
	        					R.string.txtCamposVacios, Toast.LENGTH_LONG).show();
	        		}
	        		else
	        		{
	        			Toast.makeText(getActivity().getApplicationContext(), 
	        					R.string.txtRevisarCampos, Toast.LENGTH_LONG).show();
	        		}	        			
	        	}
            }
        });
    }
}
