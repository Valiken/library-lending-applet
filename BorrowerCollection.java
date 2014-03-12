import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// A collection of library items
public class BorrowerCollection 
{
	HashMap <String, Borrower> borrowerList = new HashMap <String, Borrower>();
	
	private static final BorrowerCollection instance = new BorrowerCollection();
	
   public static BorrowerCollection getInstance() 
   {
   	return instance;
   }
	
	BorrowerCollection()
	{}
	
	// Return an item from the collection
	Borrower getBorrower(String ID)
	{
		return borrowerList.get(ID);
	}

}
