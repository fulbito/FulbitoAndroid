package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.HomeActivity;
import com.fulbitoAndroid.fulbito.R;
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
        vAgregarValidacionCorreo();    	
        vAgregarValidacionContrasena();    	
    	
        //Agregamos el evento OnClick al botón Registrar
    	vAgregarEventoBotónIngresar();
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
    private void vAgregarEventoBotónIngresar(){
    	//Falta agregar el llamado al WebService para loguearse
    	//Si el logueo es correcto se debe llamar al HomeActivity
    	btnIngresar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
                Intent intent = new Intent(getActivity().getApplicationContext(), HomeActivity.class);
                    startActivity(intent);      
            }
        }); 
    }
}

