package dbtLab3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * The GUI pane where a new user logs in. Contains a text field where the user
 * id is entered and a button to log in.
 */
public class SearchingPane extends BasicPane {
	private static final long serialVersionUID = 1;
	/**
	 * The text field where the user id is entered.
	 */
	private JTextField[] fields;
	private JComboBox<String> combobox;
	private JScrollPane scrollPane;
	private JList<String> searchResultList;
	private DefaultListModel<String> searchListModel;

	/**
	 * The number of the field where the user id is entered.
	 */
	private static final int USER_ID = 0;

	/**
	 * The total number of fields in the fields array.
	 */
	private static final int NBR_FIELDS = 1;

	/**
	 * Create the login pane.
	 * 
	 * @param db
	 *            The database object.
	 */
	public SearchingPane(Database db) {
		super(db);
	}

	/**
	 * Create the top panel, consisting of the text field.
	 * 
	 * @return The top panel.
	 */
	public JComponent createTopPanel() {
		String[] texts = new String[NBR_FIELDS];
		texts[USER_ID] = "Search entry";
		fields = new JTextField[NBR_FIELDS];
		fields[USER_ID] = new JTextField(20);
		
		
		JPanel panel = new InputPanel(texts, fields);
		String[] comboOptions= new String[] {"Pallet ID", "Customer name", "Order ID", "Blocked","Date Search"};
		combobox = new JComboBox<>(comboOptions);
		
		panel.add(combobox);
		
		return panel;
	}
	public JComponent createMiddlePanel() {
		
		searchListModel = new DefaultListModel<>();
		searchResultList = new JList<>(searchListModel);
		searchResultList.setLayoutOrientation(JList.VERTICAL);
		searchResultList.setVisibleRowCount(-1);
		scrollPane = new JScrollPane(searchResultList);
		return scrollPane;
		
		
		
		
	}


	/** 
	 * Create the bottom panel, consisting of the login button and the message
	 * line.
	 * 
	 * @return The bottom panel.
	 */
	public JComponent createBottomPanel() {
		JButton searchButton = new JButton();
		searchButton = new JButton("Search");
		TextFieldListener actHand = new TextFieldListener();
		fields[USER_ID].addActionListener(actHand);
		JPanel testPanel = new JPanel();
		testPanel.add(searchButton);
		
		
		
		searchButton.addActionListener(actHand);
		return testPanel;
		
	}

	/**
	 * Perform the entry actions of this pane, i.e. clear the message line.
	 */
	public void entryActions() {
		clearMessage();
	}

	/**
	 * A class which listens for button clicks.
	 */
	class TextFieldListener implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the
		 * database if the user exists, and if so notifies the CurrentUser
		 * object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			String searchEntry = fields[USER_ID].getText();
			/* --- insert own code here --- */
			ArrayList <String> tmp = db.getSearchData((String)combobox.getSelectedItem(), fields[USER_ID].getText());
			searchListModel.removeAllElements();
			for( String s : tmp) {
				searchListModel.addElement(s);
			}
		
			
			
		}
	}
}
