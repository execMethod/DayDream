package com.neighborcell.daydream.UI;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import com.neighborcell.daydream.Util.*;

public abstract class NCActivBase extends Activity
{
  public void zReport(String msg)
  {
    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent)
  {
    super.onActivityResult(requestCode, resultCode, intent);

    if (0 < resultCode - 1)
    {
      finish(resultCode - 1, getIntent());
    }
    return;
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    NCLog.d();
    try
    {
      xCreateSafe();
    }
    catch ( Exception e )
    {
      NCLog.e(e);
      zReport(e.getMessage());
    }
  }
  protected abstract void xCreateSafe();

  @Override
  protected void onResume()
  {
    super.onResume();
    NCLog.d();
    try
    {
      xResumeSafe();
    }
    catch ( Exception e )
    {
      NCLog.e(e);
      zReport(e.getMessage());
    }
  }
  protected abstract void xResumeSafe();

  @Override
  protected void onPause()
  {
    NCLog.d();
    try
    {
      xPauseSafe();
    }
    catch ( Exception e )
    {
      NCLog.e(e);
      zReport(e.getMessage());
    }
    super.onPause();
  }
  protected abstract void xPauseSafe();

  public void contentView()
  {
    setContentView(xLayoutId());
  }
  protected abstract int xLayoutId();
  
  public void finish(int resultCode, Intent intent)
  {
    setResult(resultCode, intent);
    super.finish();
  }
  
}
