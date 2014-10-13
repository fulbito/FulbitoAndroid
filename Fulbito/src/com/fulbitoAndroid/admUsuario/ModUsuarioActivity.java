package com.fulbitoAndroid.admUsuario;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.fulbito.R.layout;
import com.fulbitoAndroid.fulbito.R.menu;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ModUsuarioActivity extends Activity {

	ImageView imgFoto;
	private ProgressDialog pDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mod_usuario);
		imgFoto = (ImageView) findViewById(R.id.imageView1);
		
		Button btnBuscarFoto = (Button) findViewById(R.id.btnBuscarFoto);
		btnBuscarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	/*Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	        	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        	startActivityForResult(pickPhoto , 1);*/
	        	new LoadImage().execute("http://maquinadelaire.com/web/wp-content/uploads/2014/07/lionel-messi_416x416.jpg");
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
	
	public Uri getImageUri(Context inContext, Bitmap inImage){
    	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    	inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);    	
    	String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, null, null);    	
    	return Uri.parse(path);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) { 
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent); 
		switch(requestCode) {
		case 0:
		    if(resultCode == RESULT_OK){  
		    	Bitmap bm = imageReturnedIntent.getExtras().getParcelable("data");
		        //Uri selectedImage = getImageUri(getApplicationContext(), bm);
		        
		        imgFoto.setImageBitmap(bm);		        
		    }

		break; 
		case 1:
		    if(resultCode == RESULT_OK){  
		    	Uri selectedImage = imageReturnedIntent.getData();
		        
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
	
	private class LoadImage extends AsyncTask<String, String, Bitmap> {
		Bitmap bitmapURL;
	    @Override
	    protected void onPreExecute() 
	    {
			//super.onPreExecute();
			/*pDialog = new ProgressDialog(getApplicationContext());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setTitle("Procesando...");
	        pDialog.setMessage("Espere por favor...");
	        pDialog.setCancelable(false);
	        pDialog.setIndeterminate(true);  
	    	pDialog.show();*/
	    }
	       
	    protected Bitmap doInBackground(String... args) 
	    {
	    	try 
	    	{
	    		bitmapURL = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());
	    	} 
	    	catch (Exception e) 
	    	{
	    		e.printStackTrace();
	    	}
	    	return bitmapURL;
	    }
	       
	    protected void onPostExecute(Bitmap image) 
	    {
	    	if(image != null)
	    	{
	    		imgFoto.setImageBitmap(image);
	    		//pDialog.dismiss();
	    	}
	    	else
	    	{
	    		//pDialog.dismiss();
	    		Toast.makeText(getApplicationContext(), "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
	    	}
	    }
	}

}
