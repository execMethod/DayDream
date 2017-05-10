package com.neighborcell.daydream.Data;
import java.io.*;
import java.util.*;

public class DDRowPage extends DDRowInfo implements Serializable
{
  //public static final int PAGE_STATUS_INIT = 0;
  //public static final int PAGE_STATUS_READ = 1;
  
  private boolean read;
  
  public DDRowPage()
  {
    super();
    readnot();
  }
  
  public boolean isRead()
  {
    return read;
  }
  public void read()
  {
    read = true;
  }
  public void readnot()
  {
    read = false;
  }
  
  public boolean deadlimit(Date now, int limit)
  {
    Date update = getUpdateDate();
    Calendar calupd = Calendar.getInstance();
    calupd.setTime(update);
    calupd.add(Calendar.DATE, limit);
    //calupd.add(Calendar.MINUTE, 1);
    
    Calendar calnow = Calendar.getInstance();
    calnow.setTime(now);
    
    if( calupd.compareTo( calnow ) < 0 )
    {
      return true;
    }
    return false;
  }
}

