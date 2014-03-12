import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LibraryCollection
{
	public HashMap<String, Item> collection = new HashMap <String, Item>();
		
	private static final LibraryCollection instance = new LibraryCollection();
	
   public static LibraryCollection getInstance() 
   {
   	return instance;
   }
		
	LibraryCollection()
	{}
	
	// Return an item from the collection
	Item getItem(String ID)
	{
		return collection.get(ID);
	}

}