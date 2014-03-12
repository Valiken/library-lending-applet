public class Music extends Item
{
	String artist, format;
	
	Music(String ID)
	{
		super(ID);
	}

	Music(String ID, String name, String medium, String artist, String desc, String format)
	{
		super(ID, name, desc, medium);
		this.artist = artist; 
		this.format = format;
	}

	public String toString()
	{
		String message = "";
		message += super.toString();
		message += "\nArtist: " + artist;
		message += "\nFormat: " + format;
		return message;
	}
}