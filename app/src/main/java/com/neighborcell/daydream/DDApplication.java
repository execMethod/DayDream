package com.neighborcell.daydream;
import android.app.*;
import com.neighborcell.daydream.Data.*;
import com.neighborcell.daydream.Util.*;

public class DDApplication extends Application
{
  private DDRowPacking pack = new DDRowPacking();

  public void onCreate()
  {
    super.onCreate();

    DDRowInfo.preSetUpdateFormat(getBaseContext());

    NCLog.i("========== APP START ==========");
    pack = (DDRowPacking) NCFile.load(getBaseContext(), pack);

    if (pack.getFirst())
    {
      firstboot();
    }
    //pack.setLimit(1);

    pack.deadlimit();
  }

  private void firstboot()
  {
    DDRowBook book;
    
    book = new DDRowBook(
      getBaseContext(),
      "http://feeds.lifehacker.jp/rss/lifehacker/index.xml"
    );
    pack.addBook(book);
    
    book = new DDRowBook(
      getBaseContext(),
      "http://www.atmarkit.co.jp/rss/rss.xml"
    );
    pack.addBook(book);
    
    book = new DDRowBook(
      getBaseContext(),
      "http://japan.cnet.com/rss/index.rdf"
    );
    pack.addBook(book);

    book = new DDRowBook(
      getBaseContext(),
      "http://www3.asahi.com/rss/index.rdf"
    );
    pack.addBook(book);

    book = new DDRowBook(
      getBaseContext(),
      "http://www.oreilly.co.jp/catalog/soon.xml"
    );
    pack.addBook(book);
    
    book = new DDRowBook(
      getBaseContext(),
      "http://dd.hokkaido-np.co.jp/rss/index.rdf"
    );
    pack.addBook(book);
    
    pack.setLimit(7);
  }

  public DDRowPacking getPacking()
  {
    return pack;
  }

  public void setPacking(DDRowPacking pack)
  {
    this.pack = pack;
  }
}

