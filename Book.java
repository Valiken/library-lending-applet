
// Represents a book in the lending system
public class Book extends Item 
{
	String author;
	
	Book(String ID)
	{
		super(ID);
	}
	
	Book (String ID, String name, String desc, String author, String medium)
	{
		super(ID, name, desc, medium);
		this.author = author;
	}
	
	public String toString()
	{
		String message = "";
		message += super.toString();
		message += "\nAuthor: " + author;
		return message;
	}
}
