package dbtLab3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * The GUI pane where a new user logs in. Contains a text field where the user
 * id is entered and a button to log in.
 */
public class BlockingPane extends BasicPane {
	private static final long serialVersionUID = 1;
	/**
	 * The text field where the user id is entered.
	 */
	private JTextField[] fields;
	private JComboBox<String> combobox;
	private JScrollPane scrollPane;
	private JList<String> searchResultList;
	private DefaultListModel<String> searchListModel;
	private JLabel palletIdDisplay;

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
	public BlockingPane(Database db) {
		super(db);
	}

	/**
	 * Create the top panel, consisting of the text field.
	 * 
	 * @return The top panel.
	 */
	public JComponent createTopPanel() {
		String[] texts = new String[3];
		texts[0] = "Start Date (yyyy-mm-dd)";
		texts[1] = "End Date (yyyy-mm-dd)";
		texts[2] = "Product Name";
		fields = new JTextField[3];
		fields[0] = new JTextField(20);
		fields[1] = new JTextField(20);
		fields[2] = new JTextField(20);

		JPanel panel = new InputPanel(texts, fields);

		return panel;
	}

	public JComponent createMiddlePanel() {

		JPanel pan = new JPanel();
		palletIdDisplay = new JLabel();
		pan.add(palletIdDisplay);
		return pan;

	}

	/**
	 * Create the bottom panel, consisting of the login button and the message line.
	 * 
	 * @return The bottom panel.
	 */
	public JComponent createBottomPanel() {
		JButton createButton = new JButton();
		createButton = new JButton("Block Pallets");
		ButtonListener actHand = new ButtonListener();
		fields[USER_ID].addActionListener(actHand);
		JPanel testPanel = new JPanel();
		testPanel.add(createButton);

		createButton.addActionListener(actHand);
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
	class ButtonListener implements ActionListener {
		/**
		 * Called when the user clicks the login button. Checks with the database if the
		 * user exists, and if so notifies the CurrentUser object.
		 * 
		 * @param e
		 *            The event object (not used).
		 */
		public void actionPerformed(ActionEvent e) {
			String s = "Pallets blocked (Låtsas vi tills det är implementerat)";

			s += "\n" + "StartDate : " + fields[0].getText();
			s += "\n" + "EndDate: " + fields[1].getText();
			s += "\n" + "ProductName: " + fields[2].getText();
			db.blockPallets(fields[0].getText(), fields[1].getText(), fields[2].getText());
			palletIdDisplay.setText(s);

		}
	}
}
