package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.fulbito.R.layout;
import com.fulbitoAndroid.fulbito.R.menu;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ModUsuarioActivity extends Activity {

	ImageView imgFoto;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mod_usuario);
		
		Button btnBuscarFoto = (Button) findViewById(R.id.btnBuscarFoto);
		btnBuscarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	        	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        	startActivityForResult(pickPhoto , 1);	        	
	        }
	    });
		
		Button btnTomarFoto = (Button) findViewById(R.id.btnTomarFoto);
		btnTomarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	startActivityForResult(takePicture, 0);
	        }
	    });
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
		case 0:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = imageReturnedIntent.getData();
		        imgFoto = (ImageView) findViewById(R.id.imageView1);;
		        imgFoto.setImageURI(selectedImage);
		    }

		break; 
		case 1:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = imageReturnedIntent.getData();
		        imgFoto = (ImageView) findViewById(R.id.imageView1);;
		        imgFoto.setImageURI(selectedImage);
		    }
		break;
		}
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mod_usuario, menu);
		return true;
	}

}
