package com.amrmustafa.familyfinder;

import android.location.Location;

public class FamilyCart extends Object
{
  public double Distance;
  public String ItemName;
  public String ItemUID;
  public String PlaceName;
  public String RequestedDate;
  public String RequestedPhoneUID;
  public String ShippedDate;
  public String ShippedPhoneUID;
  public int StatusID;
  public Location location;
  
  public FamilyCart(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, double paramDouble1, double paramDouble2, String paramString7)
  {
    this.RequestedPhoneUID = paramString1;
    this.ItemUID = paramString2;
    this.ItemName = paramString3;
    this.RequestedDate = paramString4;
    this.ShippedPhoneUID = paramString5;
    this.ShippedDate = paramString6;
    this.StatusID = paramInt;
    this.RequestedDate = paramString4;
    this.ShippedPhoneUID = paramString5;
    this.StatusID = paramInt;
    this.PlaceName = paramString7;
    this.location = new Location(paramString3);
    this.location.setLongitude(paramDouble2);
    this.location.setLatitude(paramDouble1);
    this.Distance = 0.0D;
  }
  
  public double getDistance()
  {
    return this.Distance;
  }
}
