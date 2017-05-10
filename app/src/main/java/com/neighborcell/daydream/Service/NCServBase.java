package com.neighborcell.daydream.Service;

import android.app.*;
import android.content.*;
import android.os.*;
import com.neighborcell.daydream.Util.*;

public abstract class NCServBase extends Service
{
  @Override
  public void onCreate()
  {
    NCLog.d();
    super.onCreate();
    
    try
    {
      onCreateSafe();
    }
    catch ( Exception e )
    {
      NCLog.e(e);
    }
  }
  
  protected abstract void onCreateSafe();

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) 
  {
    //NCLog.d();
    
    try
    {
      Intent out = exec(intent);
      if( null != out )
      {
        sendBroadcast(out);
      }
    }
    catch (Exception e)
    {
      NCLog.e(e);
    }
    return START_STICKY;
  }
  
  protected abstract Intent exec(Intent arg);

  @Override
  public IBinder onBind(Intent intent)
  {
    return null;
  }

  @Override
  public void onDestroy()
  {
    //NCLog.d();
    super.onDestroy();
  }
}
