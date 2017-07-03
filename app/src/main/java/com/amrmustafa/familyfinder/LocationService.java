package com.amrmustafa.familyfinder;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Iterator;

public class LocationService
 // extends Service
{
//  public static final String BROADCAST_ACTION = "Hello World";
//  public static boolean ServiceIsRun = false;
//  public static Location location = null;
//  Intent intent;
//  DatabaseReference mDatabase;
//  MyLocationListener myLocationListener;
//  MyLocationListenerHistory myLocationListenerHistory;
//  String paramAnonymousDataSnapshot1;
//  public IBinder onBind(Intent paramIntent)
//  {
//    return null;
//  }
//
//  public void onCreate()
//  {
//    super.onCreate();
//    this.intent = new Intent("Hello World");
//    ServiceIsRun = true;
//    this.mDatabase = FirebaseDatabase.getInstance().getReference();
//  }
//
//  public void onDestroy()
//  {
//    super.onDestroy();
//    this.myLocationListener.onClick(0);
//    this.myLocationListenerHistory.onClick(0);
//  }
//
//  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
//  {
//    new FileLoad(this).LoadData();
//    this.myLocationListener = new MyLocationListener(this);
//    this.myLocationListenerHistory = new MyLocationListenerHistory(this);
//    this.mDatabase.child("UsersUpdates").child(GlobalClass.UserUID).child("UpdateOrder").addValueEventListener(new ValueEventListener()
//    {
//      public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
//
//      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
//      {
//
//        paramAnonymousDataSnapshot1 = (String)paramAnonymousDataSnapshot.getValue(String.class);
//        new GlobalClass(LocationService.this.getApplicationContext()).UpdateCarts();
//      }
//    });
//    this.mDatabase.child("UsersUpdates").child(GlobalClass.UserUID).child("PlaceNotify").addValueEventListener(new ValueEventListener()
//    {
//      public void onCancelled(DatabaseError paramAnonymousDatabaseError) {}
//
//      public void onDataChange(DataSnapshot paramAnonymousDataSnapshot)
//      {
//        paramAnonymousDataSnapshot1 = (String)paramAnonymousDataSnapshot.getValue(String.class);
//        new GlobalClass(LocationService.this.getApplicationContext()).SendGPS((int)GlobalClass.getBatteryLevel(LocationService.this.getApplicationContext()));
//      }
//    });
//    return 2;
//  }
//
//  public class MyLocationListener
//    implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener
//  {
//    private final long LOC_FASTEST_UPDATE = 5000L;
//    private final long LOC_UPDATE_INTERVAL = 10000L;
//    private final String TAG = "LOC_RECURRING_SAMPLE";
//    Context context;
//    protected GoogleApiClient mGoogleApiClient;
//    protected LocationRequest mLocRequest;
//
//    public MyLocationListener(Context paramContext)
//    {
//      this.context = paramContext;
//      this.mGoogleApiClient = new Builder(paramContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
//      this.mLocRequest = new LocationRequest();
//      this.mLocRequest.setInterval(10000L);
//      this.mLocRequest.setFastestInterval(5000L);
//      this.mLocRequest.setPriority(100);
//      onStart();
//    }
//
//    protected void initializeUI()
//    {
//      if (LocationService.location == null) {
//        LocationService.location = LocationServices.FusedLocationApi.getLastLocation(this.mGoogleApiClient);
//      }
//    }
//
//    public void onClick(int paramInt)
//    {
//      switch (paramInt)
//      {
//      default:
//        return;
//      case 1:
//        startLocationUpdates();
//        return;
//      }
//    //  stopLocationUpdates();
//    }
//
//    public void onConnected(Bundle paramBundle)
//    {
//      initializeUI();
//      startLocationUpdates();
//    }
//
//    public void onConnectionFailed(ConnectionResult paramConnectionResult)
//    {
//      Log.d("LOC_RECURRING_SAMPLE", "Connection failed: ConnectionResult.getErrorCode() = " + paramConnectionResult.getErrorCode());
//    }
//
//    public void onConnectionSuspended(int paramInt)
//    {
//      Log.d("LOC_RECURRING_SAMPLE", "Connection was suspended for some reason");
//      this.mGoogleApiClient.connect();
//    }
//
//    public void onLocationChanged(Location paramLocation)
//    {
//      LocationService.location = paramLocation;
//    }
//
//    public void onStart()
//    {
//      this.mGoogleApiClient.connect();
//    }
//
//    public void onStop()
//    {
//      this.mGoogleApiClient.disconnect();
//    }
//
//    public void startLocationUpdates()
//    {
//      try
//      {
//        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocRequest, this);
//        return;
//      }
//      catch (Exception localException) {}
//    }
//
//    public void stopLocationUpdates()
//    {
//      try
//      {
//        LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
//        return;
//      }
//      catch (Exception localException) {}
//    }
//  }
//
//  public class MyLocationListenerHistory
//    implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener
//  {
//    private boolean IsCheckingNotify = false;
//    private final long LOC_FASTEST_UPDATE = 300000L;
//    private final long LOC_UPDATE_INTERVAL = 480000L;
//    private final String TAG = "LOC_RECURRING_SAMPLE";
//    Context context;
//    float distanceInMeters;
//    protected GoogleApiClient mGoogleApiClient;
//    protected LocationRequest mLocRequest;
//    Operations op;
//
//    public MyLocationListenerHistory(Context paramContext)
//    {
//      this.context = paramContext;
//      this.op = new Operations(paramContext);
//      this.mGoogleApiClient = new Builder(paramContext).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
//      this.mLocRequest = new LocationRequest();
//      this.mLocRequest.setInterval(480000L);
//      this.mLocRequest.setFastestInterval(300000L);
//      this.mLocRequest.setPriority(100);
//      onStart();
//    }
//
//    public void onClick(int paramInt)
//    {
//      switch (paramInt)
//      {
//      default:
//        return;
//      case 1:
//        startLocationUpdates();
//        return;
//      }
//     // stopLocationUpdates();
//    }
//
//    public void onConnected(Bundle paramBundle)
//    {
//      startLocationUpdates();
//    }
//
//    public void onConnectionFailed(ConnectionResult paramConnectionResult)
//    {
//      Log.d("LOC_RECURRING_SAMPLE", "Connection failed: ConnectionResult.getErrorCode() = " + paramConnectionResult.getErrorCode());
//    }
//
//    public void onConnectionSuspended(int paramInt)
//    {
//      Log.d("LOC_RECURRING_SAMPLE", "Connection was suspended for some reason");
//      this.mGoogleApiClient.connect();
//    }
//
//    public void onLocationChanged(Location paramLocation)
//    {
//      if (!this.IsCheckingNotify)
//      {
//        this.IsCheckingNotify = true;
//        new myCheckingNotify(paramLocation).start();
//      }
//      if (!this.op.isConnectingToInternet()) {
//        return;
//      }
//    String  paramLocation1 = GlobalClass.WebURL + "WebService3.asmx/UserTracking?PhoneUID=" + GlobalClass.PhoneUID + "&Latitude=" + String.valueOf(paramLocation.getLatitude()) + "&longitude=" + String.valueOf(paramLocation.getLongitude()) + "&BatteryLevel=" + String.valueOf((int)GlobalClass.getBatteryLevel(this.context));
//      new AsyTaskTracking().execute(new String[] { paramLocation1 });
//    }
//
//    public void onStart()
//    {
//      this.mGoogleApiClient.connect();
//    }
//
//    public void onStop()
//    {
//      this.mGoogleApiClient.disconnect();
//    }
//
//    public void startLocationUpdates()
//    {
//      try
//      {
//        LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocRequest, this);
//        return;
//      }
//      catch (Exception localException) {}
//    }
//
//    public void stopLocationUpdates()
//    {
//      try
//      {
//        LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
//        return;
//      }
//      catch (Exception localException) {}
//    }
//
//    public class AsyTaskTracking
//      extends AsyncTask<String, String, String>
//    {
//      public AsyTaskTracking() {}
//
//      /* Error */
//      protected String doInBackground(String... paramVarArgs)
//      {
//        // Byte code:
//        //   0: new 32	java/net/URL
//        //   3: dup
//        //   4: aload_1
//        //   5: iconst_0
//        //   6: aaload
//        //   7: invokespecial 35	java/net/URL:<init>	(Ljava/lang/String;)V
//        //   10: invokevirtual 39	java/net/URL:openConnection	()Ljava/net/URLConnection;
//        //   13: checkcast 41	java/net/HttpURLConnection
//        //   16: astore_1
//        //   17: new 43	java/io/BufferedInputStream
//        //   20: dup
//        //   21: aload_1
//        //   22: invokevirtual 47	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
//        //   25: invokespecial 50	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
//        //   28: invokestatic 56	com/hussienalrubaye/familyfinder/Operations:ConvertInputToStringNoChange	(Ljava/io/InputStream;)Ljava/lang/String;
//        //   31: pop
//        //   32: aload_1
//        //   33: invokevirtual 59	java/net/HttpURLConnection:disconnect	()V
//        //   36: goto +11 -> 47
//        //   39: astore_2
//        //   40: aload_1
//        //   41: invokevirtual 59	java/net/HttpURLConnection:disconnect	()V
//        //   44: aload_2
//        //   45: athrow
//        //   46: astore_1
//        //   47: aconst_null
//        //   48: areturn
//        // Local variable table:
//        //   start	length	slot	name	signature
//        //   0	49	0	this	AsyTaskTracking
//        //   0	49	1	paramVarArgs	String[]
//        //   39	6	2	localObject	Object
//        // Exception table:
//        //   from	to	target	type
//        //   17	32	39	finally
//        //   0	17	46	java/lang/Exception
//        //   32	36	46	java/lang/Exception
//        //   40	46	46	java/lang/Exception
//      }
//
//      protected void onPostExecute(String paramString) {}
//
//      protected void onPreExecute() {}
//
//      protected void onProgressUpdate(String... paramVarArgs) {}
//    }
//
//    class myCheckingNotify
//      extends Thread
//    {
//      Location mylocation;
//
//      public myCheckingNotify(Location paramLocation)
//      {
//        this.mylocation = paramLocation;
//      }
//
//      public void run()
//      {
//        int i = 0;
//        for (;;)
//        {
//          if (i < GlobalClass.familyCarts.size()) {}
//          try
//          {
//            if (((FamilyCart)GlobalClass.familyCarts.get(i)).location.getLatitude() > 0.0D)
//            {
//              MyLocationListenerHistory.this.distanceInMeters = this.mylocation.distanceTo(((FamilyCart)GlobalClass.familyCarts.get(i)).location);
//              if (MyLocationListenerHistory.this.distanceInMeters <= GlobalClass.DistanceToNofiy)
//              {
//                Boolean localBoolean = Boolean.valueOf(false);
//                Iterator localIterator = GlobalClass.NotifiedItems.iterator();
//                do
//                {
//    boolean              localObject = localBoolean;
//                  if (!localIterator.hasNext()) {
//                    break;
//                  }
//                } while (!((String)localIterator.next()).equals(((FamilyCart)GlobalClass.familyCarts.get(i)).ItemUID));
//                Object localObject = Boolean.valueOf(true);
//                if (!((Boolean)localObject).booleanValue())
//                {
//                  localObject = new Intent();
//                  ((Intent)localObject).setAction("com.example.Broadcast");
//                  ((Intent)localObject).putExtra("PlaceName", ((FamilyCart)GlobalClass.familyCarts.get(i)).PlaceName);
//                  ((Intent)localObject).putExtra("msg", ((FamilyCart)GlobalClass.familyCarts.get(i)).RequestedPhoneUID + MyLocationListenerHistory.this.context.getResources().getString(2131099727) + ((FamilyCart)GlobalClass.familyCarts.get(i)).ItemName + " from " + ((FamilyCart)GlobalClass.familyCarts.get(i)).PlaceName + MyLocationListenerHistory.this.context.getResources().getString(2131099705) + MyLocationListenerHistory.this.context.getResources().getString(2131099736));
//                  MyLocationListenerHistory.this.context.sendBroadcast((Intent)localObject);
//                  GlobalClass.NotifiedItems.add(((FamilyCart)GlobalClass.familyCarts.get(i)).ItemUID);
//                  MyLocationListenerHistory.access$002(MyLocationListenerHistory.this, false);
//                  return;
//                }
//              }
//            }
//          }
//          catch (Exception localException)
//          {
//            i += 1;
//          }
//        }
//      }
//    }
//  }
}