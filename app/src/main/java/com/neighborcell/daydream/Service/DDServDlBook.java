package com.neighborcell.daydream.Service;
import android.content.*;
import com.neighborcell.daydream.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Task.*;
import com.neighborcell.daydream.Util.*;

public class DDServDlBook extends NCServBase
{
  public static final String CLASS = "DDServDlBook";
  private long jobs = 0;
  private DDTaskDlBook task;

  @Override
  protected void onCreateSafe()
  {
    //task = new DDTaskDlBook(callback);
  }

  @Override
  protected Intent exec(Intent arg)
  {
    task = new DDTaskDlBook(callback);
    DDApplication app = (DDApplication) getApplication();
    DDRowPacking pack = app.getPacking();
    
    NCLog.d();
    for (DDRowBook book : pack.getBooks())
    {
      if (book.isInitialized())
      {
        continue;
      }
      task.addShot( book);
      jobs++;
    }
    //NCLog.d();
    task.execute();
    return null;
  }
  
  private NCTaskCallback<DDRowBook> callback = new NCTaskCallback<DDRowBook>()
  {
    @Override
    public void abCallback(int status, DDRowBook data)
    {
      //NCLog.d();
      //NCLog.i("status;" +status);
      //NCLog.i("jobs;" +jobs);
      DDApplication app = (DDApplication) getApplication();
      DDRowPacking pack = app.getPacking();
      pack.updBook(data);
      app.setPacking(pack);

      Intent out = new Intent(CLASS);
      sendBroadcast(out);

      if (CALLBACK_STATUS_NOTIFY != status)
      {
        jobs--;
        if (0 == jobs)
        {
          // all jobs done
          NCLog.i("task finished");
          pack.setFirst( false );
          NCFile.save( getBaseContext(), pack );
          stopSelf();
        }
      }
    }
  };

}
