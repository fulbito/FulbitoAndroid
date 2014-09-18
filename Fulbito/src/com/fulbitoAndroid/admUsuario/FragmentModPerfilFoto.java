package com.fulbitoAndroid.admUsuario;

import com.fulbitoAndroid.fulbito.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
//import android.app.Activity;

public class FragmentModPerfilFoto extends Fragment {
	// no se puede traer la constante desde import android.app.Activity;
	private static final int RESULT_OK = -1; 
	ImageView imProfilePic;
	
	@Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
			
		return inflater.inflate(R.layout.fragment_mod_perfil_foto, container, false);
        
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        
		Button btnBuscarFoto = (Button) getView().findViewById(R.id.btnBuscarFoto);
		btnBuscarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	        	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        	getActivity().startActivityForResult(pickPhoto , 1);	
	        }
	    }); 
		
		Button btnTomarFoto = (Button) getView().findViewById(R.id.btnTomarFoto);
		btnTomarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	
	        	Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	        	getActivity().startActivityForResult(takePicture, 0);
	        }
	    });
      
    }

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) 
		{
		case 0:
		    if(resultCode == RESULT_OK){  
		    	Bitmap photo = (Bitmap) data.getExtras().get("data");
		        ((ImageView) getView().findViewById(R.id.imageView1)).setImageBitmap(photo);
		        /*Uri selectedImage = data.getData();
		        imProfilePic = (ImageView) getView().findViewById(R.id.imageView1);;
		        imProfilePic.setImageURI(selectedImage);*/
		    }

		break; 
		case 1:
		    if(resultCode == RESULT_OK){  
		        Uri selectedImage = data.getData();
		        imProfilePic = (ImageView) getView().findViewById(R.id.imageView1);;
		        imProfilePic.setImageURI(selectedImage);
		    }
		break;
	   }
	}	
	
}
