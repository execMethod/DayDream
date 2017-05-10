package com.neighborcell.daydream.Task;

public interface NCTaskCallback<DATA>
{
  public static final int CALLBACK_STATUS_ERROR = -1;
  public static final int CALLBACK_STATUS_NOTIFY = 0;
  public static final int CALLBACK_STATUS_DONE = 1;
  //public static final int CALLBACK_STATUS_FINISH = 2;
  
  public void abCallback( int status, DATA data );
}

