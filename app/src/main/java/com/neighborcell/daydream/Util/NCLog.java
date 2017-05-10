package com.neighborcell.daydream.Util;

import android.os.*;
import android.util.*;
import java.io.*;
import java.text.*;
import java.util.*;

public final class NCLog
{
  private static final String TAG = "DayDream";
  private static final String PATH_OUT = "/neighborcell/DayDream/";
  private static final String PATH_LOG = "app.log";
  
  private static final String crlf = System.getProperty("line.separator");
  
  public static void d()
  {
    Log.d(TAG,"");
    fileout(formatMessage());
  }
  
  public static void i(String msg)
  {
    Log.i(TAG, msg);
    fileout(formatMessage(msg));
  }

  public static void e(Throwable t)
  {
    Log.e(TAG, "", t);
    fileout(formatMessage(t.getMessage()));
    
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    try
    {
      t.printStackTrace(pw);
      pw.flush();
      pw.close();
      fileout( sw.getBuffer().toString());
      pw = null;
    }
    catch( Exception e )
    {
      
    }
    finally
    {
      if( null != pw )
      {
        try
        {
          pw.close();
        }
        catch( Exception ex )
        {

        }
      }
    }
    
    //printStackTrace(t);
  }

  private static void printStackTrace(Throwable th)
  {
    String filePath = Environment.getExternalStorageDirectory() + PATH_LOG;
    File file = new File(filePath);
    if (!file.getParentFile().exists())
    {
      file.getParentFile().mkdir();
    }

    FileOutputStream fos = null;
    try
    {
      fos = new FileOutputStream(file, true);
      OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
      PrintWriter pw = new PrintWriter(osw);
      th.printStackTrace(pw);
      pw.flush();
      pw.close();
    }
    catch (Exception e)
    {

    }
    finally
    {
      if( null != fos )
      {
        try
        {
          fos.close();
        }
        catch( Exception ex )
        {
          
        }
      }
    }
  }
  
  private static String formatMessage()
  {
    int rank = 4;

    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss.SSS");
    sdf.format(c.getTime());

    Thread th = Thread.currentThread();
    StackTraceElement st[] = th.getStackTrace();
    StringBuffer sb = new StringBuffer();

    if (st.length < rank-1)
    {
      return "";
    }

    sb.append(sdf.format(c.getTime()));
    sb.append("#");
    sb.append(st[rank].getFileName());
    sb.append("[");
    sb.append(st[rank].getLineNumber());
    sb.append("] @");
    sb.append(st[rank].getMethodName());
    sb.append(crlf);

    return sb.toString();
  }
  
  private static String formatMessage(String msg)
  {
    int rank = 4;
    
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss.SSS");
    sdf.format(c.getTime());

    Thread th = Thread.currentThread();
    StackTraceElement st[] = th.getStackTrace();
    StringBuffer sb = new StringBuffer();

    if (st.length < rank-1)
    {
      return "";
    }

    sb.append(sdf.format(c.getTime()));
    sb.append("#");
    sb.append(st[rank].getFileName());
    sb.append("[");
    sb.append(st[rank].getLineNumber());
    sb.append("]:");
    sb.append(msg);
    sb.append(crlf);

    return sb.toString();
  }
  
  private static void clear()
  {
    //clear( PATH_LOG );
  }

  public static void clear(String filename)
  {
    String filePath = Environment.getExternalStorageDirectory() + PATH_OUT + filename;
    File file = new File(filePath);
    if (!file.getParentFile().exists())
    {
      file.getParentFile().mkdir();
    }

    FileOutputStream fos = null;
    try
    {
      fos = new FileOutputStream(file, false);
      OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
      BufferedWriter bw = new BufferedWriter(osw);
      bw.write("");
      bw.flush();
      bw.close();
    }
    catch (Exception e)
    {

    }
    finally
    {
      if( null != fos )
      {
        try
        {
          fos.close();
        }
        catch( Exception ex )
        {

        }
      }
    }
  }
  
  private static void fileout(String msg)
  {
    //fileout( PATH_LOG, msg);
  }
  
  public static void fileout(String filename, String msg)
  {
    String filePath = Environment.getExternalStorageDirectory() + PATH_OUT + filename;
    File file = new File(filePath);
    if (!file.getParentFile().exists())
    {
      file.getParentFile().mkdir();
    }

    FileOutputStream fos = null;
    try
    {
      fos = new FileOutputStream(file, true);
      OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
      BufferedWriter bw = new BufferedWriter(osw);
      bw.write(msg);
      bw.flush();
      bw.close();
    }
    catch (Exception e)
    {

    }
    finally
    {
      if( null != fos )
      {
        try
        {
          fos.close();
        }
        catch( Exception ex )
        {

        }
      }
    }
  }
}

