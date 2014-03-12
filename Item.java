import java.util.HashMap;

public abstract class Item 
{
	String ID, name, desc, medium;
	String borrower = "";
	int quantity;
	//HashMap <String,String> borrowers = new HashMap <String,String>();

	
	Item (String ID)
	{
		this.ID = ID;
	}
	
	Item (String ID, String name, String desc, String medium)
	{
		this(ID);
		this.name = name;
		this.desc = desc;
		this.medium = medium;
	}
	
	public String toString()
	{
		return ("ID: " + ID + "\n" + "Name: " + name + "\n" + "Medium: " + medium + "\n" + "Genre: " + desc + "\n" + "Quantity: [" + quantity + "]");
	}
}
