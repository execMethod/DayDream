package com.neighborcell.daydream.UI;

import android.graphics.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import com.neighborcell.daydream.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Util.*;
import java.io.*;

public class DDActivWeb extends NCActivBase
{
  public static String ARG_BOOKUNIQID = "ARG_BOOKUNIQID";
  private String argBookUniqId = null;
  DDRowBook argBook = null;

  public static String ARG_PAGEUNIQID = "ARG_PAGEUNIQID";
  private String argPageUniqId = null;
  DDRowPage argPage = null;
  
  @Override
  protected int xLayoutId()
  {
    return R.layout.activ_web;
  }

  @Override
  protected void xCreateSafe()
  {
    contentView();
    argBookUniqId = getIntent().getStringExtra(ARG_BOOKUNIQID);
    argPageUniqId = getIntent().getStringExtra(ARG_PAGEUNIQID);

    DDApplication app = (DDApplication)getApplication();
    DDRowPacking pack = app.getPacking();
    
    if (null == argBookUniqId || null == argPageUniqId )
    {
      finish();
    }
    else
    {
      argBook = pack.getBook(argBookUniqId);
      argPage = argBook.getPage(argPageUniqId);
      setTitle(argPage.getTitle());
    }
    argPage.read();
    pack.updPage(argBook,argPage);
    
    WebView wv = (WebView)findViewById(R.id.web);
    wv.loadUrl(argPage.getLink());
  }

  @Override
  protected void xResumeSafe()
  {
  }

  @Override
  protected void xPauseSafe()
  {
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_web, menu);
    return super.onCreateOptionsMenu(menu);
  }
  
  public boolean onOptionsItemSelected(MenuItem item)
  {
    boolean ret = false;

    switch (item.getItemId())
    {
//      case R.id.menuweb_plus:
//        zReport("push menuweb_plus");
//
//        ret = true;
//        break;
        //case R.id.menumain_cog:
        //  Intent intent = new Intent(getBaseContext(), DDActivStore.class);
        //  //intent.putExtra(INTENT_ARG_ARMS, selectArms);
        //  startActivityForResult(intent, 0);
        //  ret = true;
        //  break;
      default:
        ret = super.onOptionsItemSelected(item);
        break;
    }
    return ret;
  }
}

