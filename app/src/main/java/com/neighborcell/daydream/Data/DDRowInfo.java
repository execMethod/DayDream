package com.neighborcell.daydream.Data;

import android.content.*;
import com.neighborcell.daydream.*;
import java.io.*;
import java.text.*;
import java.util.*;

public class DDRowInfo extends NCRow implements Serializable
{
  private String title = null;
  private String link = null;
  private String desc = null;
  private String date = null;

  private Date update;
  private static String updateFmt = "";
  
  public static void preSetUpdateFormat(Context cntx)
  {
    DDRowInfo.updateFmt = cntx.getString(R.string.book_updateFmt);
  }
  
  public DDRowInfo()
  {
    super();
    setUpdate();
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getTitle()
  {
    return title;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public String getLink()
  {
    return link;
  }

  public void setDesc(String desc)
  {
    this.desc = desc;
  }

  public String getDesc()
  {
    return desc;
  }

  public void setDate(String date)
  {
    this.date = date;
  }

  public String getDate()
  {
    return date;
  }

  public void setUpdate()
  {
    this.update = new Date();
  }
  
  public String getUpdate()
  {
    DateFormat formatter = new SimpleDateFormat(updateFmt);
    return formatter.format(update);
  }
  
  public Date getUpdateDate()
  {
    return update;
  }
  
  public void copy( DDRowInfo info )
  {
    title = info.title;
    link = info.link;
    desc = info.desc;
    date = info.date;
    super.copy( (NCRow) info );
  }
}

