package com.neighborcell.daydream.UI;
import android.app.*;
import android.content.*;
import android.net.*;
import android.view.*;
import android.widget.AbsListView.*;
import com.neighborcell.daydream.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Service.*;
import com.neighborcell.daydream.Task.*;
import com.neighborcell.daydream.Util.*;
import java.util.*;

public class DDActivMain extends NCActivListBase
{
  private Uri argUri = null;

  public static String ARG_BOOKUNIQID = "ARG_BOOKUNIQID";
  private String argBookUniqId = null;

  private List<NCRow> rows = new ArrayList<NCRow>();
  private BroadcastReceiver receiver = new DDRcvDlBook();
  private IntentFilter intentFilter = new IntentFilter();

  protected int xLayoutId()
  {
    return R.layout.activ_main;
  }

  protected int xRowLayoutId()
  {
    return R.layout.row_main;
  }

  public void xCreateSafe()
  {
    contentView();

    argUri = getIntent().getData();
    argBookUniqId = getIntent().getStringExtra(ARG_BOOKUNIQID);

    DDApplication app = (DDApplication)getApplication();
    DDRowPacking pack = app.getPacking();

    if (null == argBookUniqId)
    {
      rows.addAll(pack.getBooks());
    }
    else
    {
      DDRowBook book = pack.getBook(argBookUniqId);
      rows.addAll(book.getPages());
      setTitle(book.getTitle());
    }
    if (null != argUri)
    {
      NCLog.i("add book");
      NCLog.i("getAuthority;" + argUri.getAuthority());
      NCLog.i("getEncodedAuthority;" + argUri.getEncodedAuthority());
      NCLog.i("getEncodedFragment;" + argUri.getEncodedFragment());
      NCLog.i("getEncodedPath;" + argUri.getEncodedPath());
      NCLog.i("getEncodedQuery;" + argUri.getEncodedQuery());
      NCLog.i("getEncodedSchemeSpecificPart;" + argUri.getEncodedSchemeSpecificPart());
      NCLog.i("getEncodedUserInfo;" + argUri.getEncodedUserInfo());
      NCLog.i("getFragment;" + argUri.getFragment());
      NCLog.i("getHost;" + argUri.getHost());
      NCLog.i("getLastPathSegment;" + argUri.getLastPathSegment());
      NCLog.i("getPath;" + argUri.getPath());
      NCLog.i("getPathSegments;" + argUri.getPathSegments());
      NCLog.i("getPort;" + argUri.getPort());
      NCLog.i("getQuery;" + argUri.getQuery());
      NCLog.i("getScheme;" + argUri.getScheme());
      NCLog.i("getSchemeSpecificPart;" + argUri.getSchemeSpecificPart());
      NCLog.i("getUserInfo;" + argUri.getUserInfo());
      NCLog.i("toString;" + argUri.toString());

      DDTaskDlBook task = new DDTaskDlBook(callback);
      DDRowBook book = new DDRowBook(getBaseContext(), argUri.toString());
      task.addShot(book);
      task.execute();
    }

    intentFilter.addAction(DDServDlBook.CLASS);
  }

  @Override
  public void xResumeSafe()
  {
    registerReceiver(receiver, intentFilter);

    DDApplication app = (DDApplication)getApplication();
    DDRowPacking pack = app.getPacking();

    //if( ! pack.getBookInitialize() && ! isServiceRunning(DDServDlBook.class))
    if (pack.isWorkable())
    {
      Intent arg = new Intent(getBaseContext(), DDServDlBook.class);
      startService(arg);
    }

    zReload();
  }

  @Override
  public void xPauseSafe()
  {
    unregisterReceiver(receiver);
  }

  @Override
  protected List<NCRow> xRows()
  {
    return rows;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {
    boolean ret = false;
    DDApplication app;
    DDRowPacking pack;

    switch (item.getItemId())
    {
      case R.id.menumain_spin:
        registerReceiver(receiver, intentFilter);

        app = (DDApplication)getApplication();
        pack = app.getPacking();

        pack.clearStatus(argBookUniqId);
        //NCLog.i("getBookInitialize; " + pack.getBookInitialize() );
        //NCLog.i("isServiceRunning ; " + isServiceRunning(DDServDlBook.class) );
        //NCLog.i("isWorkable ; " + pack.isWorkable());

        //if( ! pack.getBookInitialize() && ! isServiceRunning(DDServDlBook.class))
        if (pack.isWorkable())
        {
          Intent arg = new Intent(getBaseContext(), DDServDlBook.class);
          startService(arg);
          
          String msg = getString(R.string.tst_load);
          zReport(msg);
        }

        ret = true;
        break;
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
  
  protected void xRowClick(NCRow row)
  {
    if (row instanceof DDRowBook)
    {
      //DDRowBook book = (DDRowBook) row;
      //book.getUniq();

      Intent intent = new Intent(getBaseContext(), DDActivMain.class);
      intent.putExtra(ARG_BOOKUNIQID, row.getUniq());
      startActivityForResult(intent, 0);
    }
    else if (row instanceof DDRowPage)
    {
      Intent intent = new Intent(getBaseContext(), DDActivWeb.class);
      intent.putExtra(DDActivWeb.ARG_BOOKUNIQID,argBookUniqId);
      intent.putExtra(DDActivWeb.ARG_PAGEUNIQID, row.getUniq());
      startActivityForResult(intent, 0);
    }
  }

  protected void xRowLongClick(NCRow row)
  {
    if (row instanceof DDRowBook)
    {
      DDRowBook book = (DDRowBook)row;
      NCDialogYesNo dig = new NCDialogYesNo();
      String msg = getResources().getString(R.string.dig_msg_delete, book.getTitle());
      dig.zSetMsg(msg);
      msg = getResources().getString(R.string.dig_btn_delete);
      dig.zSetListenerYes(msg, new DDDialogBookDelListener(book));
      msg = getResources().getString(R.string.dig_btn_cancel);
      dig.zSetListenerNo(msg, null);
      dig.show(getFragmentManager(), "test");
    }
    else if (row instanceof DDRowPage)
    {
      //catpureWeb((DDRowPage)row);
      //Toast.makeText(this, "capture web", Toast.LENGTH_SHORT).show();

    }
  }

  @Override
  protected void xRowScroll(int state, int pos)
  {
    if(
      ( null != argBookUniqId )
    &&( OnScrollListener.SCROLL_STATE_IDLE == state ) 
    &&( 0 < pos )
    )
    {
      int cnt = 0;
      for( NCRow row : rows )
      {
        if( row instanceof DDRowPage )
        {
          DDRowPage page = (DDRowPage)row;
          //zReport("read ; "+page.getTitle());
          page.read();
        }
        cnt++;
        if( cnt >= pos )
        {
          break;
        }
      }
      DDApplication app = (DDApplication)getApplication();
      DDRowPacking pack = app.getPacking();
      NCFile.save(getBaseContext(), pack);
    }
  }
  
  private class DDRcvDlBook extends BroadcastReceiver
  {
    @Override
    public void onReceive(Context context, Intent intent)
    {
      //NCLog.d();

      DDApplication app = (DDApplication)getApplication();
      DDRowPacking pack = app.getPacking();

      rows.clear();
      if (null == argBookUniqId)
      {
        rows.addAll(pack.getBooks());
      }
      else
      {
        DDRowBook book = pack.getBook(argBookUniqId);
        rows.addAll(book.getPages());
      }
      zReload();
      String msg = getString(R.string.tst_fin);
      zReport(msg);
    }
  }

  private boolean isServiceRunning(Class srvcls)
  {
    ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
    List<ActivityManager.RunningServiceInfo> listServiceInfo = am.getRunningServices(Integer.MAX_VALUE);
    boolean found = false; 
    for (ActivityManager.RunningServiceInfo curr : listServiceInfo)
    {
      if (curr.service.getClassName().equals(srvcls.getName()))
      {
        found = true;
        break;
      }
    }
    return found;
  }

  private NCTaskCallback<DDRowBook> callback = new NCTaskCallback<DDRowBook>()
  {
    @Override
    public void abCallback(int status, DDRowBook book)
    {
      try
      {
        boolean isErr = false;
        boolean isVisible = false;
        NCDialogYesNo dig = new NCDialogYesNo();

        if (CALLBACK_STATUS_DONE == status)
        {
          isVisible = true;
          if (null == book)
          {
            NCLog.d();
            isErr = true;
          }
          else if (DDRowBook.BOOK_STATUS_ERRR == book.getStatus())
          {
            NCLog.d();
            isErr = true;
          }
          else if (0 >= book.getNum())
          {
            NCLog.d();
            isErr = true;
          }
        }
        else if (CALLBACK_STATUS_ERROR == status)
        {
          isVisible = true;
          isErr = true;
        }

        if (isVisible)
        {
          if (isErr)
          {
            String msg = getResources().getString(R.string.dig_msg_register_err);
            dig.zSetMsg(msg);
            dig.zSetListenerYes(msg, new DDDialogFinishListener());
          }
          else
          {
            String msg = getResources().getString(R.string.dig_msg_register, book.getTitle());
            dig.zSetMsg(msg);
            msg = getResources().getString(R.string.dig_btn_register);
            dig.zSetListenerYes(msg, new DDDialogBookAddListener(book));
            msg = getResources().getString(R.string.dig_btn_cancel);
            dig.zSetListenerNo(msg, new DDDialogFinishListener() );
          }
          dig.show(getFragmentManager(), "test");
        }
      }
      catch (Exception e)
      {
        NCLog.e(e);
      }
    }
  };

  private class DDDialogBookAddListener implements DialogInterface.OnClickListener
  {
    private DDRowBook book = null;

    public DDDialogBookAddListener(DDRowBook book)
    {
      this.book = book;
    }

    public void onClick(DialogInterface dialog, int id)
    {
      DDApplication app = (DDApplication)getApplication();
      DDRowPacking pack = app.getPacking();
      pack.addBook(book);
      NCFile.save(getBaseContext(), pack);
      
      rows.clear();
      if (null == argBookUniqId)
      {
        rows.addAll(pack.getBooks());
        zReload();
      }
      finish();
    }
  }

  private class DDDialogFinishListener implements DialogInterface.OnClickListener
  {
    public DDDialogFinishListener()
    {
    }

    public void onClick(DialogInterface dialog, int id)
    {
      finish();
    }
  }
  
  private class DDDialogBookDelListener implements DialogInterface.OnClickListener
  {
    private DDRowBook book = null;

    public DDDialogBookDelListener(DDRowBook book)
    {
      this.book = book;
    }

    public void onClick(DialogInterface dialog, int id)
    {
      DDApplication app = (DDApplication)getApplication();
      DDRowPacking pack = app.getPacking();
      pack.delBook(book);
      NCFile.save(getBaseContext(), pack);
      rows.clear();
      rows.addAll(pack.getBooks());
      zReload();
    }
  }
}

