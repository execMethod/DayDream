package com.neighborcell.daydream.Task;

import com.neighborcell.daydream.Util.*;
import android.os.*;

public class NCTaskCallbackExe<PARAM> implements Runnable
{
  private int status;
  private NCTaskCallback<PARAM> cb;
  private PARAM data;

  public NCTaskCallbackExe( NCTaskCallback<PARAM> cb)
  {
    this.cb = cb;
    this.status = NCTaskCallback.CALLBACK_STATUS_ERROR;
    this.data = null;
  }

  public void set( int status, PARAM data )
  {
    //NCLog.i("set status;" + status );
    this.status = status;
    this.data = data;
  }

  @Override
  public void run()
  {
    //NCLog.d();
    //NCLog.i("run status;" + status );
    cb.abCallback(status,data);
  }
  
  public void execute( Handler handler)
  {
    handler.post(this);
  }
}
