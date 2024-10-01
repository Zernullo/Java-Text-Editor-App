package JavaProject2;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class TextEditor extends JFrame implements ActionListener{

	JTextArea textArea;
	JScrollPane scroll;
	JSpinner fontSizeSpinner;//Spinner is the tiny arrow that allows the changes to happen
	JLabel label;
	JButton button;
	JComboBox<?> box;
	
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TextEditor() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Text Editor");
		this.setSize(500, 500);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);//Set frame to the center of the screen
		
		
		//--------textArea----------------------
		textArea = new JTextArea();
			//No longer need this because scroll.setPreferredSize//textArea.setPreferredSize(new Dimension(450,450));
		textArea.setLineWrap(true);//allocate the text to the next line if run out of space at the first line
		textArea.setWrapStyleWord(true);//What this do is make the word go the next line if the space is full
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		//---------------------------------------------------
		
		
		
		
		
		//------Scroll Bar---------------------------------------------------------------
		scroll = new JScrollPane(textArea);//Parameters:view the component to display in the scrollpane's viewport
		scroll.setPreferredSize(new Dimension(450,450));//Basically set the textArea size on the frame
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//Creates vertical scroll;
		//-------------------------------------------------------------------------------
		
		
		
		
		
		label = new JLabel("Font Size: ");
		
		
		
		
		
		//-----------JSpinner---------------------------------------------------------------------------------------------------------
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
		fontSizeSpinner.setValue(20);//initial value is 20
		
		fontSizeSpinner.addChangeListener(
				//Allow Changes to happen on the Spinner
				new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						// TODO Auto-generated method stub
						textArea.setFont( new Font( textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue() ) );
					}
			
				}
		);
		//----------------------------------------------------------------------------------------------------------------------------
		
		
		
		
		
		
		
		//---Creating a button for font color-----
		button = new JButton("Font Color: ");
		button.addActionListener(this);
		//----------------------------------------

		
		
		
		
		//------ComboBox--------------------------------------------------------------------------------
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//Get all the font available in java and assign the to the array
		box = new JComboBox(fonts);
		box.addActionListener(this);
		box.setSelectedItem("Arial");//initial font value
		//------ComboBox End--------------------------------------------------------------------------------

		
		
		
		
		//------Menu----------------
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);

		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		//--------------------------
		this.setJMenuBar(menuBar);
		this.add(label);
		this.add(fontSizeSpinner);
		this.add(button);
		this.add(box);
		this.add(scroll);//Since textArea is inputed into the scrollPane, you can just add the variable scroll to the frame
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		//Set the color---------------------------------
		if(e.getSource() == button) {
			JColorChooser colorChooser = new JColorChooser();
			
			@SuppressWarnings("static-access")
			Color color = colorChooser.showDialog(null, "Choose a color", Color.black);
			
			textArea.setForeground(color);
		}
		//----------------------------------------------
		
		
		
		
		//Set the font style--------------------------------------
		if(e.getSource() == box) {
			textArea.setFont( new Font( (String)box.getSelectedItem(), Font.PLAIN, textArea.getFont().getSize() ) );
		}
		//--------------------------------------------------------
		
		
		
		
		
		//Menu actions---------------------------------
		if(e.getSource() == openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			fileChooser.setFileFilter(filter);//Sets the File Filter used to filter out files of type
			
			int response = fileChooser.showOpenDialog(null);//Allow open to be shown on the file window
			
			if(response == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());//Basically once click on the file, you will have the file path
				Scanner fileIn = null;
				
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {//basically reads the file line by line
							String line = fileIn.nextLine() + "\n";
							textArea.append(line);
						}
					}
				} 
				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
			
		}
		if(e.getSource() == saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));//set your file into your project folder you just need to write a .
			
			int response = fileChooser.showSaveDialog(null);//allow save option to be available on the file window
			
			if(response == JFileChooser.APPROVE_OPTION) {//if save option is available, once the written textArea is saved, it creates a new file
				File file;
				PrintWriter fileOut = null;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				} 
				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
		}
		if(e.getSource() == exitItem) {
			System.exit(0);
		}
		//---------------------------------------------
		
		
		
		
		
		
		
		
	}

}
