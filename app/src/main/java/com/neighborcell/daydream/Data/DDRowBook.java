package com.neighborcell.daydream.Data;
import android.content.*;
import android.graphics.*;
import com.neighborcell.daydream.*;
import java.text.*;
import java.util.*;
import android.graphics.drawable.*;
import java.io.*;
import com.neighborcell.daydream.Util.*;

public class DDRowBook extends DDRowInfo implements Serializable
{
  public static final int BOOK_STATUS_INIT = 0;
  public static final int BOOK_STATUS_STRT = 1;
  public static final int BOOK_STATUS_LOAD = 2;
  public static final int BOOK_STATUS_REDY = 3;
  public static final int BOOK_STATUS_ERRR = -1;
  
  private int status;
  private NCSerializedBitmap icon;
  private String url;
  private List<DDRowPage> pages;
  
  private String init;
  private String strt;
  private String load;
  private String errr;
  
  public DDRowBook(Context cntx, String url)
  {
    super();
    this.init = cntx.getString(R.string.book_init);
    this.strt = cntx.getString(R.string.book_strt);
    this.load = cntx.getString(R.string.book_load);
    this.errr = cntx.getString(R.string.book_errr);

    this.pages = new ArrayList<DDRowPage>();
    this.status = BOOK_STATUS_INIT;
    
    this.icon = new NCSerializedBitmap( 
      BitmapFactory.decodeResource(
        cntx.getResources(), R.drawable.rss_ora 
      )
    );
    this.url = url;
  }

  public void addPages(List<DDRowPage> pages)
  {
    this.pages.addAll(pages);
    setUpdate();
  }

  public void addPage(DDRowPage page)
  {
    // not null link
    if( null == page.getLink() )
    {
      NCLog.i("can't addpage for null link");
      return;
    }
    
    // not duplicate
    String link = page.getLink();
    for( DDRowPage have : pages )
    {
      if( link.equalsIgnoreCase( have.getLink() ) )
      {
        // duplicate
        return;
      }
    }
    
    // add
    pages.add(page);
    setUpdate();
  }

  public List<DDRowPage> getPages()
  {
    List<DDRowPage> pagesd = new ArrayList<DDRowPage>();
    for( DDRowPage page : pages )
    {
      if( ! page.isRead() )
      {
        pagesd.add( page );
      }
    }
    //return pages;
    return pagesd;
  }

  public void setIcon(Bitmap icon)
  {
    this.icon = new NCSerializedBitmap( icon );
  }

  public Bitmap getIcon()
  {
    return icon.decode();
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }

  public String getUrl()
  {
    return url;
  }
  
  public void setStatus( int status )
  {
    this.status = status;
  }
  public int getStatus()
  {
    return status;
  }

  public Integer getNum()
  {
    int cnt = 0;
    for( DDRowPage page : pages )
    {
      if( ! page.isRead() )
      {
        cnt++;
      }
    }
    return cnt;
    //return pages.size();
  }
  
  public String getUpdate()
  {
    String ret;
    
    switch(status)
    {
      case BOOK_STATUS_INIT:
        ret = init;
        break;
      case BOOK_STATUS_STRT:
        ret = strt;
        break;
      case BOOK_STATUS_LOAD:
        ret = load;
        break;
      case BOOK_STATUS_ERRR:
        ret = errr;
        break;
      default:
        ret = super.getUpdate();
        break;
    }
    
    return ret;
  }

  public boolean isInitialized()
  {
    return BOOK_STATUS_INIT != status;
  }
  
  public void copy( DDRowBook book )
  {
    status = book.status;
    icon = book.icon;
    url = book.url;
    pages = book.pages;
    init = book.init;
    load = book.load;
    super.copy((DDRowInfo)book);
  }
  
  public DDRowPage getPage( String uniq )
  {
    DDRowPage rpage = null;
    for( DDRowPage apage : pages )
    {
      if( apage.chkUniq(uniq) )
      {
        rpage = apage;
        break;
      }
    }
    return rpage;
  }
  
  public void deadlimit( Date now, int limit )
  {
    for( int i = 0; i < pages.size(); )
    {
      if( pages.get(i).deadlimit(now,limit) )
      {
        NCLog.i("dell page; " + pages.get(i).getUpdate() );
        pages.remove(i);
      }
      else
      {
        i++;
      }
    }
  }
}

