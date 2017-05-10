package com.neighborcell.daydream.UI;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AbsListView.*;
import android.widget.AdapterView.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Util.*;
import java.util.*;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public abstract class NCActivListBase extends ListActivity
{
  public void zReport(String msg)
  {
    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
    NCLog.i(msg);
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
    setListAdapter(new ListViewAdapter(this, xRowLayoutId(), xRows()));

    ListView listView = getListView();
    listView.setOnItemClickListener(new ItemClickListener());
    listView.setOnItemLongClickListener(new ItemLongTouchListener());
    listView.setOnScrollListener(new ItemScrollListener() );
  }

  public void finish(int resultCode, Intent intent)
  {
    setResult(resultCode, intent);
    super.finish();
  }

  protected abstract int xLayoutId();

  protected abstract int xRowLayoutId();

  protected abstract List<NCRow> xRows();

  protected void zReload()
  {
    try
    {
      NCLog.d();
      ListViewAdapter adpt = (ListViewAdapter)getListAdapter();
      adpt.notifyDataSetChanged();
    }
    catch (Exception e)
    {
      NCLog.e(e);
    }
  }

  private class ListViewAdapter extends ArrayAdapter<NCRow>
  {
    private LayoutInflater inflater;
    private int layout;
    NCRow row;

    public ListViewAdapter(Context context, int layout,  List<NCRow> rows)
    {
      super(context, 0, rows);
      this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
      try
      {
        DDHolderInfo holder;
        if (convertView == null)
        {
          convertView = inflater.inflate(layout, parent, false);
          holder = new DDHolderInfo();
          holder.findView(convertView);
          convertView.setTag(holder);
        }
        else
        {
          holder = (DDHolderInfo) convertView.getTag();
        }

        row = getItem(position);
        holder.setRow(row);
      }
      catch (Exception e)
      {
        NCLog.e(e);
        zReport(e.getMessage());
      }
      return convertView;
    }
  }

  private class ItemClickListener implements OnItemClickListener
  {
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
      DDHolderInfo holder = (DDHolderInfo)view.getTag();
      try
      {
        xRowClick(holder.getRow());
      }
      catch ( Exception e )
      {
        NCLog.e(e);
        zReport(e.getMessage());
      }
    }
  }
  protected abstract void xRowClick(NCRow row);
  
  private class ItemLongTouchListener implements OnItemLongClickListener
  {
    @Override
    public boolean onItemLongClick(AdapterView<?> p1, View view, int p3, long p4)
    {
      DDHolderInfo holder = (DDHolderInfo)view.getTag();
      try
      {
        xRowLongClick(holder.getRow());
      }
      catch ( Exception e )
      {
        NCLog.e(e);
        zReport(e.getMessage());
      }
      return true;
    }
  }
  protected abstract void xRowLongClick(NCRow row);
  
  private class ItemScrollListener implements OnScrollListener
  {
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
      int position = view.getFirstVisiblePosition();
      //zReport("onscrollChanged state;"+scrollState +" pos;"+position);
      xRowScroll(scrollState,position);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
    {
    }
  }
  protected abstract void xRowScroll(int state, int pos );
  
}

