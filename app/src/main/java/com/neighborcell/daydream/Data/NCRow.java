package com.neighborcell.daydream.Data;
import java.util.*;

public class NCRow
{
  private static long uniqCnt = 0;
  private String uniq;
  
  public NCRow()
  {
    uniq = creUniq();
  }
  
  private String creUniq()
  {
    Date date = new Date();
    String ret = String.format("%d_%d", date.getTime(), uniqCnt );
    if( Long.MAX_VALUE == uniqCnt )
    {
      uniqCnt = 0;
    }
    else
    {
      uniqCnt++;
    }
    return ret;
  }
  
  public String getUniq()
  {
    return uniq;
  }
  
  public boolean chkUniq( String uniq )
  {
    return 0 == this.uniq.compareTo( uniq );
  }

  public boolean equal( NCRow row )
  {
    return chkUniq( row.uniq );
  }
  
  public void copy( NCRow row )
  {
    uniq = row.uniq;
  }
}
