/* ----------------------------------------------------------------------------- 
Nombre: 		MainActivity
Descripción:	Clase que contiene la lógica de login y registración de la aplicación

Log de modificaciones:

Fecha		Autor		Descripción
22/03/2014	MAC			Creación
----------------------------------------------------------------------------- */

package com.fulbitoAndroid.fulbito;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import com.fulbitoAndroid.admUsuario.ModUsuarioActivity;
import com.fulbitoAndroid.fulbito.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	//Atributos privados de la clase
	private static final String URL_REGISTRAR_USR = "http://www.fulbitoweb.com.ar/procesaRegistracion.php?alias=%1$s&email=%2$s&password=%3$s&registrar=true";
	private EditText edtTextAlias;
	private EditText edtTextCorreo;
	private EditText edtTextContrasena;
	private EditText edtTextConfirmarContrasena;

	//Metodos publicos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Obtenemos los controles EditText
        edtTextAlias = (EditText) findViewById(R.id.edtTxtAlias);
    	edtTextCorreo = (EditText) findViewById(R.id.edtTxtEmailRegistrar);
    	edtTextContrasena = (EditText) findViewById(R.id.edtTxtContrasenaRegistrar);
    	edtTextConfirmarContrasena = (EditText) findViewById(R.id.edtTxtConfirmarContrasena);
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwLogin = (TextView) findViewById(R.id.txtVwLogin);        
        txtVwLogin.setTypeface(typeFace);
        
        TextView txtVwRegistrar = (TextView) findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
        //seteamos el evento OnClick del botón btnIngresar
        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        
        btnIngresar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
                Intent intent = new Intent(getApplicationContext(), ModUsuarioActivity.class);
                    startActivity(intent);      
            }
        });  
        
        
        /////////////////////
        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        
        btnRegistrar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
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
        });      
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
