import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AliasTableModel extends AbstractTableModel
{
	private String[] columnNames =
			{
					"Alias",
					"First Used On",
					"Last Used On",
			};

	private List<Alias> aliases;

	public AliasTableModel()
	{
		aliases = new ArrayList<Alias>();
	}

	public AliasTableModel(List<Alias> aliases)
	{
		this.aliases = aliases;
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
		return aliases.size();
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
		Alias alias = getAlias(row);

		switch (column)
		{
			case 0:
				return alias.getAlias();
			case 1:
				return alias.getFirstUsed();
			case 2:
				return alias.getLastUsed();
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Alias alias = getAlias(row);

		switch (column)
		{
			case 0:
				alias.setAlias((String) value);
				break;
			case 1:
				alias.setFirstUsed((String) value);
				break;
			case 2:
				alias.setLastUsed((String) value);
				break;
		}

		fireTableCellUpdated(row, column);
	}

	public Alias getAlias(int row)
	{
		return aliases.get(row);
	}

	public void addAlias(Alias alias)
	{
		insertAlias(getRowCount(), alias);
	}

	public void insertAlias(int row, Alias alias)
	{
		aliases.add(row, alias);
		fireTableRowsInserted(row, row);
	}

	public void removeAlias(int row)
	{
		aliases.remove(row);
		fireTableRowsDeleted(row, row);
	}
}