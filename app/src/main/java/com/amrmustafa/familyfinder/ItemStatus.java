package com.amrmustafa.familyfinder;

import java.util.ArrayList;

public class ItemStatus
{
  public ArrayList<String> Status = new ArrayList();
  public ArrayList<Integer> StatusID = new ArrayList();
  
  public ItemStatus()
  {
    this.Status.add("Pending");
    this.Status.add("Shipped");
    this.Status.add("Canceled");
    this.Status.add("Deleted");
    this.StatusID.add(Integer.valueOf(1));
    this.StatusID.add(Integer.valueOf(2));
    this.StatusID.add(Integer.valueOf(3));
    this.StatusID.add(Integer.valueOf(4));
  }
}


/* Location:              C:\apktool\dex2jar-2.0\FamilyIn_1.5-dex2jar.jar!\com\hussienalrubaye\familyfinder\ItemStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */