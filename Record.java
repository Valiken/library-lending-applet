import javax.swing.JOptionPane;
public class Record
{ 
	String history = "";
	public static final String CHECKOUT = "Check Out";
	public static final String RETURNS = "Return"; 
	public void addRecordInfo(String date, String Item, String transactions)
	{
		history += "\nTransation Date: " + date + "\nType: " + transactions + "\nItem: " + Item + "\n";
	}
	
	public void printRecord(String borrowerID)
	{
		JOptionPane.showMessageDialog(null, history);
	}

}