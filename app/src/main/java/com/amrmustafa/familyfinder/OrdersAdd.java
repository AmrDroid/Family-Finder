package com.amrmustafa.familyfinder;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlacePicker.IntentBuilder;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.JSONObject;

public class OrdersAdd
  extends AppCompatActivity
{
  public EditText EDOrderName;
  public EditText EDPReferLocation;
  int PLACE_PICKER_REQUEST = 1;
  double PlaceLatitude = 0.0D;
  double Placelongitude = 0.0D;

  public void BuAddCart(View paramView)
  {
    if (this.EDOrderName.getText().toString().length() < 3)
    {
      ShowAlert(getResources().getString(R.string.require_feild));
      return;
    }

    String    oreder;
    String   loc;
//    for (paramView = "N";; paramView = this.EDPReferLocation.getText().toString())
      if (this.EDPReferLocation.getText().toString().length() != 0) {}
    {
      String EDOrderName = this.EDOrderName.getText().toString();
      try
      {
        oreder = URLEncoder.encode(EDOrderName, "UTF-8");
        loc = URLEncoder.encode(this.EDPReferLocation.getText().toString(), "UTF-8");
      }
      catch (UnsupportedEncodingException paramView11)
      {
        for (;;)
        {
          loc = "N";

        }
      }
String      paramVie = GlobalClass.WebURL2 + "ShoppingWS.asmx/Add2Cart?RequestedPhoneUID=" + GlobalClass.PhoneUID + "&ItemName=" + oreder + "&PlaceName=" + loc + "&PlaceLatitude=" + String.valueOf(this.PlaceLatitude) + "&Placelongitude=" + String.valueOf(this.Placelongitude);
      new AsyTaskAdd2Cart().execute(new String[] { paramVie });
      return;
    }
  }




  void ShowAlert(final String paramString)
  {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(paramString).setCancelable(false).setNegativeButton("Close", new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramString.equals(OrdersAdd.this.getApplicationContext().getResources().getString(R.string.added_order))) {
          OrdersAdd.this.finish();
        }
      }
    });
    localBuilder.create().show();
  }



  public void buGetLocation(View paramView)
  {
    try
    {
      startActivityForResult(new IntentBuilder().build(this), this.PLACE_PICKER_REQUEST);
      return;
    }
    catch (Exception paramView11) {}
  }


  @Override
  protected void onActivityResult (int requestCode, int resultCode, Intent data) {

    if ((requestCode == this.PLACE_PICKER_REQUEST) && (resultCode == -1))
    {
     Place paramIntent =  PlacePicker.getPlace( data,this);

      Toast.makeText(this, String.format(getResources().getString(R.string.place_get), new Object[] { paramIntent.getName() }), Toast.LENGTH_LONG).show();
      this.EDPReferLocation.setText(paramIntent.getName());
      this.PlaceLatitude = paramIntent.getLatLng().latitude;
      this.Placelongitude = paramIntent.getLatLng().longitude;
    }
  }


  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_orders_add);
    this.EDOrderName = ((EditText)findViewById(R.id.EDOrderName));
    this.EDPReferLocation = ((EditText)findViewById(R.id.EDPReferLocation));
    this.EDPReferLocation.setOnFocusChangeListener(new OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        OrdersAdd.this.buGetLocation(null);
      }
    });
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(R.menu.map_menu, paramMenu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131558600) {
      finish();
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }






  public class AsyTaskAdd2Cart
    extends AsyncTask<String, String, String>
  {
    public AsyTaskAdd2Cart() {}

    /* Error */
    protected String doInBackground(String... paramVarArgs)
    {

      String result;
      try {
        //String query =new String( params[0].getBytes(), "UTF-8");
        URL url = new URL(paramVarArgs[0]);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
          result = Operations.ConvertInputToStringNoChange(in);
        }finally {
          urlConnection.disconnect();
        }

        publishProgress(result);

      } catch (Exception e) {
        // TODO Auto-generated catch block

      }
      return null;
    }


    protected void onPostExecute(String paramString) {}

    protected void onPreExecute() {}

    protected void onProgressUpdate(String... paramVarArgs)
    {
      try
      {
        if (new JSONObject(paramVarArgs[0]).getString("IsDeliver").equals("Y"))
        {
          new GlobalClass(OrdersAdd.this.getApplicationContext()).UpdateStatus();
          OrdersAdd.this.ShowAlert(OrdersAdd.this.getApplicationContext().getResources().getString(R.string.added_order));
          return;
        }
        OrdersAdd.this.ShowAlert(OrdersAdd.this.getApplicationContext().getResources().getString(R.string.canno_add_order));
        return;
      }
      catch (Exception paramVarArs) {}
    }
  }
}