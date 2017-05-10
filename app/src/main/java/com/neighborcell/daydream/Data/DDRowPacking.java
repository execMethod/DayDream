package com.neighborcell.daydream.Data;
import java.util.*;
import java.io.*;
import com.neighborcell.daydream.Util.*;
import android.telecom.*;

public class DDRowPacking implements Serializable, NCFileInf
{
  private String FILE_PATH = "Packing.dat";
  private boolean isFirst = true;
  
  private List<DDRowBook> books = new ArrayList<DDRowBook>();
  
  private int limit = 7;

  public void setLimit(int limit)
  {
    this.limit = limit;
  }

  public int getLimit()
  {
    return limit;
  }
  
  public void setBooks(List<DDRowBook> books)
  {
    this.books = books;
  }

  public List<DDRowBook> getBooks()
  {
    return books;
  }
  
  public void addBook( DDRowBook book )
  {
    books.add( book );
  }
  
  public void delBook( DDRowBook book )
  {
    books.remove( book );
  }
  
  public void setFirst(boolean isFirst)
  {
    this.isFirst = isFirst;
  }
  
  public boolean getFirst()
  {
    return isFirst;
  }
  
  public String getPath()
  {
    return FILE_PATH;
  }
  
  public boolean isWorkable()
  {
    boolean status = true;
    
    // 読み込み中が存在している場合は実行不可
    for( DDRowBook book : books )
    {
      if( ( DDRowBook.BOOK_STATUS_INIT < book.getStatus() )
        && ( book.getStatus() < DDRowBook.BOOK_STATUS_REDY ) )
      {
        status = false;
        break;
      }
    }
    if( ! status )
    {
      return false;
    }
    
    // 初期状態のものが一件以上存在している必要がある
    status = false;
    for( DDRowBook book : books )
    {
      if( DDRowBook.BOOK_STATUS_INIT == book.getStatus() )
      {
        status = true;
        break;
      }
    }
    
    return status;
  }
  
  public void clearStatus( String bookUniqId )
  {
    for( DDRowBook book : books )
    {
      if( null == bookUniqId || book.chkUniq( bookUniqId ) )
      {
        book.setStatus(DDRowBook.BOOK_STATUS_INIT);
      }
    }
  }
  
  public void updBook(DDRowBook book)
  {
    for( DDRowBook abook : books )
    {
      if( abook.equal(book) )
      {
        abook.copy(book);
      }
    }
  }
  public void updPage(DDRowBook book, DDRowPage page)
  {
    for( DDRowBook abook : books )
    {
      if( abook.equal(book) )
      {
        for( DDRowPage apage : abook.getPages() )
        {
          if( apage.equal(page) )
          {
            apage.copy( page);
          }
        }
      }
    }
  }
  
  public DDRowBook getBook( String uniq )
  {
    DDRowBook rbook = null;
    for( DDRowBook abook : books )
    {
      if( abook.chkUniq(uniq) )
      {
        rbook = abook;
        break;
      }
    }
    return rbook;
  }
  
  public void deadlimit()
  {
    Date now = new Date();
    for( DDRowBook abook : books )
    {
      abook.deadlimit(now,limit);
    }
  }
}

