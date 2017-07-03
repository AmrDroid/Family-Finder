package com.amrmustafa.familyfinder;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;

import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by hussienalrubaye on 9/14/15.
 */
public class GlobalClass {

    public static int AdditionUsers;
    public static int DistanceToCheck;
    public static int DistanceToNofiy;
    public static int MaxUsersToView;
    public static ArrayList<String> NotifiedItems = new ArrayList();
    public static int TimeToSendLocation;
    public static String Uid;
    public static ArrayList<FamilyCart> familyCarts;
    Context context;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    public  static String WebURL="http://myphonelocations.alruabye.net/WebService3.asmx/";
    public  static String WebURL2="http://myphonelocations.alruabye.net/";
public static String APPURL="phonelocation.example.asuss550c.phonelocation";
    public  static  String UserUID=null ;
    public  static  String PhoneUID=null ;
    public  static  String UserName=null ;
    public  static  int RecordNumbers=40 ; //last location visited

    static
    {
        APPURL = "com.alrubaye.familyfinder";
        UserUID = null;
        PhoneUID = null;
        AdditionUsers = 0;
        MaxUsersToView = 8;
        UserName = null;
        RecordNumbers = 40;
        DistanceToCheck = 50;
        DistanceToNofiy = 1000;
        TimeToSendLocation = 300000;
        familyCarts = new ArrayList();
    }





    public GlobalClass(Context paramContext)
    {
        this.context = paramContext;
    }

    public static String PostTimeLeft(Date paramDate)
    {

        try
        {
            long l1 = new Date().getTime() - paramDate.getTime();
            long l2 = TimeUnit.MILLISECONDS.toDays(l1);
            if (l2 > 0L) {
                return l2 + " Days left";
            }
            l2 = TimeUnit.MILLISECONDS.toHours(l1);
            if (l2 > 0L) {
                return l2 + " Hours left";
            }
            l2 = TimeUnit.MILLISECONDS.toMinutes(l1);
            if (l2 > 0L) {
                return l2 + " Minutes left";
            }
            l1 = TimeUnit.MILLISECONDS.toSeconds(l1);
            if (l1 > 0L) {
                return l1 + " Seconds left";
            }
            return "Now";
        }
        catch (Exception para1mDate) {}
        return "Not active";
    }

    public static double getBatteryLevel(Context paramContext)
    {

     Intent paramContext1 = paramContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        int i = paramContext1.getIntExtra("level", -1);
        int j = paramContext1.getIntExtra("scale", -1);
        if ((i == -1) || (j == -1)) {
            return 50.0D;
        }
        return i / j * 100.0D;
    }

//    public void SendGPS(double paramDouble)
//    {
//
//        Location localLocation = getLocation();
//        if (localLocation == null) {
//            return;
//        }
//
//        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date localDate = new Date();
//        DatabaseReference localDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        localDatabaseReference.child("UsersUpdates").child(UserUID).child(PhoneUID).child("Location").child("lat").setValue(Double.valueOf(localLocation.getLatitude()));
//        localDatabaseReference.child("UsersUpdates").child(UserUID).child(PhoneUID).child("Location").child("lag").setValue(Double.valueOf(localLocation.getLongitude()));
//        localDatabaseReference.child("UsersUpdates").child(UserUID).child(PhoneUID).child("Location").child("LastDateOnline").setValue(localSimpleDateFormat.format(localDate).toString());
//        localDatabaseReference.child("UsersUpdates").child(UserUID).child(PhoneUID).child("Location").child("Battery").setValue(Integer.valueOf((int)paramDouble));
//    }

    void UpdateCarts()
    {
        Object localObject = new ItemStatus();
      String  localObject1 = WebURL + "ShoppingWS.asmx/OrderList?UserUID=" + UserUID + "&StatusID=" + String.valueOf(((ItemStatus)localObject).StatusID.get(0));
        //new AsyTaskReloadTool().execute(new String[] { localObject1 });

    }

    void UpdateStatus()
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date localDate = new Date();
        this.mDatabase.child("UsersUpdates").child(UserUID).child("UpdateOrder").setValue(localSimpleDateFormat.format(localDate).toString());
    }

    void UserUpdates()
    {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date localDate = new Date();
        this.mDatabase.child("UsersUpdates").child(UserUID).child("PlaceNotify").setValue(localSimpleDateFormat.format(localDate).toString());
    }

//    public Location getLocation()
//    {
//
//        Object localObject1 = LocationService.location;
//        if (localObject1 == null)
//        {
//            localObject1 = (LocationManager)this.context.getSystemService("location");
//            if (localObject1 != null)
//            {
//                Object localObject2 = new Criteria();
//                ((Criteria)localObject2).setAccuracy(1);
//                ((Criteria)localObject2).setCostAllowed(false);
//                localObject2 = ((LocationManager)localObject1).getLastKnownLocation(((LocationManager)localObject1).getBestProvider((Criteria)localObject2, false));
//                if (localObject2 != null) {
//                    return (Location)localObject2;
//                }
//                return ((LocationManager)localObject1).getLastKnownLocation("passive");
//            }
//            return null;
//        }
//        return (Location)localObject1;
//    }
//
//
//
//
//    public class AsyTaskReloadTool
//            extends AsyncTask<String, String, String>
//    {
//        public AsyTaskReloadTool() {}
//
//        /* Error */
//        protected String doInBackground(String... paramVarArgs)
//        {
//            // Byte code:
//            //   0: new 29	java/net/URL
//            //   3: dup
//            //   4: aload_1
//            //   5: iconst_0
//            //   6: aaload
//            //   7: invokespecial 32	java/net/URL:<init>	(Ljava/lang/String;)V
//            //   10: invokevirtual 36	java/net/URL:openConnection	()Ljava/net/URLConnection;
//            //   13: checkcast 38	java/net/HttpURLConnection
//            //   16: astore_1
//            //   17: new 40	java/io/BufferedInputStream
//            //   20: dup
//            //   21: aload_1
//            //   22: invokevirtual 44	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
//            //   25: invokespecial 47	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
//            //   28: invokestatic 53	com/hussienalrubaye/familyfinder/Operations:ConvertInputToStringNoChange	(Ljava/io/InputStream;)Ljava/lang/String;
//            //   31: astore_2
//            //   32: aload_1
//            //   33: invokevirtual 56	java/net/HttpURLConnection:disconnect	()V
//            //   36: aload_0
//            //   37: iconst_1
//            //   38: anewarray 58	java/lang/String
//            //   41: dup
//            //   42: iconst_0
//            //   43: aload_2
//            //   44: aastore
//            //   45: invokevirtual 62	com/hussienalrubaye/familyfinder/GlobalClass$AsyTaskReloadTool:publishProgress	([Ljava/lang/Object;)V
//            //   48: goto +11 -> 59
//            //   51: astore_2
//            //   52: aload_1
//            //   53: invokevirtual 56	java/net/HttpURLConnection:disconnect	()V
//            //   56: aload_2
//            //   57: athrow
//            //   58: astore_1
//            //   59: aconst_null
//            //   60: areturn
//            // Local variable table:
//            //   start	length	slot	name	signature
//            //   0	61	0	this	AsyTaskReloadTool
//            //   0	61	1	paramVarArgs	String[]
//            //   31	13	2	str	String
//            //   51	6	2	localObject	Object
//            // Exception table:
//            //   from	to	target	type
//            //   17	32	51	finally
//            //   0	17	58	java/lang/Exception
//            //   32	48	58	java/lang/Exception
//            //   52	58	58	java/lang/Exception
//        }
//
//        protected void onPostExecute(String paramString) {}
//
//        protected void onPreExecute() {}
//
//        protected void onProgressUpdate(String... paramVarArgs)
//        {
//            try
//            {
//                paramVarArgs = new JSONObject(paramVarArgs[0]);
//                Object localObject = paramVarArgs.getString("ErrorID");
//                GlobalClass.familyCarts.clear();
//                if (((String)localObject).equals(ErrorNumbers.NoError))
//                {
//                    paramVarArgs = paramVarArgs.getJSONArray("UsersPhonesInfo");
//                    int i = 0;
//                    while (i < paramVarArgs.length())
//                    {
//                        localObject = paramVarArgs.getJSONObject(i);
//                        GlobalClass.familyCarts.add(new FamilyCart(((JSONObject)localObject).getString("RequestedPhoneUID"), ((JSONObject)localObject).getString("ItemUID"), ((JSONObject)localObject).getString("ItemName"), ((JSONObject)localObject).getString("RequestedDate"), ((JSONObject)localObject).getString("ShippedPhoneUID"), ((JSONObject)localObject).getString("ShippedDate"), ((JSONObject)localObject).getInt("StatusID"), ((JSONObject)localObject).getDouble("PlaceLatitude"), ((JSONObject)localObject).getDouble("Placelongitude"), ((JSONObject)localObject).getString("PlaceName")));
//                        i += 1;
//                    }
//                }
//                GlobalClass.familyCarts.add(0, new FamilyCart("No_new_data", null, null, null, null, null, 0, 0.0D, 0.0D, null));
//                return;
//            }
//            catch (Exception paramVarArgs) {}
//        }
//    }
}


