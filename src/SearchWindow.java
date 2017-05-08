import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class SearchWindow
{
	private JFrame frame;
	private JPanel searchPanel;
	private JLabel search;
	private JTextField data;
	private JButton submit;

	private Logger logger = Logger.getLogger(SearchWindow.class.getName());

	public SearchWindow()
	{
		frame = new JFrame("Database Search:");
		searchPanel = new JPanel();

		search = new JLabel("Search for: ");
		searchPanel.add(search);

		data = new JTextField(32);
		searchPanel.add(data);

		submit = new JButton("Search");
		searchPanel.add(submit);


		implementListeners();
		frame.add(searchPanel);
		frame.setVisible(true);
		frame.pack();
	}

	public void implementListeners()
	{
		submit.addActionListener(new SearchListener());
	}

	private class SearchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == submit)
			{
				new PlayerDetails(data.getText());
			}
		}
	}
}