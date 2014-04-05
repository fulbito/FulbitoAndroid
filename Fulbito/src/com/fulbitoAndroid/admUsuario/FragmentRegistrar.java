package com.fulbitoAndroid.admUsuario;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fulbitoAndroid.fulbito.R;

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
	private EditText edtTextAlias;
	private EditText edtTextCorreo;
	private EditText edtTextContrasena;
	private EditText edtTextConfirmarContrasena;
	
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
    	
    	//Validación del campo Alias
    	edtTextAlias.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		iLargoAlias = edtTextAlias.getText().length();
                	if(iLargoAlias == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtAliasVacio, Toast.LENGTH_LONG).show();
                		bAliasCorrecto = false;
                		return;
                	}

            		if(iLargoAlias < 6 || iLargoAlias > 20)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtLargoAlias, Toast.LENGTH_LONG).show();
                		bAliasCorrecto = false;
                		return;
                	}
            		
            		bAliasCorrecto = true;
            	}
            }
        });
    	
    	//Validación del campo Correo
    	edtTextCorreo.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		iLargoCorreo = edtTextCorreo.getText().length();
                	if(iLargoCorreo == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtCorreoVacio, Toast.LENGTH_LONG).show();
                		bCorreoCorrecto = false;
                		return;
                	}

            		if(!android.util.Patterns.EMAIL_ADDRESS.matcher(edtTextCorreo.getText()).matches())
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                				R.string.txtCorreoInvalido, Toast.LENGTH_LONG).show();
                		bCorreoCorrecto = false;
                		return;
                	}          
            		bCorreoCorrecto = true;
            	}
            }
        });
    	
    	//Validación del campo Contraseña
    	edtTextContrasena.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
            		iLargoContrasena = edtTextContrasena.getText().length();
                	if(iLargoContrasena == 0)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                	               R.string.txtContrasenaVacio, Toast.LENGTH_LONG).show();
                		bContrasenaCorrecto = false;
                		return;
                	}

            		if(iLargoContrasena < 6 || iLargoContrasena > 15)
                	{
                		Toast.makeText(getActivity().getApplicationContext(), 
                				R.string.txtLargoContrasena, Toast.LENGTH_LONG).show();
                		bContrasenaCorrecto = false;
                		return;
                	}

            		bContrasenaCorrecto = true;
            	}
            }
        });    	
    	
    	//Validación del campo Contraseña Confirmada
    	edtTextConfirmarContrasena.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {                
            	if (!hasFocus)
            	{
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
                		return;
                	}

            		if(edtTextConfirmarContrasena.getText().toString() != edtTextContrasena.getText().toString())
        			{
            			Toast.makeText(getActivity().getApplicationContext(), 
             	               R.string.txtContrasenasDistintas, Toast.LENGTH_LONG).show();
		         		bContrasenaConfirmadaCorrecto = false;
		         		return;
        			}
            		
            		bContrasenaConfirmadaCorrecto = true;
            	}
            }
        });  
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwRegistrar = (TextView) getView().findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
        Button btnRegistrar = (Button) getView().findViewById(R.id.btnRegistrar);
        
        btnRegistrar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
	        	if(bAliasCorrecto == true && bCorreoCorrecto == true && bContrasenaCorrecto == true && bContrasenaConfirmadaCorrecto == true)
	        	{
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
	        		if(iLargoAlias == 0 || iLargoCorreo == 0 && iLargoContrasena == 0 || iLargoContrasenaConfirmada == 0)
	        		{
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
