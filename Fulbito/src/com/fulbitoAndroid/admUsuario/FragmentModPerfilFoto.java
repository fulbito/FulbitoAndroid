package com.fulbitoAndroid.admUsuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.fulbitoAndroid.clases.SingletonAppSettings;
import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.R;

public class FragmentModPerfilFoto extends Fragment {
	
	private static final String TAG="FragmentModPerfilFoto"; 

	private static final int RESULT_OK = -1;
	
	//Request codes
	private static final int CAMERA_INTENT_REQUEST 	= 2000;
	private static final int GALLERY_INTENT_REQUEST = 2001;
	private static final int CROP_FROM_CAMERA 		= 2002;
	
	ImageView imgAvatar;
	ImageButton btnBuscarFoto;
	ImageButton btnTomarFoto;
	
	Uri uriImageSelected;
	
	Usuario usrLogueado = null;	
	
	//Solo para cuando se saca una foto
	

	public static final String TEMP_PHOTO_FILE_NAME = "IMG_CAMARA.jpg";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {			
		return inflater.inflate(R.layout.fragment_mod_perfil_foto, container, false);        
    }
 
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        //Obtenemos el usuario logueado
        usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();
        String sPathFotoAvatar = usrLogueado.getFoto();       
        
        //Habria que validar que no se cambio la foto de perfil desde la web, si es asi hay que descargarla
        
        //Obtenemos los elementos de la interfaz grafica
        imgAvatar = (ImageView)getView().findViewById(R.id.imageView1);         
        
        //Cargamos la imagen de perfil del usuario logueado
        if(sPathFotoAvatar.length() == 0)
        {
        	//Imagen por default
        	imgAvatar.setImageResource(R.drawable.no_avatar);
        }
        else
		{
        	String sPath = SingletonAppSettings.getAppSettings().getsBasePath() + SingletonAppSettings.getAppSettings().getsMediaPerfilPath();        	
        	Bitmap photo = BitmapFactory.decodeFile(sPath + "/" + sPathFotoAvatar);			
			imgAvatar.setImageBitmap(photo);
		}

        //Seteamos el botón para buscar una imagen de la galeria
		btnBuscarFoto = (ImageButton) getView().findViewById(R.id.btnBuscarFoto);
		btnBuscarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent pickPhoto = new Intent(Intent.ACTION_PICK,
	        	           android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	        	getActivity().startActivityForResult(pickPhoto , GALLERY_INTENT_REQUEST);	
	        }
	    }); 
		
        //Seteamos el botón para tomar una foto		
		btnTomarFoto = (ImageButton) getView().findViewById(R.id.btnTomarFoto);
		btnTomarFoto.setOnClickListener(new OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
	        	
	        	uriImageSelected = getTempFile(); // create a file to save the image
	        	cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImageSelected); // set the image file name
	        	
	        	//cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, getTempFile());
	        	getActivity().startActivityForResult(cameraIntent, CAMERA_INTENT_REQUEST); 
	        }
	    });      
    }    

    //Metodo para crear un archivo temporal de la imagen capturada por la camara
    private Uri getTempFile()
    {
    	File mFileTemporalBitmap;    	
    	String sPath = SingletonAppSettings.getAppSettings().getsBasePath() + SingletonAppSettings.getAppSettings().getsMediaPerfilPath();     	
    	mFileTemporalBitmap = new File(sPath + File.separator + TEMP_PHOTO_FILE_NAME);    	
      	return Uri.fromFile(mFileTemporalBitmap);
    }
    
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) 
		{
			//Se captura el resultado de la camara
			case CAMERA_INTENT_REQUEST:
			    if(resultCode == RESULT_OK)
			    {  
			    	//Para ver los extras de un intent
			    	/*
			    	Bundle extras = data.getExtras();
			    	String result="";
			        if (extras != null)
			        {
			        	result += "key count: " + extras.keySet().size();
			            for (String key : extras.keySet())
			            {
			            	result += "\n" + key + extras.get(key);
			            }
			        }
			        */
			    	doCrop(uriImageSelected);
			    }
	
			    break; 
			//Se captura el resultado del CROP
			case CROP_FROM_CAMERA:
				Bitmap photo = null;
				Bundle extras = data.getExtras();
		          
				if (extras != null)
				{
					//Mostramos la foto de perfil en la interfaz grafica
					photo = extras.getParcelable("data");
					((ImageView) getView().findViewById(R.id.imageView1)).setImageBitmap(photo);
				}
		          			
				//En el caso que la foto de perfil se haya capturado desde la camara se debe eliminar el archivo temporal creado
				File f = new File(uriImageSelected.getPath());
				if (f.exists()) 
					f.delete();
		        				
				String sPath = SingletonAppSettings.getAppSettings().getsBasePath() + SingletonAppSettings.getAppSettings().getsMediaPerfilPath(); 		          				
	
				if(usrLogueado.getFoto().length() != 0)
				{
					//Eliminamos la foto de perfil anterior
					File fFotoPerfilAnterior = new File(sPath + "/" + usrLogueado.getFoto());
					if (fFotoPerfilAnterior.exists()) 
						fFotoPerfilAnterior.delete();
				}			
				
				//Almacenamos la foto de perfil en el FileSystem
				File mFileFotoPerfil;
				Calendar cal = Calendar.getInstance(Locale.getDefault());
				String date = DateFormat.format("yyyyMMddHHmmss", cal).toString();
				mFileFotoPerfil = new File(sPath, Integer.toString(usrLogueado.getId()) + "_" + date + ".jpg");
		          		        
		      	try 
		      	{
					FileOutputStream fOut = new FileOutputStream(mFileFotoPerfil);
					photo.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
					fOut.flush();
					fOut.close();
				} 
		      	catch (FileNotFoundException e) 
		      	{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Log.d(TAG,e.getMessage());
				} 
		      	catch (IOException e) 
		      	{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					Log.d(TAG,e.getMessage());
				}
		        
		      	//Guardamos el nombre de la foto de perfil en la información del usuario logueado
		      	String sNombreFotoPerfil = mFileFotoPerfil.getPath().substring(mFileFotoPerfil.getPath().lastIndexOf('/')+1);
		      	usrLogueado.setFoto(sNombreFotoPerfil);
		      	SingletonUsuarioLogueado.actualizarUsuarioLogueado(usrLogueado);
		      	//Habria que hacer el UPLOAD del archivo		          
				break;
			//Se captura el resultado de la galeria
			case GALLERY_INTENT_REQUEST:
			    if(resultCode == RESULT_OK)
			    {  
			    	uriImageSelected = data.getData();		    			    			    	
			    	doCrop(uriImageSelected);
			    }
			    break;
		}
	}	
	
	private void doCrop(Uri uriImage)
	{
		//Croping
    	Intent intent = new Intent("com.android.camera.action.CROP");
    	intent.setType("image/*");
    	//Chequeamos si hay una app instalada que realice el crop
    	List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent,0);
    	int size = list.size();
    	
    	if (size == 0 )
    	{
		   Toast.makeText(getActivity().getApplicationContext(), "Cant find crop app", Toast.LENGTH_LONG).show();
		   return;
    	} 
    	else
    	{
			intent.setData(uriImage);
			intent.putExtra("outputX", 200);
			intent.putExtra("outputY", 200);
			/*
			 * Deteriora la resolucion de la imagen
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			intent.putExtra("scale", true);
			*/
			intent.putExtra("return-data", true);
			
			Intent i = new Intent(intent);
			ResolveInfo res = list.get(0);
			i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			getActivity().startActivityForResult(i, CROP_FROM_CAMERA);
    	}
	}	
}
