/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentRegistrar
Descripción:	Clase que controla la interfaz de registración de usuario 
				implementada mediante Fragment y que es invocada a través
				del FragmentInicio.java

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.FulbitoException;
import com.fulbitoAndroid.fulbito.HomeActivity;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
	private EditText 	edtTextAlias;
	private EditText 	edtTextCorreo;
	private EditText 	edtTextContrasena;
	private EditText 	edtTextConfirmarContrasena;
	private Button 		btnRegistrar;
	private ProgressDialog pDialog;
	
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
            		vValidarCampoAlias();
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
            		vValidarCampoCorreo();
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
            		vValidarCampoContrasena();
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
            		vValidarCampoContrasenaConfirmada();
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
        		//Validamos los campos que ingreso el usuario
        		vValidarCampoAlias();
        		vValidarCampoCorreo();
        		vValidarCampoContrasena();
        		vValidarCampoContrasenaConfirmada();        		
        	
        		if(bAliasCorrecto == true && bCorreoCorrecto == true && bContrasenaCorrecto == true && bContrasenaConfirmadaCorrecto == true)
	        	{
        			//La validación fue correcta        			      	             	        
        			//Se llama al WebService de Login
        			RegistrarAsyncTask registrarTask = new RegistrarAsyncTask();
        			registrarTask.execute();
	            }
	            else
	        	{	    
	        		Toast.makeText(getActivity().getApplicationContext(), 
	        			R.string.txtRevisarCampos, Toast.LENGTH_LONG).show();
	        	}	        	
            }
        });
    }

    private void vValidarCampoAlias(){
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
    private void vValidarCampoCorreo(){
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
    private void vValidarCampoContrasena(){   	
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
		if(
			iLargoContrasena < getResources().getInteger(R.integer.min_largo_contrasena) 
			|| iLargoContrasena > getResources().getInteger(R.integer.max_largo_contrasena)
		)
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
    private void vValidarCampoContrasenaConfirmada(){    	
		iLargoContrasenaConfirmada = edtTextConfirmarContrasena.getText().length();
    	
		if(iLargoContrasenaConfirmada == 0)
    	{
    		Toast.makeText(getActivity().getApplicationContext(), 
    	               R.string.txtContrasenaConfirmadaVacia, Toast.LENGTH_LONG).show();
    		bContrasenaConfirmadaCorrecto = false;
    		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.campo_editable_error);
    		return;
    	}

    	if(iLargoContrasenaConfirmada < 6 || iLargoContrasenaConfirmada > 15)
    	{
    		Toast.makeText(getActivity().getApplicationContext(), 
    	               R.string.txtLargoContrasenaConfirmada, Toast.LENGTH_LONG).show();
    		bContrasenaConfirmadaCorrecto = false;
    		edtTextConfirmarContrasena.setBackgroundResource(R.drawable.campo_editable_error);
    		return;
    	}

		//Validamos que el campo ConfirmarContrasena sea igual al campo Contrasena
    	String sContrasena = edtTextContrasena.getText().toString();
    	String sContrasenaConfirmada = edtTextConfirmarContrasena.getText().toString();
		if(!sContrasena.equals(sContrasenaConfirmada))
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
    
    class RegistrarAsyncTask extends AsyncTask<Void, Void, Boolean> {
        
    	private Usuario cUsrRegistrar 	= null;
		private String sError 		= "";
		
        @Override 
        protected void onPreExecute() {
        	cUsrRegistrar = new Usuario();
    		cUsrRegistrar.setAlias(edtTextAlias.getText().toString());
    		cUsrRegistrar.setEmail(edtTextCorreo.getText().toString());
    		cUsrRegistrar.setPassword(edtTextContrasena.getText().toString());
	    	
	    	pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setTitle("Procesando...");
	        pDialog.setMessage("Espere por favor...");
	        pDialog.setCancelable(false);
	        pDialog.setIndeterminate(true);  
	    	pDialog.show();
        }

        @Override 
        protected Boolean doInBackground(Void... par) {
        	Boolean bResult = false;
	    	
        	//Invocamos el Web Service de Registrar
        	WebServiceUsuario wsRegistrarUsuario = new WebServiceUsuario(getActivity().getApplicationContext());
        	RespuestaWebService cRespWS = new RespuestaWebService();
        	
        	try
        	{
    	    	switch(wsRegistrarUsuario.bRegistrarUsuario(cUsrRegistrar, cRespWS))
    	    	{
    	    		case OK:
    	    			//Seteamos los datos del usuario logueado
    	    			SingletonUsuarioLogueado.registrarUsuarioLogueado(cUsrRegistrar);
    	    			bResult = true;
    	    			break;
    	    		case NO_CONNECTION:
    	    			//no hay conexión a internet
    	    			sError = getString(R.string.errMsjSinConexion);
    	    			bResult = false;
    	    			break;
    	    		case ERROR:
    	    			//el logueo automatico no fue exitoso
    	    			//El webservice envio una respuesta con error
    	    			sError = cRespWS.sGetData();
    	    			bResult = false;
    	    			break;		    		
    	    	}
    		}
        	catch(FulbitoException feException)
        	{
        		sError = getString(R.string.errMsjRegistrarUsuario);
        		bResult = false;
        	}

	        return bResult;
        }

        @Override 
        protected void onProgressUpdate(Void... prog) {
        }

        @Override 
        protected void onPostExecute(Boolean bResult) {
        	
        	if(pDialog!=null) 
        	{
        		pDialog.dismiss();
        		//pDialog.setEnabled(true);
			}
        	
        	if(bResult == true)
        	{
				//El login fue correcto, se ingresa al home
        		Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
				startActivity(intent);
				getActivity().finish();
        	}
	        else
	        {
	        	Toast.makeText(getActivity().getApplicationContext(), 
	        		sError, Toast.LENGTH_LONG).show();
	        }
        }
	}
}
