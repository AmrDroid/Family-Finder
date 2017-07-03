package com.amrmustafa.familyfinder;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import org.json.JSONArray;
import org.json.JSONObject;

public class OrdersList
  extends AppCompatActivity {
  ArrayList<FamilyCart> familyCartsList = new ArrayList();
  ListView lsNews;
  MyCustomAdapter myadapter;

  void ShowAlert(String paramString) {
    Builder localBuilder = new Builder(this);
    localBuilder.setMessage(paramString).setCancelable(false).setNegativeButton(getResources().getString(R.string.Close), new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
      }
    });
    localBuilder.create().show();
  }

  void ShowDelivered() {
    this.familyCartsList.remove(this.familyCartsList.size() - 1);
    this.familyCartsList.add(new FamilyCart("loading_ticket", null, null, null, null, null, 0, 0.0D, 0.0D, null));
    this.myadapter.notifyDataSetChanged();
    Object localObject = new ItemStatus();
    String b = GlobalClass.WebURL + "ShoppingWS.asmx/OrderList?UserUID=" + GlobalClass.UserUID + "&StatusID=" + String.valueOf(((ItemStatus) localObject).StatusID.get(1));
    new AsyTaskReloadTool().execute(new String[]{b});
  }

  void SortByDistance() {
    Object localObject = new Operations(this).getLocation();
    ArrayList<FamilyCart> localArrayList = new ArrayList<FamilyCart>();

    localArrayList.addAll(this.familyCartsList);

    int i = 0;
    for (; ; ) {
      if (i < localArrayList.size()) {
      }
      try {
        if (((FamilyCart) localArrayList.get(i)).location.getLatitude() > 0.0D) {
          ((FamilyCart) localArrayList.get(i)).Distance = ((FamilyCart) localArrayList.get(i)).location.distanceTo((Location) localObject);
        }
        i += 1;
//        continue;
        Collections.sort(localArrayList, new Comparator() {
          @Override
          public int compare(Object o1, Object o2) {
            FamilyCart paramAnonymousFamilyCart1 = (FamilyCart) o1;
            FamilyCart paramAnonymousFamilyCart2 = (FamilyCart) o2;

            return (int) (paramAnonymousFamilyCart1.getDistance() - paramAnonymousFamilyCart2.getDistance());
          }
        });


        localObject = new ArrayList();
        this.familyCartsList.clear();
        i = 0;
        if (i < localArrayList.size()) {
          if (((FamilyCart) localArrayList.get(i)).location.getLatitude() > 0.0D) {
            this.familyCartsList.add(localArrayList.get(i));
          }
          for (; ; ) {
            i += 1;
            //    break;
            ((ArrayList) localObject).add(localArrayList.get(i));
          }
        }
        if (((ArrayList) localObject).size() > 0) {
          this.familyCartsList.addAll((Collection) localObject);
        }
        if ((this.familyCartsList.size() != 0) && (!((FamilyCart) this.familyCartsList.get(this.familyCartsList.size() - 1)).RequestedPhoneUID.equals("Show_delivered"))) {
          this.familyCartsList.add(new FamilyCart("Show_delivered", null, null, null, null, null, 0, 0.0D, 0.0D, null));
        }
        return;
      } catch (Exception localException) {
        for (; ; ) {
        }
      }
    }
  }


  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_orders_list);

    ((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener() {
      public void onClick(View paramAnonymousView) {
        Intent i = new Intent(OrdersList.this.getApplicationContext(), OrdersAdd.class);
        OrdersList.this.startActivity(i);
      }
    });
    this.lsNews = ((ListView) findViewById(R.id.LVNews));
  }

  public boolean onCreateOptionsMenu(Menu paramMenu) {
    getMenuInflater().inflate(R.menu.orders_menu, paramMenu);
    return true;
  }


  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    int i = paramMenuItem.getItemId();
    if (i == R.id.goback) {
      finish();
    }
    if (i == R.id.Add) {
      startActivity(new Intent(getApplicationContext(), OrdersAdd.class));
    }
    return super.onOptionsItemSelected(paramMenuItem);
  }


  protected void onResume() {
    super.onResume();
    this.familyCartsList.clear();
    this.familyCartsList.addAll(GlobalClass.familyCarts);
    SortByDistance();
    this.myadapter = new MyCustomAdapter(this.familyCartsList);
    this.lsNews.setAdapter(this.myadapter);
  }

  public class AsyTaskReloadTool
          extends AsyncTask<String, String, String> {
    public AsyTaskReloadTool() {
    }

    @Override
    protected String doInBackground(String... params) {
      // TODO Auto-generated method stub

      String result;
      try {
        //String query =new String( params[0].getBytes(), "UTF-8");
        URL url = new URL(params[0]);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
          result = Operations.ConvertInputToStringNoChange(in);
        } finally {
          urlConnection.disconnect();
        }

        publishProgress(result);

      } catch (Exception e) {
        // TODO Auto-generated catch block

      }
      return null;
    }

    protected void onPostExecute(String paramString) {
    }

    protected void onPreExecute() {
    }


    protected void onProgressUpdate(String... paramVarArgs) {
      try {
        JSONObject paramVarArgs1 = new JSONObject(paramVarArgs[0]);
        String localObject = paramVarArgs1.getString("ErrorID");
        OrdersList.this.familyCartsList.clear();


        if ((localObject).equals(ErrorNumbers.NoError)) {

          JSONArray jsonArray4 = paramVarArgs1.getJSONArray("UsersPhonesInfo");
          int i = 0;

          while (i < jsonArray4.length()) {
            JSONObject localObject2 = jsonArray4.getJSONObject(i);

            OrdersList.this.familyCartsList.add(new FamilyCart((localObject2).getString("RequestedPhoneUID"),
                    ((JSONObject) localObject2).getString("ItemUID"), ((JSONObject) localObject2).getString("ItemName"),
                    ((JSONObject) localObject2).getString("RequestedDate"),
                    ((JSONObject) localObject2).getString("ShippedPhoneUID"), ((JSONObject) localObject2).getString("ShippedDate"),
                    ((JSONObject) localObject2).getInt("StatusID"), ((JSONObject) localObject2).getDouble("PlaceLatitude"),
                    ((JSONObject) localObject2).getDouble("Placelongitude"),
                    ((JSONObject) localObject2).getString("PlaceName")));
            i += 1;
          }
        }
        OrdersList.this.familyCartsList.add(0, new FamilyCart("No_new_data", null, null, null, null, null, 0, 0.0D, 0.0D, null));
        OrdersList.this.myadapter.notifyDataSetChanged();
        return;
      } catch (Exception paramVarssArgs) {
      }
    }
  }


  public class AsyTaskShapping
          extends AsyncTask<String, String, String> {
    public AsyTaskShapping() {
    }

    /* Error */
    protected String doInBackground(String... paramVarArgs) {

      String result;
      try {
        //String query =new String( params[0].getBytes(), "UTF-8");
        URL url = new URL(paramVarArgs[0]);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
          InputStream in = new BufferedInputStream(urlConnection.getInputStream());
          result = Operations.ConvertInputToStringNoChange(in);
        } finally {
          urlConnection.disconnect();
        }

        publishProgress(result);

      } catch (Exception e) {
        // TODO Auto-generated catch block

      }
      return null;
    }


    protected void onPostExecute(String paramString) {
    }

    protected void onPreExecute() {
    }


    protected void onProgressUpdate(String... paramVarArgs) {


      try {
        if (new JSONObject(paramVarArgs[0]).getString("IsDeliver").equals("Y")) {
          new GlobalClass(OrdersList.this.getApplicationContext()).UpdateStatus();
          return;
        }
        OrdersList.this.ShowAlert(OrdersList.this.getApplicationContext().getResources().getString(R.string.cannotadd));
        OrdersList.this.familyCartsList.clear();
        OrdersList.this.familyCartsList.addAll(GlobalClass.familyCarts);
        OrdersList.this.SortByDistance();
        OrdersList.this.myadapter.notifyDataSetChanged();
        return;
      } catch (Exception paramVmmarArgs) {
      }
    }
  }


  private class MyCustomAdapter
          extends BaseAdapter {
    public ArrayList<FamilyCart> listnewsDataAdpater;

    public MyCustomAdapter(ArrayList<FamilyCart> listnewsDataAdpater) {
      this.listnewsDataAdpater = listnewsDataAdpater;
    }


    public int getCount() {
      return this.listnewsDataAdpater.size();
    }

    public String getItem(int paramInt) {
      return null;
    }

    public long getItemId(int paramInt) {
      return paramInt;
    }


    @Override
    public View getView(final int paramInt, final View paramView, final ViewGroup paramViewGroup) {
      LayoutInflater layoutInflater = OrdersList.this.getLayoutInflater();

      final FamilyCart paramViewGroup1 = (FamilyCart) this.listnewsDataAdpater.get(paramInt);


      if (paramViewGroup1.RequestedPhoneUID.equals("No_new_data")) {
        View paramVie = layoutInflater.inflate(R.layout.news_ticket_no_news, null);

        ((TextView) paramVie.findViewById(R.id.txtMessage)).setText(OrdersList.this.getResources().getString(R.string.NoOrders));
        return paramVie;
      }


      if (paramViewGroup1.RequestedPhoneUID.equals("Show_delivered")) {
        View paramVie = layoutInflater.inflate(R.layout.news_button, null);

        ((Button) paramVie.findViewById(R.id.buShowMore)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View paramAnonymousView) {
            OrdersList.this.ShowDelivered();
          }
        });
        return paramView;
      }


      if (paramViewGroup1.RequestedPhoneUID.equals("loading_ticket")) {


        return layoutInflater.inflate(R.layout.news_ticket_loading, null);
      }


      View localView = layoutInflater.inflate(R.layout.order_ticket, null);


      ((TextView) localView.findViewById(R.id.name)).setText(paramViewGroup1.ItemName);

      ((TextView) localView.findViewById(R.id.name_owner)).setText(paramViewGroup1.RequestedPhoneUID + OrdersList.this.getApplicationContext().getResources().getString(R.string.owner));
      ((TextView) localView.findViewById(R.id.owner_date)).setText(paramViewGroup1.RequestedDate);
      TextView localTextView1 = (TextView) localView.findViewById(R.id.name_deliver);


      if (paramViewGroup1.ShippedPhoneUID.length() == 0) {
        String paramVie = "";
        localTextView1.setText(paramVie);
        TextView localTextView2 = (TextView) localView.findViewById(R.id.txt_news_date);


        if (localTextView1.length() <= 0) {
          paramVie = "";
          localTextView2.setText(paramVie);


          if (paramViewGroup1.StatusID != 0) {

            paramVie = " (" + String.format("%.1f", new Object[]{Double.valueOf(paramViewGroup1.Distance / 1609.34D)}) + "m)";
            if (paramViewGroup1.PlaceName.length() != 0) {
              paramVie = "Place: " + paramViewGroup1.PlaceName + paramView;


              //((TextView)localView.findViewById(2131558582)).setText(paramVie);


              final ItemStatus itemStatus = new ItemStatus();
              ((TextView) localView.findViewById(R.id.pending)).setText((CharSequence) itemStatus.Status.get(paramViewGroup1.StatusID - 1));


              (localView.findViewById(R.id.iv_route)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                  ((FamilyCart) OrdersList.this.familyCartsList.get(paramInt)).ShippedPhoneUID = "me";
                  ((FamilyCart) OrdersList.this.familyCartsList.get(paramInt)).ShippedDate = "Now";
                  OrdersList.this.myadapter.notifyDataSetChanged();
                  String paramAnonymousVie = GlobalClass.WebURL2 + "ShoppingWS.asmx/Shipping?ShippedPhoneUID=" + GlobalClass.PhoneUID + "&ItemUID=" + paramViewGroup1.ItemUID;

                  new AsyTaskShapping().execute(new String[]{paramAnonymousVie});
                }
              });


              (localView.findViewById(R.id.aaa)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                  OrdersList.this.familyCartsList.remove(paramInt);
                  OrdersList.this.myadapter.notifyDataSetChanged();
                  String paramAnonymousVi = GlobalClass.WebURL2 + "ShoppingWS.asmx/UpdateItemStatus?ItemStatus=" + itemStatus.StatusID.get(2) + "&ItemUID=" + paramViewGroup1.ItemUID;
                  new AsyTaskShapping().execute(new String[]{paramAnonymousVi});
                }
              });


              if (paramViewGroup1.location.getLatitude() == 0.0D) {
                //  ((LinearLayout)localView.findViewById(R.id.LLRoute)).setVisibility(View.GONE);
              }
//              ((ImageView)localView.findViewById(2131558558)).setOnClickListener(new View.OnClickListener()
//              {
//                public void onClick(View paramAnonymousView)
//                {
//                  new Operations(OrdersList.this.getApplicationContext());
//                  paramAnonymousView = new Intent("android.intent.action.VIEW", Uri.parse("http://maps.google.com/maps?saddr=" + LocationService.location.getLatitude() + "," + LocationService.location.getLongitude() + "&daddr=" + paramViewGroup.location.getLatitude() + "," + paramViewGroup.location.getLongitude()));
//                  OrdersList.this.startActivity(paramAnonymousView);
//                }
//              });
//            }
                return localView;
              //  String paramVi = paramViewGroup1.ShippedPhoneUID + OrdersList.this.getApplicationContext().getResources().getString(2131099712);
                //break;


            }

          }

        }
      }
      return null;
    }
  }
}