package www.sumanmyon.com.fitnessapp.Map;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
// Classes needed to initialize the map
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
// Classes needed to handle location permissions
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import java.util.List;
// Classes needed to add the location engine
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import java.lang.ref.WeakReference;
// Classes needed to add the location component
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.sumanmyon.com.fitnessapp.DataBase.MapDataBase;
import www.sumanmyon.com.fitnessapp.MainActivity;
import www.sumanmyon.com.fitnessapp.R;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

/**
 * Use the Mapbox Core Library to listen to device location updates
 */
public class Map2 extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {

    // Variables needed to initialize a map
    private static MapboxMap mapboxMap;
    private static MapView mapView;

    // Variables needed to handle location permissions
    private PermissionsManager permissionsManager;

    // Variables needed to add the location engine
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 10;

    // Variables needed to listen to location updates
    private MapLocationCallback callback = new MapLocationCallback(this);

    private Button startButton, endButton;
    private TextView distanceTextView;

    private boolean startBool = false;
    private boolean endBool = false;

    public static Map2 contextAct;

    public static Point originPoint, destinationPoint;
    public static Marker originMarker, destinationMarker;
    public static NavigationMapRoute navigationMapRoute;
    public static Style styleStyle = null;
    public static MapboxDirections mapboxDirections;

    public static Point origin = Point.fromLngLat(85.300140,27.700769);//(23.588098, 37.176164);
    public static Point destination = Point.fromLngLat(85.3206,27.70169);//(33.601845, 37.184080);
    public static DirectionsRoute currentRoute;
    private static final String ROUTE_SOURCE_ID = "route-source-id";
    private static final String ROUTE_LAYER_ID = "route-layer-id";

    MapDataBase mapDataBase;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.api_key));

        // This contains the MapView in XML and needs to be called after the access token is configured.
        setContentView(R.layout.activity_map_box);

        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid");

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        contextAct = this;

        castView();
        start();
        end();
    }

    private void castView(){
        startButton = findViewById(R.id.map_start_button);
        endButton = findViewById(R.id.map_end_button);
        distanceTextView = findViewById(R.id.map_distance_text);
        endButton.setEnabled(false);
    }

    private void start(){
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBool = true;
            }
        });
    }

    private void end(){
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endBool = true;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        styleStyle = style;
                        enableLocationComponent(style);
                        //setPathInMap(style);
                        //openMap();

                    }
                });

        //mapboxMap.getUiSettings().setZoomControlsEnabled(true);
        mapboxMap.getUiSettings().setZoomGesturesEnabled(true);
        mapboxMap.getUiSettings().setScrollGesturesEnabled(true);
        mapboxMap.getUiSettings().setAllGesturesEnabled(true);
    }

    private void openMap() {
            LatLng latLng = new LatLng(27.700769,85.300140);
            originMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng));
            originPoint = Point.fromLngLat(latLng.getLatitude(), latLng.getLongitude());
            originMarker.setTitle("Start");

            LatLng latLng2 = new LatLng(27.70169,85.3206);
            destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(latLng2));
            destinationPoint = Point.fromLngLat(latLng2.getLatitude(), latLng2.getLongitude());
            destinationMarker.setTitle("Destination");

            // Get the directions route from the Mapbox Directions API
            //getRoute(origin, destination);
            getDistanceBetweenTwoPoints(origin,destination);

    }

    private void getRoute(Point originPoint, Point destinationPoint){
        mapboxDirections = MapboxDirections.builder()
                .origin(originPoint)
                .destination(destinationPoint)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_WALKING)
                .accessToken(Mapbox.getAccessToken())
                .build();

        mapboxDirections.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                System.out.println(call.request().url().toString());
                //Toast.makeText(contextAct,response.message(),Toast.LENGTH_SHORT).show();
                // You can get the generic HTTP info about the response
                System.out.println("Response code: " + response.code());
                if (response.body() == null) {
                    System.out.println("No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    System.out.println("No routes found");
                    return;
                }

                // Get the directions route
                currentRoute = response.body().routes().get(0);

                // Make a toast which displays the route's distance
                Toast.makeText(contextAct, currentRoute.distance().toString(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                Toast.makeText(contextAct,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDistanceBetweenTwoPoints(Point origin, Point destination) {
        //Route the real time path
        NavigationRoute.builder(contextAct)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        if(response==null){
                            Log.e("MapCallHere","No route found. Check access and token");
                            return;
                        }else if(response.body().routes().size() == 0) {
                            Log.e("MapCallHere","No route found.");
                            return;
                        }

                        DirectionsRoute currentRoute = response.body().routes().get(0);
                        if(navigationMapRoute!=null){
                            navigationMapRoute.removeRoute();
                        }else {
                            navigationMapRoute = new NavigationMapRoute(null,mapView, mapboxMap);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                        try{
                            Thread.sleep(3000);
                        }catch (Exception e){

                        }

                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                        Log.e("MapCallHere","3 "+t.getMessage());
                    }
                });
    }

    /**
     * Initialize the Maps SDK's LocationComponent
     */
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Set the LocationComponent activation options
            LocationComponentActivationOptions locationComponentActivationOptions =
                    LocationComponentActivationOptions.builder(this, loadedMapStyle)
                            .useDefaultLocationEngine(false)
                            .build();

            // Activate with the LocationComponentActivationOptions object
            locationComponent.activateLocationComponent(locationComponentActivationOptions);

            // Enable to make component visible
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.NORMAL);

            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    /**
     * Set up the LocationEngine and the parameters for querying the device's location
     */
    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest
                .Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME)
                .build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "Permission explain", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            if (mapboxMap.getStyle() != null) {
                enableLocationComponent(mapboxMap.getStyle());
            }
        } else {
            Toast.makeText(this, "permission", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private static class MapLocationCallback implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<Map2> activityWeakReference;

        MapLocationCallback(Map2 activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        boolean isTrue = false;

        @Override
        public void onSuccess(LocationEngineResult result) {
            Map2 activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }else {

                }

                // Create a Toast which displays the new location's coordinates
                //Toast.makeText(activity,location.getLatitude()+"\n"+location.getLongitude(),Toast.LENGTH_SHORT).show();


                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());

                    if(activity.startBool == true){
                        activity.startBool = false;
                        activity.startButton.setEnabled(false);
                        activity.endButton.setEnabled(true);
                        isTrue = true;

                        //Add maker to start point
                        if(originMarker!=null){
                            activity.mapboxMap.removeMarker(originMarker);
                        }
                       // LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        originMarker = activity.mapboxMap.addMarker(new MarkerOptions().position(latLng));
                        originPoint = Point.fromLngLat(latLng.getLongitude(),latLng.getLatitude());
                        originMarker.setTitle("Start");
                    }

                    if(activity.endBool == true){
                        //Toast.makeText(activity,activity.endLat+"\n"+activity.endLong,Toast.LENGTH_SHORT).show();
                        activity.endBool = false;
                        activity.startButton.setEnabled(true);
                        activity.endButton.setEnabled(false);
                        isTrue = false;

                        //Add maker to destination point
                        if(destinationMarker!=null){
                            activity.mapboxMap.removeMarker(destinationMarker);
                        }
                        //LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                        destinationMarker = activity.mapboxMap.addMarker(new MarkerOptions().position(latLng));
                        destinationPoint = Point.fromLngLat(latLng.getLongitude(),latLng.getLatitude());
                        destinationMarker.setTitle("Destination");
                        getDistanceBetweenTwoPoints(originPoint, destinationPoint);

                        contextAct.mapDataBase = new MapDataBase(contextAct);
                        boolean isInserted = contextAct.mapDataBase.insert(contextAct.distanceTextView.getText().toString(),contextAct.uid);
                        if(isInserted == true){
                            showMessage("Inserted Successfully");
                        }else {
                            showMessage("Insertion failed");
                        }
                    }

                    if(isTrue == true){
                        getDistanceBetweenTwoPoints(originPoint, Point.fromLngLat(location.getLongitude(),location.getLatitude()));
                        //getDistanceBetweenTwoPoints(originPoint, Point.fromLngLat(27.70169, 85.3206));
                    }

                }
            }
        }

        private void showMessage(String message) {
            Toast.makeText(contextAct,message,Toast.LENGTH_LONG).show();

        }


        private void getDistanceBetweenTwoPoints(Point origin, Point destination) {

            //Route the real time path
            NavigationRoute.builder(contextAct)
                    .accessToken(Mapbox.getAccessToken())
                    .origin(origin)
                    .destination(destination)
                    .build()
                    .getRoute(new Callback<DirectionsResponse>() {
                        @Override
                        public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                            if(response==null){
                                Log.e("MapCallHere","No route found. Check access and token");
                                return;
                            }else if(response.body().routes().size() == 0) {
                                Log.e("MapCallHere","No route found.");
                                return;
                            }

                            DirectionsRoute currentRoute = response.body().routes().get(0);
                            if(navigationMapRoute!=null){
                                navigationMapRoute.removeRoute();
                            }else {
                                navigationMapRoute = new NavigationMapRoute(null,mapView, mapboxMap);
                            }
                            navigationMapRoute.addRoute(currentRoute);

                            //calculate distance
                            double distance = currentRoute.distance();
                            //double disInKM = distance / 1000;
                            contextAct.distanceTextView.setText("Distance : "+String.valueOf(distance)+" m");
                           
                        }

                        @Override
                        public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                            Log.e("MapCallHere","3 "+t.getMessage());
                        }
                    });
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can not be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            Log.d("LocationChangeActivity", exception.getLocalizedMessage());
            Map2 activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
