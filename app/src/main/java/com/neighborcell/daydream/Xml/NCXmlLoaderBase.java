package com.neighborcell.daydream.Xml;

import android.content.*;
import com.neighborcell.daydream.Util.*;
import java.io.*;
import java.util.*;
import org.xmlpull.v1.*;

public abstract class NCXmlLoaderBase<NODE>
{
  private List<NODE> nodes = new ArrayList<NODE>();

  public List<NODE> getNodes()
  {
    return nodes;
  }
  
  protected void addNode(NODE node)
  {
    nodes.add(node);
  }

  //protected abstract int abGetXml();
  protected abstract void abStartTag(XmlPullParser xpp, String name);
  protected abstract void abEndTag(XmlPullParser xpp, String name);
  protected abstract void abTextTag(XmlPullParser xpp, String text);
  
  public void parse(String xml)
  {
    parse( new StringReader(xml) );
  }
  
  public void parse(Reader xml)
  {
    //XmlResourceParser xpp = null;
    XmlPullParserFactory factory = null;
    XmlPullParser xpp = null;
    
    try
    {
      factory = XmlPullParserFactory.newInstance();
      factory.setNamespaceAware(true);
      
      xpp = factory.newPullParser();
      xpp.setInput( xml );
      
      //int xml = getXml();
      int event;

      //xpp = res.getXml(xml);
      event = xpp.getEventType();
      while (event != XmlPullParser.END_DOCUMENT)
      {
        switch (event)
        {
          case XmlPullParser.START_DOCUMENT:
            break;

          case XmlPullParser.START_TAG:
            abStartTag(xpp, xpp.getName() );
            break;

          case XmlPullParser.END_TAG:
            abEndTag(xpp, xpp.getName() );
            break;
          
          case XmlPullParser.TEXT:
            String tmp = xpp.getText();
            if( null == tmp )
            {
              break;
            }
            tmp = tmp.trim();
            if ( tmp.length() > 0 )
            {
              abTextTag(xpp, tmp );
            }
            break;

          default:
            break;
        }
        event = xpp.next();
      }
    }
    catch (XmlPullParserException e)
    {
      NCLog.e(e);
    }
    catch (IOException e)
    {
      NCLog.e(e);
    }
    catch (Exception e)
    {
      NCLog.e(e);
    }
    //finally
    //{
    //  xpp.close();
    //}
  }
}
