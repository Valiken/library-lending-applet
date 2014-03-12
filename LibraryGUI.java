import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.text.*;
import java.util.ArrayList;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class LibraryGUI extends JPanel implements ActionListener, ListSelectionListener
{

	BorrowerCollection bc = BorrowerCollection.getInstance();
	LibraryCollection lc = LibraryCollection.getInstance();
	
	//Private variables and instances 
	private Controller ctrl = new Controller();
	private JTextArea viewArea;
	private JScrollPane scroll, listScroll;
	private JToolTip tip;
	private JButton catalogButton, checkOutButton, returnButton, historyButton, quitButton;
	private JTextField borrowerField, dateField;
	private JLabel borrowerLabel, dateLabel;
	private JList list;
	private DefaultListModel model;
	
	//variable to grab item ID from JList
	String listItem ="";
	
	LibraryGUI(String c, String w, String n, String bl, String il)
	{
		createButtonPanel();
		createDisplayPanel(c, w, n);
		createInputPanel();
		getBorrowerFile(bl);
		getItemFile(il);
	}
	
	void createButtonPanel() //creates panel with JButtons
	{	
		//establish layouts for the panel
		GridLayout grid = new GridLayout(0,1);
		JPanel ButtonPanel = new JPanel();
		ButtonPanel.setLayout(grid);
		grid.setVgap(5);
		ButtonPanel.setBorder(BorderFactory.createTitledBorder("Controls:"));
		
		tip = new JToolTip();
		tip.setBackground(Color.YELLOW);
      tip.setForeground(Color.RED);
		
		//create buttons
		catalogButton = new JButton("Catalog")
		{
		  public JToolTip createToolTip() 
		  {
      	  JToolTip tip = super.createToolTip();
      	  tip.setBackground(Color.BLUE);
      	  tip.setForeground(Color.YELLOW);
      	  return tip;
     	 }
  	   };
		
		checkOutButton = new JButton("Check Out");
		returnButton = new JButton("Return");
		historyButton = new JButton("History");
		quitButton = new JButton("Quit");
	
		catalogButton.setToolTipText("View the Catalog");
		checkOutButton.setToolTipText("Check out an item");
		returnButton.setToolTipText("Return an item");
		historyButton.setToolTipText("View borrower history");
		quitButton.setToolTipText("Quit the program");
		
		//enable buttons
		catalogButton.setEnabled(true);
		checkOutButton.setEnabled(true);
		returnButton.setEnabled(true);
		historyButton.setEnabled(true);
		quitButton.setEnabled(true);
		
		//add actionListeners to buttons
		catalogButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		returnButton.addActionListener(this);
		historyButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		//add buttons to panel
		ButtonPanel.add(catalogButton);
		ButtonPanel.add(checkOutButton);
		ButtonPanel.add(returnButton);
		ButtonPanel.add(historyButton);
		ButtonPanel.add(quitButton);
		
		//add panel to frame
		add(ButtonPanel, BorderLayout.WEST);	
	}
	
	void createDisplayPanel(String c,String w,String n) //creates panel with JList and JTextArea
	{
		//create panel
		JPanel DisplayPanel = new JPanel();
		
		//establish borders and layout
		DisplayPanel.setBorder(BorderFactory.createTitledBorder("Output:"));
		
		//add text and scroll area 
		viewArea = new JTextArea(15,45);
		viewArea.setWrapStyleWord(true);
		scroll = new JScrollPane(viewArea);
		model = new DefaultListModel();
		list = new JList(model);
		listScroll = new JScrollPane(list);
		list.addListSelectionListener(this);
		
		//add to panel
		DisplayPanel.add(scroll);
		DisplayPanel.add(listScroll);
		
		//add to frame
		add(DisplayPanel, BorderLayout.EAST);
		
		viewArea.append(w + "\n");
		viewArea.append(n + "\n");
		viewArea.append(c + "\n");
		
	}
	
	void createInputPanel() //creates input panel with JLabels and TextFields
	{
		//create panel and border
		JPanel InputPanel = new JPanel();
		InputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
		
		//date field and label 
		dateLabel = new JLabel("Date:");
		dateField = new JTextField(10);
		
		//borrower field and label
		borrowerLabel = new JLabel("Borrower:"); 
		borrowerField = new JTextField(10);
		
		//set into the panel 
		InputPanel.add(dateLabel);
		InputPanel.add(dateField);
		InputPanel.add(borrowerLabel);
		InputPanel.add(borrowerField);
		
		//add panel to the frame
		add(InputPanel, BorderLayout.SOUTH);
	}
	
	void getBorrowerFile(String bl)
	{
	// Borrowers registered with the library
		try
		{
			InputStream in = BorrowerCollection.class.getResourceAsStream(bl);
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buffer = new BufferedReader(reader);
		
			try
			{
				String input = buffer.readLine();
				while (input != null)
				{
					// Parse input into individual fields
					String[] fields = input.split(",");
					String id = fields[0];
					String name = fields[1];		
						// Create object and populate with data
						Borrower borrower = new Borrower(id, name);
						
						// Add object to collection
						bc.borrowerList.put(borrower.ID, borrower);
	
					
					// Read another record from the file
					input = buffer.readLine();
				}	
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(NullPointerException e)
		{
			viewArea.append("You have started the program without a specified borrower list file.\nPlease add one to the program folder and try again.\n\n");
		}
	}
	
	void getItemFile(String il)
	{
		try
		{
			// Open a file stream
			InputStream in = LibraryCollection.class.getResourceAsStream(il);
			InputStreamReader reader = new InputStreamReader(in);
			BufferedReader buffer = new BufferedReader(reader);
		
			try
			{
				String input = buffer.readLine();
				while (input != null)
				{
					String[] fields = input.split(",");
					String type = fields[0];
					
					if (type.equals("BOOK"))
					{
						String code = fields[1];
						String title = fields[2];
						String desc = fields[3];
						String author = fields[4];
						String medium = fields[5];
						int quantity = Integer.parseInt(fields[6]);
						
						// Create object and populate with data
						Book book = new Book(code,title,desc,author,medium);
						book.quantity = quantity;
						
						// Add object to collection
						lc.collection.put(book.ID, book);
					}
					else
					if(type.equals("MUSIC"))
					{
						String code = fields[1];
						String title = fields[2];
						String artist = fields[3];
						String desc = fields[4];
						String format = fields[5];
						String medium = fields[6];
						int quantity = Integer.parseInt(fields[7]);
						
						Music music = new Music(code,title,medium,artist,desc,format);
						music.quantity = quantity;
						lc.collection.put(music.ID, music);
					}
				
					// Read another record from the file
					input = buffer.readLine();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		catch(NullPointerException e)
		{
			viewArea.append("You have started the program without a specified item list file.\nPlease add one to the program folder and try again.\n\n");
		}
			
	}
	public void valueChanged(ListSelectionEvent event)
	{
		listItem = list.getSelectedValue().toString();
		String[] itemFields = listItem.split(":");
		JOptionPane.showMessageDialog(null, "You selected code: " + itemFields[0]);
	}

	public void actionPerformed(ActionEvent event)
	{
		Object source = event.getSource();
		
		if (source == catalogButton)
		{
			// Get catalog details -- add other component fields as necessary
			// to complete the required arguments
			ArrayList <String> myList = ctrl.getCatalog();
			for (String s : myList)
			{
				model.addElement(s);
			}
		}
		else
		if (source == checkOutButton) //CheckOut Function
		{
		
			try
			{
				
				//get input from textfields 
				String date = dateField.getText(); 
				String borrower = borrowerField.getText();
		
				listItem = list.getSelectedValue().toString();
				String[] itemFields = listItem.split(":"); //gets item name from JList
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				sdf.setLenient(false);

				boolean valid = false; // date error checking
				while(!valid)
				{		
			
					try
					{
					sdf.parse(date);
					valid = true;
					}
					catch(Exception e) 
					{
					date = JOptionPane.showInputDialog(null, "The date is incorrect.\nPlease enter a valid date.", "", JOptionPane.ERROR_MESSAGE);
						if(date == null)
						{
							valid = true;
						}
					}
				}
				if(date != null)
				{
					viewArea.append(ctrl.checkOut(date, borrower, itemFields[0])); //sends input to controller 
				}
				else
				if(date == null)
				{
					JOptionPane.showMessageDialog(null, "Cancelled Check Out.");
				}
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Please select an item from the catalog list.");
			}
		}
		else
		if (source == returnButton) //Return function
		{
			try
			{
				//get input from textfields 
				String date = dateField.getText();
		
				listItem = list.getSelectedValue().toString();
				String[] itemFields = listItem.split(":"); //gets item name from JList
						
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					sdf.setLenient(false);
			
				boolean valid = false; // date error checking
				while(!valid)
				{		
					try
					{
						sdf.parse(date);
						valid = true;
					}
					catch(Exception e) 
					{
						date = JOptionPane.showInputDialog(null, "The date is incorrect.\nPlease enter a valid date.", "", JOptionPane.ERROR_MESSAGE);
						if(date == null)
						{
							valid = true;
						}
					}
				}
				if(date != null)
				{
					viewArea.append(ctrl.returnItem(date, itemFields[0])); //sends input to controller 
				}
				else
				if(date == null)
				{
					JOptionPane.showMessageDialog(null, "Cancelled Return.");
				}
			}
			catch(NullPointerException e)
			{
				JOptionPane.showMessageDialog(null, "Please select an item from the catalog list.");
			}
		}
		else
		if (source == historyButton) //returns borrower history through two JOptionPanes
		{
			String borrowerID = borrowerField.getText();

			if (ctrl.checkBorrower(borrowerID)) //borrowerID check
			{
				Borrower borrower = ctrl.borrowersOnFile.getBorrower(borrowerID);
				viewArea.append("\n\nBorrower History for ID: " + borrowerID + "\nName: " + borrower.name);
				viewArea.append(ctrl.history(borrowerID));
			}
			else
			if(!ctrl.checkBorrower(borrowerID))
			{
				JOptionPane.showMessageDialog(null, "Sorry Invalid ID.");
			}
			
		}
		else
		if(source == quitButton) //clears the view area of text
		{
			viewArea.setText("Session Terminated.\nThank you for using the Libraby Lending System.");
			
		}

	}	
	
}