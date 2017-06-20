import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClientTableModel extends AbstractTableModel
{

    private String[] columnNames =
    {
        "Client ID",
        "Score",
        "Name",
        "GUID",
    // "Database ID"
    };

    private List<Client> clients;

    public ClientTableModel()
    {
        clients = new ArrayList<Client>();
    }

    public ClientTableModel(List<Client> clients)
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
        Client client = getClient(row);

        switch (column)
        {
            case 0:
                return client.getClientId();
            case 1:
                return client.getScore();
            case 2:
                return client.getName();
            case 3:
                return client.getShortGuid();
            //	case 4:
            //return client.getDatabaseId();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column)
    {
        Client client = getClient(row);

        switch (column)
        {
            case 0:
                client.setClientId((String) value);
                break;
            case 1:
                client.setScore((String) value);
                break;
            case 2:
                client.setName((String) value);
                break;
            case 3:
                client.setGuid((String) value);
                break;
            //	case 4: client.setDatabaseId((String)value);
            //break;
        }

        fireTableCellUpdated(row, column);
    }

    public Client getClient(int row)
    {
        return clients.get(row);
    }

    public void addClient(Client client)
    {
        insertClient(getRowCount(), client);
    }

    public void insertClient(int row, Client client)
    {
        clients.add(row, client);
        fireTableRowsInserted(row, row);
    }

    public void removeClient(int row)
    {
        clients.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
