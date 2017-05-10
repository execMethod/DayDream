package com.neighborcell.daydream.UI;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.neighborcell.daydream.Util.*;
import java.io.*;

public class NCWebViewCapture extends WebView
{
  int count = 0;
  int display_h = 0;
  Display display;
  Point p = new Point();
  Bitmap bitmap;
  String path;
  //Activity act;

  public void getWebCapture(Activity act, String url, String filepath)
  {
    path = Environment.getExternalStorageDirectory().toString() + filepath;

    display = act.getWindowManager().getDefaultDisplay();
    display.getSize(p);
    display_h = p.y;

    loadUrl(url);
  }

  public NCWebViewCapture(Context context)
  {
    super(context);
    NCLog.d();

    getSettings().setJavaScriptEnabled(false);
    setWebViewClient(
      new WebViewClient()
      {
        public void onPageFinished(WebView view, String url)
        {
          if ((count == 0) && (view.getContentHeight() == 0))
          {
            Toast.makeText(view.getContext(), "onPageFinished", Toast.LENGTH_SHORT).show();
            NCLog.d();
            count++;
            view.reload();
          }
        }
      }
    );

    setWebChromeClient( 
      new WebChromeClient() 
      {
        @Override
        public void onProgressChanged(WebView view, int progress)
        {
          NCLog.i("progress;" + progress);
          Toast.makeText(view.getContext(), "Progress;" + progress, Toast.LENGTH_SHORT).show();
          if (progress == 100)
          {
            NCLog.d();
            if ((count == 0) && (view.getContentHeight() < display_h))
            {
              count++;
              view.reload();
            }
            else
            {
              bitmap = Bitmap.createBitmap(view.getWidth(), view.getWidth(), Bitmap.Config.ARGB_8888);            
              Canvas c = new Canvas(bitmap);
              view.draw(c);
            }
          }
        }
      }
    );
  }
  
  @Override
  protected void onDraw(Canvas canvas)
  {
    super.onDraw(canvas);
    try
    {
      if (getContentHeight() != 0 && null != bitmap)
      {
        Toast.makeText(getContext(), "onDraw", Toast.LENGTH_SHORT).show();
        NCLog.d();
        FileOutputStream fos = null;
        fos = new FileOutputStream(path);
        if (fos != null)
        {
          bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
          fos.close();
        }
      }       
    }
    catch (Exception e)
    {
      NCLog.e(e);
      //e.printStackTrace();
    }
  }

  /*
   WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
   WindowManager.LayoutParams params = new WindowManager.LayoutParams(
   WindowManager.LayoutParams.MATCH_PARENT,
   WindowManager.LayoutParams.MATCH_PARENT,
   WindowManager.LayoutParams.TYPE_APPLICATION,
   WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
   PixelFormat.TRANSLUCENT);
   wm.addView(web, params);
   // */
  //web.loadUrl(url);

}
