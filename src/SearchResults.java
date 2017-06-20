/**
 * Display SearchWindow search results
 */
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchResults
{

    private JFrame frame, root;
    private JTable table;
    private JPanel resultsPanel, main, buttonPanel;
    private SearchResultTableModel dtm;
    private JButton submit, cancel;

    /**
     * @param root calling JFrame
     * @param search parameter containing search query to ping server with
     * @param searchType parameter designating the type of search to be done ex.
     * @ID, GUID, NAME
     */
    public SearchResults(JFrame root, String search, String searchType)
    {
        if (search.length() < 2 && !searchType.toLowerCase().equals("@id"))
        {
            JOptionPane.showMessageDialog(null, "You must enter more than 1 character in your search",
                    "Search Data Too Short", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.root = root;
        frame = new JFrame("Search Results: \"" + search + "\"");
        frame.setIconImages(IconLoader.getList());
        main = new JPanel();
        frame.add(main);
        main.setLayout(new BorderLayout());

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridLayout());
        resultsPanel.setMinimumSize(new Dimension(920, 300));
        main.add(resultsPanel, BorderLayout.CENTER);

        setupTable();
        search(search, searchType);
        setupButtons();

        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results - " + dtm.getRowCount() + " total"));

        frame.setMinimumSize(new Dimension(920, 400));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void setupButtons()
    {
        buttonPanel = new JPanel();
        main.add(buttonPanel, BorderLayout.SOUTH);

        submit = new JButton("View Player");
        cancel = new JButton("Cancel");
        buttonPanel.add(submit);
        buttonPanel.add(cancel);

        submit.addActionListener(new SearchResultsListener());
        cancel.addActionListener(new SearchResultsListener());
    }

    private void setupTable()
    {
        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        dtm = new SearchResultTableModel();
        table.setModel(dtm);

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
        table.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeys);

        DefaultTableCellRenderer leftRender = new DefaultTableCellRenderer();
        leftRender.setHorizontalAlignment(JLabel.LEFT);
        table.getColumnModel().getColumn(1).setCellRenderer(leftRender);

        resultsPanel.add(new JScrollPane(table));
    }

    private void sizeColumns()
    {
        TableColumnModel tcm = table.getColumnModel();
        tcm.getColumn(0).setMinWidth(150);
        tcm.getColumn(1).setMinWidth(70);
        if (Integer.parseInt(Profile.getLevel()) < 90)
        {
            tcm.getColumn(2).setMinWidth(150);
        }
        else
        {
            tcm.getColumn(2).setMinWidth(300);
        }

        tcm.getColumn(3).setMinWidth(200);
        tcm.getColumn(4).setMinWidth(200);
    }

    private void search(String search, String type)
    {
        String results = NetProtocol.searchForClient(search, type.toLowerCase());
        String[] resultsArray = results.split("\n");

        for (int i = 0; i < resultsArray.length; i++)
        {
            if (!resultsArray[i].equals("none") && !resultsArray[i].contains("ERROR"))
            {
                dtm.addClientResult(new ClientResult(resultsArray[i]));
            }
        }

        sizeColumns();
    }

    private class SearchResultsListener implements ActionListener
    {

        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == submit)
            {
                int rowi = table.convertRowIndexToModel(table.getSelectedRow());
                if (rowi >= 0)
                {
                    new PlayerDetails(dtm.getClientResult(rowi).getStringDatabaseid());
                    frame.dispose();
                    root.dispose();
                }

            }
            else if (e.getSource() == cancel)
            {
                frame.dispose();
            }
        }
    }
}
