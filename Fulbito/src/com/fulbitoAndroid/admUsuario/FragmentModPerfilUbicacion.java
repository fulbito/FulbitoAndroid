package com.fulbitoAndroid.admUsuario;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.fulbitoAndroid.clases.SingletonUsuarioLogueado;
import com.fulbitoAndroid.clases.Usuario;
import com.fulbitoAndroid.fulbito.FulbitoException;
import com.fulbitoAndroid.fulbito.R;
import com.fulbitoAndroid.herramientas.GPSTracker;
import com.fulbitoAndroid.herramientas.RespuestaWebService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentModPerfilUbicacion extends Fragment{
	private static View view;
	//Elementos de la interfaz grafica
	EditText 		edtTxtDireccion;
	ImageButton 	btnBuscar;
	Button 			btnGuardarUbicacion;
	ProgressDialog 	pDialog;
	SeekBar 		seekBar;
	TextView 		seekBarValue;
	//Elementos para manejar el fragment de Google Maps
	FragmentManager 	fmHome;
	FragmentManager 	fmFragmentManager;	
	SupportMapFragment 	mfMapFragment;
	//Mapa de Google Maps
	GoogleMap	gmGoogleMap;			   
    Circle 		mCircle;        
    
    static final String TAG="FragmentModPerfilUbicacion";
    static final int I_RADIO_BUSQUEDA_MAX = 30000;
    static final int I_RADIO_BUSQUEDA_DEFAULT = 3000;
    
    Usuario usrLogueado = null;
    	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mod_perfil_ubicacion, container, false);  
        //Obtenemos el FragmentManager del HomeActivity que es donde se encuentra el MapFragment
        fmHome = getParentFragment().getFragmentManager();     
        //Obtenemos el MapFragment
        mfMapFragment = (SupportMapFragment) fmHome.findFragmentById(R.id.map);
        //Cargamos el mapa si se puede
        setUpMapIfNeeded();
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state); 
        
        //Obtenemos el usuario logueado
        usrLogueado = SingletonUsuarioLogueado.getUsuarioLogueado();
        /*
      	fmFragmentManager = getChildFragmentManager();        
      	mfMapFragment = SupportMapFragment.newInstance();
        FragmentTransaction fragmentTransaction = fmFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.loMapContainerModPerfilUbicacion, mfMapFragment, "MAP_FRAGMENT");
        fragmentTransaction.commit();
        Log.d("FragmentModPerfilUbicacion","getChildFragmentManager");
        */
        
        //Obtenemos los elementos de la interfaz grafica
        edtTxtDireccion 	= (EditText) getView().findViewById(R.id.edtTxtDireccion);                    
        btnBuscar 			= (ImageButton) getView().findViewById(R.id.btnBuscarDireccion);
        btnGuardarUbicacion = (Button) getView().findViewById(R.id.btnGuardarUbicacion);
        seekBar 			= (SeekBar)getView().findViewById(R.id.seekBarRadioUbicacion);
        seekBarValue 		= (TextView)getView().findViewById(R.id.txtKmRadios);
        
        //Cargamos el mapa si se puede
        setUpMapIfNeeded();
        
        //Seteamos el evento OnClick para el boton Buscar Direccion
        btnBuscar.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
	        {   				
	        	String sDireccion = edtTxtDireccion.getText().toString();
	        	vBuscarDireccion(sDireccion);	        	
	        }
        });                                
        
        //Seteamos el evento OnClick para el boton Guardar Ubicacion
        btnGuardarUbicacion.setOnClickListener(new OnClickListener() 
        {   public void onClick(View v) 
	        {   				
        		//Se llama al WebService de Modificar Ubicacion
        		ModificarUbicacionAsyncTask modUbicacionTask = new ModificarUbicacionAsyncTask();
        		modUbicacionTask.execute();        			          
	        }
        });
        //Seteamos el seek bar (barra para elegir el radio de juego)               
        vSetearSeekBar();
        //Agregamos la marcas de ubicación del usuario al mapa
        setUpMap();      
    }    
    
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */    
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gmGoogleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
        	gmGoogleMap = mfMapFragment.getMap();        	                	                        
        }
    }
    
    /**
     * This is where we can add markers or lines, add listeners or move the
     * camera.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap}
     * is not null.
     */
    private void setUpMap() {
    	if (gmGoogleMap != null) {
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

    		if(usrLogueado.getUbicacion().length() > 0)
    		{
    			double dLatitud = Double.parseDouble(usrLogueado.getUbicacionLatitud());
    			double dLongitud = Double.parseDouble(usrLogueado.getUbicacionLongitud());
    			vDibujarMarcador(dLatitud, dLongitud);
    			edtTxtDireccion.setText(usrLogueado.getUbicacion());
    		}				
    		else
    			vObtenerUbicacionActual();
        }    	
    }
    
    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then 
     **** app will crash ****/
    @Override
    public void onDestroyView() {    	
        super.onDestroyView();
        FragmentManager fmHome = getParentFragment().getFragmentManager();
        if (gmGoogleMap != null) {
        	fmHome.beginTransaction()
                .remove(fmHome.findFragmentById(R.id.map)).commit();
            gmGoogleMap = null;
        }
    }
    
    void vMostrarProgressDialog(){
    	if(pDialog == null)
    	{
    		pDialog = new ProgressDialog(getActivity());
    		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setTitle("Procesando...");
            pDialog.setMessage("Espere por favor...");
            pDialog.setCancelable(false);
            pDialog.setIndeterminate(true);  
    	}    		
    	
    	if(pDialog.isShowing() == false)
        	pDialog.show();
    }
    
    void vOcultarProgressDialog(){
    	if(pDialog != null && pDialog.isShowing() == true)
        	pDialog.dismiss();
    }    
    
    public void vSetearSeekBar(){
    	//El radio de juego de un usuario se incrementara de a 1 KM (1000 mts)
        seekBar.incrementProgressBy(1000);
        //El máximo de radio sera 30 KM (30000 mts)
        seekBar.setMax(I_RADIO_BUSQUEDA_MAX);        
        
        if(usrLogueado.getRadioBusqueda() == 0)
        {
        	//3 KM es el radio Default
        	seekBar.setProgress(I_RADIO_BUSQUEDA_DEFAULT);                    
        }
        else
        {
        	seekBar.setProgress((int) usrLogueado.getRadioBusqueda());        
        }
        
        //Seteamos en el texto el valor del radio de busqueda para que sea visible por el usuario
        seekBarValue.setText(Integer.toString(seekBar.getProgress()/1000) + " km");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            	mCircle.setRadius(progress);
            	
            	seekBarValue.setText(Integer.toString(progress/1000) + " Km");
            	//En la base de datos lo almacenamos en KM
            	usrLogueado.setRadioBusqueda(progress/1000);                            
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
    	
    	double radiusInMeters;
        int strokeColor = 0xffff0000; //red outline
        int shadeColor = 0x44ff0000; //opaque red fill   
        
        if(usrLogueado.getRadioBusqueda() == 0)
        {
        	//3 KM es el radio Default
        	radiusInMeters = I_RADIO_BUSQUEDA_DEFAULT;              	
        	usrLogueado.setRadioBusqueda(I_RADIO_BUSQUEDA_DEFAULT);
        }
        else
        {
        	radiusInMeters = (int) usrLogueado.getRadioBusqueda() * 1000;        	
        }
        
        int iProgress = (int) Math.round(radiusInMeters);        
        
        //Agregamos el circulo en el mapa para indicar el radio de busqueda de partidos
        CircleOptions circleOptions = new CircleOptions().center(currentPosition).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(8);
        mCircle = gmGoogleMap.addCircle(circleOptions);                
    	
        gmGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentPosition,15));
        // Zoom in, animating the camera.
        gmGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        gmGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);    
        gmGoogleMap.addMarker(new MarkerOptions().position(currentPosition));
        
        seekBar.setProgress(iProgress);
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
         
         @Override 
         protected void onPreExecute() { 
        	 vMostrarProgressDialog();
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
                 //Formamos la descripcion completa de la ubicacion
                 String addressText = "";
                 for(int i=0; i <= address.getMaxAddressLineIndex(); i++)
                 {
                	 addressText += address.getAddressLine(i) + ", ";
                 }			            
                 addressText = addressText.substring(0, addressText.length() - 2);

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
             //Guardamos temporalmente los datos de ubicación modificados
             usrLogueado.setUbicacion(address);
             usrLogueado.setUbicacionLatitud(sLatitud);
             usrLogueado.setUbicacionLongitud(sLongitud); 
             
             vOcultarProgressDialog();
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
          
          @Override 
          protected void onPreExecute() { 
         	 vMostrarProgressDialog();
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
                  //Formamos la descripción completa de la ubicacion
        		  String addressText = "";
                  for(int i=0; i <= address.getMaxAddressLineIndex(); i++)
                  {
                 	 addressText += address.getAddressLine(i) + ", ";
                  }			            
                  addressText = addressText.substring(0, addressText.length() - 2);
                  //Dibujamos el marcador
        		  vDibujarMarcador(address.getLatitude(), address.getLongitude());
			      
        		  edtTxtDireccion.setText(addressText);
        		  //Guardamos temporalmente los datos de ubicación modificados
        		  usrLogueado.setUbicacion(addressText);
        		  usrLogueado.setUbicacionLatitud(Double.toString(address.getLatitude()));
        		  usrLogueado.setUbicacionLongitud(Double.toString(address.getLongitude()));
        	  }
        	  else
        	  {
        		  Toast.makeText(getActivity().getApplicationContext(), 
  	        			"No se pudo obtener la localización de la direccion en el mapa.", Toast.LENGTH_LONG).show();
        	  }
        	  
        	  vOcultarProgressDialog();
          }
      }
      
      class ModificarUbicacionAsyncTask extends AsyncTask<Void, Void, Boolean> {
          
      	private Usuario cUsrModificado 	= null;
  		private String sError 		= "";
  		
          @Override 
          protected void onPreExecute() {  	  
        	
        	cUsrModificado = new Usuario();
        	cUsrModificado.vCopiar(usrLogueado);
        	  
        	vMostrarProgressDialog();
          }

          @Override 
          protected Boolean doInBackground(Void... par) {
          	Boolean bResult = true;
  	    	
  	    	//Invocamos el Web Service de Login
          	WebServiceUsuario wsLogin = new WebServiceUsuario(getActivity().getApplicationContext());
  	    	RespuestaWebService cRespWS = new RespuestaWebService();
  	    	try
  	    	{
  	    		switch(wsLogin.bModificarDatosUsuario(cUsrModificado, cRespWS))
  	        	{
  	        		case OK:
  	        			//el logueo automatico fue exitoso
  	        			//Seteamos los datos del usuario logueado
  	        			//SingletonUsuarioLogueado.registrarUsuarioLogueado(cUsrLogin);
  	        			bResult = true;
  	        			break;
  	        		case NO_CONNECTION:
  	        			//no hay conexión a internet
  	        			//sError = getString(R.string.errMsjSinConexion);
  	        			bResult = false;
  	        			break;
  	        		case ERROR:
  	        			//el logueo automatico no fue exitoso
  	        			//El webservice envio una respuesta con error
  	        			//sError = cRespWS.sGetData();
  	        			bResult = false;
  	        			break;	    		
  	        	}
  	    	}
  	    	catch(FulbitoException feException)
  	    	{
  	    		//sError = getString(R.string.errMsjLogin);
  	    		bResult = false;
  	    	}
  	    	
  	        return bResult;
          }

          @Override 
          protected void onProgressUpdate(Void... prog) {
          }

          @Override 
          protected void onPostExecute(Boolean bResult) {

          	vOcultarProgressDialog();
          	
          	if(bResult == true)
          	{
          		//Se modificaron correctamente los datos de prefil en el servidor
          		//entonces modificamos los datos en el archivo SharedPreferences
          		SingletonUsuarioLogueado.modificarUbicacionDesc(usrLogueado.getUbicacion());
	            SingletonUsuarioLogueado.modificarUbicacionLatitud(usrLogueado.getUbicacionLatitud());
	            SingletonUsuarioLogueado.modificarUbicacionLongitud(usrLogueado.getUbicacionLongitud());
	            SingletonUsuarioLogueado.modificarRadioBusqueda(usrLogueado.getRadioBusqueda());
          	}
  	        else
  	        {
  	        	Toast.makeText(getActivity().getApplicationContext(), 
  	        		sError, Toast.LENGTH_LONG).show();
  	        }
          }
  	}
}


     