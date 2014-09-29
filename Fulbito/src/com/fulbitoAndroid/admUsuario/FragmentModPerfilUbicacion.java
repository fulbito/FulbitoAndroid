//Se utiliza el handler porque el mapa tarda un poco en cargarse y el getMap() puede tirar null
        //y terminar la aplicación con excepcion        
        package com.fulbitoAndroid.admUsuario;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.FulbitoException;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.LocationListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentModPerfilUbicacion extends Fragment{
	//Elementos de la interfaz grafica
	EditText edtTxtDireccion;
	ImageButton btnBuscar;
	//Elementos para manejar el fragment de Google Maps
	FragmentManager fmFragmentManager;	
	SupportMapFragment mfMapFragment;
	//Mapa de Google Maps
	GoogleMap gmGoogleMap;
	
	private ProgressDialog pDialog;
	
	Handler handler;
	LocationListener locationListener;    
    Location mCurrentLocation;    
    Circle mCircle;
    SeekBar seekBar;
    TextView seekBarValue;
    
    static final String TAG="FragmentModPerfilUbicacion";
    
    Usuario usrLogueado = null;
    	
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mod_perfil_ubicacion, container, false);                     
    }
    
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state); 
        //Obtenemos el usuario logueado
        usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();
        //Obtenemos los elementos de la interfaz grafica
        edtTxtDireccion = (EditText) getView().findViewById(R.id.edtTxtDireccion);                    
        btnBuscar = (ImageButton) getView().findViewById(R.id.button1);
                    
        btnBuscar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
	        {   				
	        	String sDireccion = edtTxtDireccion.getText().toString();
	        	vBuscarDireccion(sDireccion);	        	
	        }
        });                
        
        //Agregamos el google map fragment        
      	fmFragmentManager = getChildFragmentManager();        
      	mfMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = fmFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.loMapContainerModPerfilUbicacion, mfMapFragment);
        fragmentTransaction.commit();        

        InitMapAsyncTask initMapTask = new InitMapAsyncTask();
        initMapTask.execute();
		
        vSetearSeekBar();
    }    
    
    class InitMapAsyncTask extends AsyncTask<Void, Void, GoogleMap> {
		
		private SupportMapFragment mapFragmAux;
		
        @Override 
        protected void onPreExecute() {
        	mapFragmAux = mfMapFragment;
	    	
	    	pDialog = new ProgressDialog(getActivity());
	        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        pDialog.setTitle("Procesando...");
	        pDialog.setMessage("Espere por favor...");
	        pDialog.setCancelable(false);
	        pDialog.setIndeterminate(true);  
	    	pDialog.show();
        }

        @Override 
        protected GoogleMap doInBackground(Void... par) {
  
        	GoogleMap gmAux = mapFragmAux.getMap();
    		while(gmAux == null) 
    		{
    			//try {Thread.sleep(50000);} catch(Exception e){};
    			gmAux = mapFragmAux.getMap();
    		}		

	        return gmAux;
        }

        @Override 
        protected void onProgressUpdate(Void... prog) {
        }

        @Override 
        protected void onPostExecute(GoogleMap gmResult) {
        	
        	if(pDialog!=null) 
        	{
        		pDialog.dismiss();
        		//pDialog.setEnabled(true);
			}
        	
        	gmGoogleMap = gmResult;
        	//habilitamos en el mapa el botón que marca nuestra ubicacion actual
			gmGoogleMap.setMyLocationEnabled(true);
			//seteamos la accion al hacer click en el boton "mi ubicacion actual"
			gmGoogleMap.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
		        @Override
		        public boolean onMyLocationButtonClick() {
		        	//Obtenemos la ubicacion actual
		        	vObtenerUbicacionActual();
		            return true;
		        }
		    });
			/*
			if(usrLogueado.getUbicacion().length() > 0)
				vBuscarDireccion(usrLogueado.getUbicacion());
			else
				vObtenerUbicacionActual();
			*/
        }
	}
    
    public void vSetearSeekBar(){
        seekBar = (SeekBar)getView().findViewById(R.id.seekBarRadioUbicacion);
        seekBarValue = (TextView)getView().findViewById(R.id.txtKmRadios);

        seekBar.incrementProgressBy(1000);
        seekBar.setMax(30000);        
        
        if(usrLogueado.getRadioBusqueda() == 0)
        {
        	seekBar.setProgress(3000);                    
        }
        else
        {
        	seekBar.setProgress((int) usrLogueado.getRadioBusqueda());        
        }
        
        seekBarValue.setText(Integer.toString(seekBar.getProgress()/1000) + " km");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            	mCircle.setRadius(progress);
            	seekBarValue.setText(Integer.toString(progress/1000) + " Km");
            	
            	usrLogueado.setRadioBusqueda(progress);
                SingletonUsuarioLogueado.modificarRadioBusqueda(progress);
            	
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    
    public void vObtenerUbicacionActual(){
    	try
    	{
    		GPSTracker gps = new GPSTracker(getActivity());

    		//Verificamos que se pudo obtener la ubicación actual    
    		if(gps.canGetLocation())
    		{       
    			double latitude = gps.getLatitude();
    			double longitude = gps.getLongitude();

    			vDibujarMarcador(latitude, longitude);
    			
    			Location location = gps.getLocation();
    			vObtenerDireccion(location);
    		}
    		else
    		{
    			// no se pudo obtener la ubicacion actual
    			// el GPS o internet no estan habilitados
    			// preguntamos si el usuario quiere habilitar el GPS
    			gps.showSettingsAlert();
    		}		
    	} 
    	catch(FulbitoException feException)
    	{
    		Toast.makeText(getActivity().getApplicationContext(), 
					R.string.errMsjUbicacionActual, Toast.LENGTH_LONG).show();
    	}
    	
		return;
    }
    
    private void vDibujarMarcador(double latitude, double longitude){
    	gmGoogleMap.clear();
    	LatLng currentPosition = new LatLng(latitude, longitude);    	
    	
    	double radiusInMeters = 3000.0;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill        
        
        CircleOptions circleOptions = new CircleOptions().center(currentPosition).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = gmGoogleMap.addCircle(circleOptions);
        
        seekBar.setProgress(3000);
    	
        gmGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,15));
        // Zoom in, animating the camera.
        gmGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        gmGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);    
        gmGoogleMap.addMarker(new MarkerOptions().position(currentPosition));    	
	}
    
    public void vObtenerDireccion(Location location) {
    	(new ObtenerDireccionTask(getActivity())).execute(location);
    } 
    
    public void vBuscarDireccion(String sDireccion) {
    	(new GetLatLongTask(getActivity())).execute(sDireccion);
    }
    
    /**
     * Subclase de AsyncTask que invoca a getFromLocation() en
     * background.
     * La definición de clase tiene estos tipos genéricos: 
     * Location - Un objeto que contiene la ubicación actual. 
     * Void - indica que las unidades de avance no se utilizan 
     * String - String que contiene la direccion a retornar en el OnPostExecute()
     */
     class ObtenerDireccionTask extends AsyncTask<Location, Void, String> {
         
    	 Context mContext;
    	 String sLatitud = "";
    	 String sLongitud = "";
         
         public ObtenerDireccionTask(Context context) {
             super();
             mContext = context;
         }

         /*
          * Obtiene la direccion de una ubicación a partir de 
          * una latitud y longitud
          *
          * @params params Uno o mas objetos Location
          * @return Un string que retorna la direccion relacionada 
          * con los puntos latitud y longitud pasados por parametro
          */
         @Override
         protected String doInBackground(Location... params) {
             
        	 Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
             // Get the current location from the input parameter list
             Location loc = params[0];
             // Create a list to contain the result address
             List<Address> addresses = null;
             try 
             {
                 //Return 1 address.            	 
            	 addresses = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            	 sLatitud = Double.toString(loc.getLatitude());
            	 sLongitud = Double.toString(loc.getLongitude());
             } 
             catch (IOException e1) 
             {
            	 Log.e("ObtenerDireccionTask", "IO Exception in getFromLocation() - " + e1.getMessage());
            	 e1.printStackTrace();
            	 return ("IO Exception trying to get address");
             } 
             catch (IllegalArgumentException e2) 
             {
            	 // Error message to post in the log
				 String errorString = "Illegal arguments " +
						 Double.toString(loc.getLatitude()) +
						 " , " +
						 Double.toString(loc.getLongitude()) +
						 " passed to address service";
				 Log.e("ObtenerDireccionTask", errorString);
				 e2.printStackTrace();
				 return errorString;
             }
             
             // If the reverse geocode returned an address
             if (addresses != null && addresses.size() > 0) 
             {
                 // Get the first address
                 Address address = addresses.get(0);
                 //Format the first line of address (if available), city, and country name.
                 String addressText = String.format(
                         "%s, %s",
                         // If there's a street address, add it
                         address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                         // Locality is usually a city
                         address.getLocality()/*,
                         // The country of the address
                         address.getCountryName()*/);
                 // Return the text
                 return addressText;
             } 
             else 
             {
                 return "No address found";
             }
         }
         
         /**
          * A method that's called once doInBackground() completes. Turn
          * off the indeterminate activity indicator and set
          * the text of the UI element that shows the address. If the
          * lookup failed, display the error message.
          */
         @Override
         protected void onPostExecute(String address) {
             // Display the results of the lookup.             
             edtTxtDireccion.setText(address);
             
             usrLogueado.setUbicacion(address);
             usrLogueado.setUbicacionLatitud(sLatitud);
             usrLogueado.setUbicacionLongitud(sLongitud);
             SingletonUsuarioLogueado.modificarUbicacionDesc(usrLogueado.getUbicacion());
             SingletonUsuarioLogueado.modificarUbicacionLatitud(usrLogueado.getUbicacionLatitud());
             SingletonUsuarioLogueado.modificarUbicacionLongitud(usrLogueado.getUbicacionLongitud());
         }
     }
     
     /*
      * Subclase de AsyncTask que invoca a getFromLocationName() en
      * background.
      * La definición de clase tiene estos tipos genéricos: 
      * Location - Un objeto que contiene la ubicación actual. 
      * Void - indica que las unidades de avance no se utilizan 
      * String - String que contiene la direccion a retornar en el OnPostExecute()
      */
      class GetLatLongTask extends AsyncTask<String, Void, Address> {
    	  
          Context mContext;
          
          public GetLatLongTask(Context context) {
              super();
              mContext = context;
          }

          /**
           * Get a Geocoder instance, get the latitude and longitude
           * look up the address, and return it
           *
           * @params params One or more Location objects
           * @return A string containing the address of the current
           * location, or an empty string if no address can be found,
           * or an error message
           */
          @Override
          protected Address doInBackground(String... params) {
        	  
              Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
              // Get the current location from the input parameter list
              String sDireccion = params[0];
              // Create a list to contain the result address
              List<Address> addresses = null;
              try 
              {
            	  // Return 1 address.
            	  addresses = geocoder.getFromLocationName(sDireccion, 1);
              } 
              catch (IOException e1) 
              {
	              Log.e("GetLatLongTask", "IO Exception in getFromLocationName()");
	              e1.printStackTrace();
	              return null;
              } 
              catch (IllegalArgumentException e2) 
              {
            	  // Error message to post in the log
            	  String errorString = "Illegal arguments " +
            			  sDireccion +
            			  " passed to address service";
	              Log.e("GetLatLongTask", errorString);
	              e2.printStackTrace();
	              return null;
              }                        
              
              // If the reverse geocode returned an address
              if (addresses != null && addresses.size() > 0) 
              {
                  // Get the first address
                  Address address = addresses.get(0);                  
                  return address;
              } 
              else 
              {
                  return null;
              }
          }
          
          /**
           * A method that's called once doInBackground() completes. Turn
           * off the indeterminate activity indicator and set
           * the text of the UI element that shows the address. If the
           * lookup failed, display the error message.
           */
          @Override
          protected void onPostExecute(Address address) {
        	 
        	  // Display the results of the lookup.
        	  if(address != null)
        	  {
        		  String addressText = String.format(
        				  "%s, %s",
        				  // If there's a street address, add it
        				  address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
        				  // Locality is usually a city
        				  address.getLocality()/*,
        				  // The country of the address
        				  address.getCountryName()*/);
			            
        		  vDibujarMarcador(address.getLatitude(), address.getLongitude());
			      
        		  edtTxtDireccion.setText(addressText);
        		  usrLogueado.setUbicacion(addressText);
        		  usrLogueado.setUbicacionLatitud(Double.toString(address.getLatitude()));
        		  usrLogueado.setUbicacionLongitud(Double.toString(address.getLongitude()));
        		  SingletonUsuarioLogueado.modificarUbicacionDesc(usrLogueado.getUbicacion());
        		  SingletonUsuarioLogueado.modificarUbicacionLatitud(usrLogueado.getUbicacionLatitud());
        		  SingletonUsuarioLogueado.modificarUbicacionLongitud(usrLogueado.getUbicacionLongitud());
        	  }
        	  else
        	  {
        		  Toast.makeText(getActivity().getApplicationContext(), 
  	        			"No se pudo obtener la localización de la direccion en el mapa.", Toast.LENGTH_LONG).show();
        	  }
          }
      }
}


     