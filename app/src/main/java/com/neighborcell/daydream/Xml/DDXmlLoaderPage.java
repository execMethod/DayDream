package com.neighborcell.daydream.Xml;

import com.neighborcell.daydream.Data.*;
import java.io.*;
import org.xmlpull.v1.*;

public class DDXmlLoaderPage extends NCXmlLoaderBase<DDRowPage>
{
  private static int MAX_DESC_LENGTH = 128;
  
  private DDRowBook book = null;
  private DDRowPage page = null;
  private String name = null;
  
  public DDRowBook parse(Reader xml, DDRowBook book)
  {
    this.book = book;
    this.page = null;
    super.parse(xml);
    return book;
  }
  
  @Override
  protected void abStartTag(XmlPullParser xpp, String name)
  {
    if ("item".equals(name))
    {
      page = new DDRowPage();
    }
    this.name = name;
  }

  @Override
  protected void abTextTag(XmlPullParser xpp, String text)
  {
    DDRowInfo put;
    
    if( null != page )
    {
      put = page;
    }
    else
    {
      put = book;
      //NCLog.i( "book " + name + " : " + text );
    }
    
    if("title".equals(name))
    {
      put.setTitle( text );
    }
    else if("link".equals(name))
    {
      put.setLink( text );
    }
    else if("description".equals(name))
    {
      text = text.replaceAll("<(\"[^\"]*\"|'[^']*'|[^'\">])*>","");
      if( MAX_DESC_LENGTH < text.length() )
      {
        text = text.substring( 0, MAX_DESC_LENGTH ) + "...";
      }
      put.setDesc( text );
    }
    else if("pubDate".equals(name))
    {
      put.setDate( text );
    }
    //NCLog.i( name + " : " + text );
  }

  @Override
  protected void abEndTag(XmlPullParser xpp, String name)
  {
    if("item".equals(name) )
    {
      if( null != page )
      {
        book.addPage( page );
        page = null;
      }
    }
    
    if( this.name == name )
    {
      this.name = null;
    }
  }
}
