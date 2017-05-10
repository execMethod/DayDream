package com.neighborcell.daydream.Data;

import android.graphics.*;
import android.view.*;
import android.widget.*;
import com.neighborcell.daydream.*;

public class DDHolderInfo implements NCHolder
{
  private ImageView icon = null;
  private TextView title = null;
  private TextView desc = null;
  private TextView update = null;
  private TextView num = null;
  private DDRowInfo info = null;

  public void setRow(NCRow row)
  {
    this.info = (DDRowInfo) row;

    if (null != info.getTitle())
    {
      title.setText(info.getTitle());
      title.setVisibility(View.VISIBLE);
    }
    else
    {
      title.setVisibility(View.GONE);
    }

    if (null != info.getUpdate())
    {
      update.setText(info.getUpdate());
      update.setVisibility(View.VISIBLE);
    }
    else
    {
      update.setVisibility(View.GONE);
    }

    if (info instanceof DDRowBook)
    {
      DDRowBook book = (DDRowBook) info;
      if (null != book.getIcon())
      {
        icon.setImageBitmap(book.getIcon());
        icon.setVisibility(View.VISIBLE);
      }
      else
      {
        icon.setVisibility(View.GONE);
      }

      if (null != book.getNum())
      {
        num.setText(String.valueOf(book.getNum()));
        num.setVisibility(View.VISIBLE);
      }
      else
      {
        num.setVisibility(View.GONE);
      }
    }
    else
    {
      icon.setVisibility(View.GONE);
      num.setVisibility(View.GONE);
    }
    
    if( info instanceof DDRowPage )
    {
      DDRowPage page = (DDRowPage) info;
      
      if (null != page.getDesc())
      {
        desc.setText( page.getDesc() );
        desc.setVisibility(View.VISIBLE);
      }
      else
      {
        desc.setVisibility(View.GONE);
      }
      if( page.isRead() )
      {
        title.setTextColor(Color.GRAY);
        desc.setTextColor(Color.GRAY);
        update.setTextColor(Color.GRAY);
      }
      else
      {
        title.setTextColor(Color.BLACK);
        desc.setTextColor(Color.BLACK);
        update.setTextColor(Color.BLACK);
      }
    }
    else
    {
      desc.setVisibility(View.GONE);
    }
  }

  public NCRow getRow()
  {
    return info;
  }

  public void findView(View view)
  {
    icon = (ImageView) view.findViewById(R.id.rowmain_icon);
    title = (TextView) view.findViewById(R.id.rowmain_title);
    desc = (TextView) view.findViewById(R.id.rowmain_desc);
    update = (TextView) view.findViewById(R.id.rowmain_update);
    num = (TextView) view.findViewById(R.id.rowmain_num);
  }
}

