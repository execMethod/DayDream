package com.neighborcell.daydream.UI;

import android.app.*;
import android.content.*;
import android.os.*;
import com.neighborcell.daydream.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Util.*;

public class NCDialogYesNo extends DialogFragment
{
  private String msg = "";
  private String msgYes = null;
  private DialogInterface.OnClickListener lisYes = null;
  private String msgNo = null;
  private DialogInterface.OnClickListener lisNo = null;
  private String msgErr = "";
  
  public void zSetMsg(String msg)
  {
    this.msg = msg;
  }
  
  public void zSetListenerYes( String msgYes, DialogInterface.OnClickListener lisYes )
  {
    this.msgYes = msgYes;
    this.lisYes = lisYes;
  }
  
  public void zSetListenerNo( String msgNo, DialogInterface.OnClickListener lisNo )
  {
    this.msgNo = msgNo;
    this.lisNo = lisNo;
  }
  
  public void zSetErrMsg( String err )
  {
    this.msgErr = err;
  }

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    try
    {
      if(null != msg)
      {
        builder.setMessage(msg);
      }
      if(null != msgYes)
      {
        builder.setPositiveButton(msgYes,lisYes);
      }
      if(null != msgNo )
      {
        builder.setNegativeButton(msgNo,lisNo);
      }
    }
    catch (Exception e)
    {
      NCLog.e(e);
      if( null != msgErr )
      {
        builder.setMessage(msgErr);
      }
    }
    return builder.create();
  }
}
