import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class LibraryApplet extends JApplet
{
	public void init()
	{			
		String borrowers = getParameter("BorrowerFile");
		String items = getParameter("ItemFile");
		String copy = getParameter("Copy");
		String welcome = getParameter("Welcome");
		String name = getParameter("Creator");
		
		getContentPane().add(new LibraryGUI(copy, welcome, name,borrowers,items));
		setVisible(true);
	}
}