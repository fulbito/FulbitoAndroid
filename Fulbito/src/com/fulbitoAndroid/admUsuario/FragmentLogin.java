/* ----------------------------------------------------------------------------- 
Nombre: 		FragmentLogin
Descripción:	Clase que controla la interfaz de logueo de usuario 
				implementada mediante Fragment y que es invocada a través
				del FragmentInicio.java

Log de modificaciones:

Fecha		Autor		Descripción
17/04/2014	MAC			Creación
----------------------------------------------------------------------------- */
package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.HomeActivity;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.RespuestaWebService;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class FragmentLogin extends Fragment {
 
	//Atributos privados de la clase
	private EditText edtTextCorreo;
	private EditText edtTextContrasena;
	private Button btnIngresar;
	private Button btnRecuperarContrasena;
	
	int iLargoCorreo = 0;
	int iLargoContrasena = 0;
	
	private boolean bCorreoCorrecto = false;
	private boolean bContrasenaCorrecto = false;
	
	static final String TAG="FragmentLogin";
	
	@Override
	public void onCreate( Bundle savedInstanceState ) {
	    super.onCreate( savedInstanceState );
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);               
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
        //Obtenemos los controles EditText
    	edtTextCorreo = (EditText) getView().findViewById(R.id.edtTxtEmailLogin);		
    	edtTextContrasena = (EditText) getView().findViewById(R.id.edtTxtContrasenaLogin);   
    	
    	//Obtenemos el control del botón Registrar y RecuperarContrasena
    	btnIngresar = (Button) getView().findViewById(R.id.btnIngresar);
    	btnRecuperarContrasena = (Button) getView().findViewById(R.id.btnRecuperarContrasena);
    	
    	//Obtenemos el control del TextView Login
    	TextView txtVwLogin = (TextView) getView().findViewById(R.id.txtVwLogin);
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");                        
        txtVwLogin.setTypeface(typeFace);        
        
        TextView txtTextOlvidoContrasena = (TextView) getView().findViewById(R.id.txtVwOlvidoContrasena);
        txtTextOlvidoContrasena.setTypeface(typeFace);        
        
        //Agregamos las validaciones correspondientes a los campos texto
        vAgregarValidacionCorreoEnCambioDeFoco();    	
        vAgregarValidacionContrasenaEnCambioDeFoco();    	
    	
        //Agregamos el evento OnClick al botón Registrar
    	vAgregarEventoBotónIngresar();
    	//Agregamos el evento OnClick al botón Recuperar Contraseña
    	vAgregarEventoBotónRecuperarContrasena();
    }
    
    private void vAgregarValidacionCorreoEnCambioDeFoco(){
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
    private void vAgregarValidacionContrasenaEnCambioDeFoco(){
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
    
    private void vAgregarEventoBotónIngresar(){
    	btnIngresar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
        		//Validamos los campos que ingreso el usuario
        		vValidarCampoCorreo();
        		vValidarCampoContrasena();
        		
        		if(bCorreoCorrecto == true && bContrasenaCorrecto == true)
	        	{
	        		//La validación fue correcta
        			boolean bLoginCorrecto = false;
        			//Se llama al WebService de Login
	            	bLoginCorrecto =  bInvocaWebServiceLogin();
	            	
	            	if(bLoginCorrecto == true)
	            	{
						//El login fue correcto, se ingresa al home
	            		Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
						startActivity(intent);
						getActivity().finish();
	            	}	
	        	}
	        	else
	        	{
	        		//Hay campos incorrectos
	        		/*if(iLargoCorreo == 0 || iLargoContrasena == 0)
	        		{
	        			if(iLargoCorreo == 0)
	        				edtTextCorreo.setBackgroundResource(R.drawable.campo_editable_error);
	        			if(iLargoContrasena == 0)
	        				edtTextContrasena.setBackgroundResource(R.drawable.campo_editable_error);
	
	        			Toast.makeText(getActivity().getApplicationContext(), 
	        					R.string.txtCamposVacios, Toast.LENGTH_LONG).show();
	        		}
	        		else
	        		{*/
	        			Toast.makeText(getActivity().getApplicationContext(), 
	        					R.string.txtRevisarCampos, Toast.LENGTH_LONG).show();
	        		//}
	        	}  
            }
        }); 
    }
    private void vAgregarEventoBotónRecuperarContrasena(){
    	//Falta agregar el llamado al WebService para recuperar contraseña
    	btnRecuperarContrasena.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
				
            }
        }); 
    }
    //Invoca el WebService para ingresar un usuario al sistema
    private boolean bInvocaWebServiceLogin(){    	    	
    	/*
    	 * Ver la posibilidad de invocar al WebService en segundo plano con LoginAsyncTask
    	LoginAsyncTask loginTask = new LoginAsyncTask(getActivity());
    	loginTask.execute();
    	*/
    	//Instanciamos un objeto Usuario
    	Usuario cUsrLogin = new Usuario();
    	cUsrLogin.setEmail(edtTextCorreo.getText().toString());
    	cUsrLogin.setPassword(edtTextContrasena.getText().toString());
    	
    	//Invocamos el Web Service de Login
    	WebServiceLogin wsLogin = new WebServiceLogin();
    	RespuestaWebService cRespWS = new RespuestaWebService();
    	if(wsLogin.bLoguearUsuario(cUsrLogin, cRespWS) == true)
    	{
    		//Seteamos los datos del usuario logueado
			SingletonUsuarioLogueado.registrarUsuarioLogueado(cUsrLogin, getActivity().getApplicationContext());
    	}
    	else
    	{
    		//El webservice envio una respuesta con error
			Toast.makeText(getActivity().getApplicationContext(), 
					cRespWS.sGetData(), Toast.LENGTH_LONG).show();
			
			return false;
    	}
		
    	return true;    	
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
}