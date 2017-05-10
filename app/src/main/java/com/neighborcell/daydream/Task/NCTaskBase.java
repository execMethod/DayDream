package com.neighborcell.daydream.Task;

import android.content.*;
import android.os.*;
import com.neighborcell.daydream.Util.*;
import java.util.concurrent.*;

public abstract class NCTaskBase<PARAM>
{
  private static final int PARA = 2;
  
  private NCTaskCallback<PARAM> callback = null;
  private ExecutorService exes = null;
  private Handler handler;
  
  public NCTaskBase()
  {
    exes = Executors.newFixedThreadPool(PARA);
    handler = new Handler();
  }
  
  public void setCallback( NCTaskCallback<PARAM> callback )
  {
    this.callback = callback;
  }
  
  protected void exeCallback(int status, PARAM param)
  {
    if(null == callback)
    {
      return;
    }
    NCTaskCallbackExe<PARAM> callbackExe = new NCTaskCallbackExe<PARAM>( callback );
    callbackExe.set(status,param);
    callbackExe.execute(handler);
  }
  
  public void execute()
  {
    exes.shutdown();
  }
  
  public void addShot( PARAM param )
  {
    exes.execute( new NCTaskThreadExe( param ) );
  }
  
  protected abstract PARAM abShot( PARAM param );
  
  public class NCTaskThreadExe implements Runnable
  {
    PARAM param;

    public NCTaskThreadExe(PARAM param)
    {
      this.param = param;
    }

    @Override
    public void run()
    {
      //NCLog.d();
      try
      {
        this.param = abShot(param);
      }
      catch( Exception e )
      {
        NCLog.e(e);
      }
      //exeCallback(NCTaskCallback.CALLBACK_STATUS_DONE, param);
      //NCLog.d();
    }
  }
}
