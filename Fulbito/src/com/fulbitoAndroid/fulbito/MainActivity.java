package com.fulbitoAndroid.fulbito;
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
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //seteamos el evento OnClick del botón Modificar Usuario
        Button btnIngresar = (Button) findViewById(R.id.btnIngresar);
        
        btnIngresar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
            {   
                Intent intent = new Intent(getApplicationContext(), ModUsuarioActivity.class);
                    startActivity(intent);      
            }
        });        
        
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "Helvetica-Black-SemiBold.ttf");
        
        TextView txtVwLogin = (TextView) findViewById(R.id.txtVwLogin);        
        txtVwLogin.setTypeface(typeFace);
        
        TextView txtVwRegistrar = (TextView) findViewById(R.id.txtVwRegistrar);       
        txtVwRegistrar.setTypeface(typeFace);
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
