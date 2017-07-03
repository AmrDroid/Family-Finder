package com.amrmustafa.familyfinder;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ForgetPassword
  extends AppCompatActivity
{
  public static EditText Username;
  
  public void BuGetPassword(View paramView1)
  {
 String   paramView = GlobalClass.WebURL + "ForgetMyPassword?Email=" + Username.getText().toString();
    new AsyPassword().execute(new String[] { paramView });
  }
  
  void ShowAlert(String paramString)
  {
    //  Toast.makeText(this,"GGGGGGGGG",Toast.LENGTH_LONG).show();
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(paramString).setCancelable(false).setNegativeButton("canel", new OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        ForgetPassword.this.finish();
      }
    });
    localBuilder.create().show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_forget_password);
    Username = (EditText)findViewById(R.id.EDTUser);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
 //   getMenuInflater().inflate(R.menu.orders_menu, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 2131558600) {
      finish();
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }
  
  public class AsyPassword
    extends AsyncTask<String, String, String>
  {
    public AsyPassword() {}
    
    /* Error */
    protected String doInBackground(String... paramVarArgs)
    { String result;
      try {
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
    
    protected void onProgressUpdate(String... paramVarArgs1)
    {
      try
      {
        String paramVarArgs = new JSONObject(paramVarArgs1[0]).getString("IsDeliver");
        ForgetPassword.this.ShowAlert(paramVarArgs);
        return;
      }
      catch (Exception paramVarArgs) {}
    }
  }
}