import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

public class SearchWindow
{
	private JFrame frame;
	private JPanel main, searchPanel, submitPanel;
	private JLabel search;
	private JTextField data;
	private JButton submit;
	private JComboBox<String> searchType;

	private Logger logger = Logger.getLogger(SearchWindow.class.getName());

	public SearchWindow()
	{
		frame = new JFrame("Database Search:");
		frame.setIconImages(IconLoader.getList());
		main = new JPanel();
		main.setLayout(new BorderLayout());
		frame.add(main);

		searchPanel = new JPanel();
		submitPanel = new JPanel();

		String[] array = {"Name", "GUID", "@ID"};
		searchType = new JComboBox<String>(array);
		JLabel type = new JLabel("Search Type:");
		submitPanel.add(type);
		submitPanel.add(searchType);


		search = new JLabel("Search for: ");
		searchPanel.add(search);

		data = new JTextField(32);
		searchPanel.add(data);

		submit = new JButton("Search");
		submitPanel.add(submit);

		main.add(searchPanel, BorderLayout.CENTER);
		main.add(submitPanel, BorderLayout.SOUTH);

		implementListeners();
		frame.setVisible(true);
		frame.pack();
	}

	public void implementListeners()
	{
		submit.addActionListener(new SearchListener());
		submit.addKeyListener(new SearchListener());
		data.addKeyListener(new SearchListener());
		searchType.addKeyListener(new SearchListener());
	}

	private class SearchListener extends KeyAdapter implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == submit)
			{
				new SearchResults(frame, data.getText(), (String) searchType.getSelectedItem());
			}
		}

		public void keyPressed(KeyEvent e)
		{
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				new SearchResults(frame, data.getText(), (String) searchType.getSelectedItem());
		}
	}
}