package com.fulbitoAndroid.admUsuario;

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
import android.support.v4.app.FragmentActivity;

public class LoginUsuarioActivity extends FragmentActivity {

	//Metodos publicos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
    	
    	//Seteamos el tipo de fuente a los TextView txtVwLogin y txtVwRegistrar
    	Typeface typeFace = Typeface.createFromAsset(getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwRegistrar = (TextView) findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
        /////////////////////
        Button btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        
        btnRegistrar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
        		Intent intent = new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
        		startActivity(intent);
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
