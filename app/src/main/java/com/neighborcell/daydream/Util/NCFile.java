package com.neighborcell.daydream.Util;
import android.content.*;
import android.util.*;
import java.io.*;

public class NCFile
{
  public static void save(Context cont, NCFileInf obj)
  {
    save(cont, obj.getPath(), obj);
  }
  
  public static NCFileInf load(Context cont, NCFileInf obj)
  {
    NCFileInf loaded = load(cont,obj.getPath());
    if( null == loaded )
    {
      loaded = obj;
      save(cont,obj);
    }
    return loaded;
  }
  
  public static void delete(Context cont, String path)
  {
    try
    {
      cont.deleteFile(path);
    }
    catch (Exception e)
    {
      NCLog.e(e);
    }
  }
  
  public static void save(Context cont, String path, NCFileInf obj)
  {
    FileOutputStream fos = null;
    ObjectOutputStream oos = null;
    NCLog.i("save path : " + path);
    try
    {
      fos = cont.openFileOutput(path, Context.MODE_PRIVATE);
      oos = new ObjectOutputStream(fos);
      oos.writeObject(obj);
      oos.close();
    } 
    catch (Exception e)
    {
      NCLog.e(e);
    }
    finally
    {
      try
      {
        if( null != fos)
        {
          fos.close();
        }
      }
      catch( IOException ex)
      {
        // no code
      }
    }
  }
  
  public static NCFileInf load(Context cont, String path)
  {
    FileInputStream fis = null;
    ObjectInputStream ois = null;
    NCFileInf obj = null;
    try
    {
      fis = cont.openFileInput(path);
      ois = new ObjectInputStream(fis);
      obj = (NCFileInf) ois.readObject();
      ois.close();
    }
    catch (Exception e)
    {
      NCLog.e(e);
    }
    finally
    {
      try
      {
        if( null != fis )
        {
          fis.close();
        }
      }
      catch( IOException ex )
      {
        // no code
      }
    }
    return obj;
  }
}
