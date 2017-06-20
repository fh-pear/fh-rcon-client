import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * TabelModel for displaying database search results when searching for clients
 */
public class SearchResultTableModel extends AbstractTableModel
{
    //<id>:<name>:<guid>:<connections>:<level (String title)>:<level (int value)>:<first seen>:<last seen>

    private String[] columnNames =
    {
        "Name",
        "@ID",
        "GUID",
        "First Seen",
        "Last Seen",
    };

    private List<ClientResult> clients;

    public SearchResultTableModel()
    {
        clients = new ArrayList<ClientResult>();
    }

    public SearchResultTableModel(List<ClientResult> clients)
    {
        this.clients = clients;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public int getRowCount()
    {
        return clients.size();
    }

    @Override
    public Class getColumnClass(int column)
    {
        if (column == 1)
        {
            return Integer.class;
        }

        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    @Override
    public Object getValueAt(int row, int column)
    {
        ClientResult client = getClientResult(row);

        switch (column)
        {
            case 0:
                return client.getName();
            case 1:
                return client.getDatabaseid();
            case 2:
                return client.getGuid();
            case 3:
                return client.getFirstSeen();
            case 4:
                return client.getLastSeen();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column)
    {
        ClientResult client = getClientResult(row);

        switch (column)
        {
            case 0:
                client.setName((String) value);
                break;
            case 1:
                client.setDatabaseid((int) value);
                break;
            case 2:
                client.setGuid((String) value);
                break;
            case 3:
                client.setFirstSeen((String) value);
                break;
            case 4:
                client.setLastSeen((String) value);
                break;
        }

        fireTableCellUpdated(row, column);
    }

    public ClientResult getClientResult(int row)
    {
        return clients.get(row);
    }

    public void addClientResult(ClientResult client)
    {
        insertClientResult(getRowCount(), client);
    }

    public void insertClientResult(int row, ClientResult client)
    {
        clients.add(row, client);
        fireTableRowsInserted(row, row);
    }

    public void removeClientResult(int row)
    {
        clients.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
