package com.neighborcell.daydream.Task;

import android.content.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Util.*;
import com.neighborcell.daydream.Xml.*;
import java.io.*;
import java.net.*;

public class DDTaskDlBook extends NCTaskBase<DDRowBook>
{
  //private static final String crlf = System.getProperty("line.separator");
  
  public DDTaskDlBook( NCTaskCallback<DDRowBook> callback )
  {
    super();
    //NCLog.d();
    setCallback( callback );
  }
  
  @Override
  protected DDRowBook abShot(DDRowBook book)
  {
    boolean isSuccess = true;
    
    URL urlObj = null;
    HttpURLConnection urlCon = null;
    BufferedReader urlIn = null;
    String str;
    StringBuffer strbuf = new StringBuffer();

    //NCLog.d();
    NCLog.i("run ; " + book.getUrl() );
    //NCLog.clear(dbgf);

    book.setStatus(DDRowBook.BOOK_STATUS_STRT);
    exeCallback(NCTaskCallback.CALLBACK_STATUS_NOTIFY,book);

    try
    {
      urlObj = new URL(book.getUrl());
      urlCon = (HttpURLConnection)urlObj.openConnection();
      urlCon.setRequestMethod("GET");
      urlIn = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
      
      book.setStatus(DDRowBook.BOOK_STATUS_LOAD);
      
      DDXmlLoaderPage loader = new DDXmlLoaderPage();
      book = loader.parse( urlIn, book);
      
      urlIn.close();
      urlIn = null;
      urlCon.disconnect();
      urlCon = null;
      
//      for( DDRowPage page : book.getPages() )
//      {
//        String dbgf = String.format("rssfile%s.txt", page.getUniq());
//        
//        urlObj = new URL(page.getLink());
//        urlCon = (HttpURLConnection)urlObj.openConnection();
//        urlCon.setRequestMethod("GET");
//        urlIn = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
//        
//        while ((str = urlIn.readLine()) != null)
//        {
//          strbuf.append(str + crlf);
//        }
//        str = strbuf.toString().replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>","");
//        
//        NCLog.fileout(dbgf,str);
//      }
      
    }
    catch (Exception e) 
    {
      NCLog.e(e);
      isSuccess = false;
    }
    finally
    {
      try
      {
        if (null != urlIn)
        {
          urlIn.close();
        }
        if (null != urlCon)
        {
          urlCon.disconnect();
        }
      }
      catch (Exception e)
      {
        NCLog.e(e);
      }
    }
    
    if( isSuccess )
    {
      book.setStatus(DDRowBook.BOOK_STATUS_REDY);
      exeCallback(NCTaskCallback.CALLBACK_STATUS_DONE, book);
    }
    else
    {
      book.setStatus(DDRowBook.BOOK_STATUS_ERRR);
      exeCallback(NCTaskCallback.CALLBACK_STATUS_ERROR, book);
    }
    //NCLog.d();
    
    return book;
  }

}
