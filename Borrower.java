// A library borrower
public class Borrower 
{
	String ID, name;
	Record history = new Record();
	
	Borrower(String ID)
	{
		this.ID = ID;
	}
	
	Borrower(String ID, String name)
	{
		this(ID);
		this.name = name;
	}

}
