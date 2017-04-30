import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PenaltyTableModel extends AbstractTableModel
{
	private String[] columnNames =
			{
					"Type",
					"Reason",
					"Expires",
					"Active",
			};

	private List<Penalty> penalties;

	public PenaltyTableModel()
	{
		penalties = new ArrayList<Penalty>();
	}

	public PenaltyTableModel(List<Penalty> penalties)
	{
		this.penalties = penalties;
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
		return penalties.size();
	}

	@Override
	public Class getColumnClass(int column)
	{
		if (column == 3)
			return Boolean.class;

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
		Penalty penalty = getPenalty(row);

		switch (column)
		{
			case 0:
				return penalty.getPenaltyType();
			case 1:
				return penalty.getReason();
			case 2:
				return penalty.getTimeExpire();
			case 3:
				return penalty.isActive();
			default:
				return null;
		}
	}

	@Override
	public void setValueAt(Object value, int row, int column)
	{
		Penalty penalty = getPenalty(row);

		switch (column)
		{
			case 0:
				penalty.setPenaltyType((String) value);
				break;
			case 1:
				penalty.setReason((String) value);
				break;
			case 2:
				penalty.setTimeExpire((String) value);
				break;
		}

		fireTableCellUpdated(row, column);
	}

	public Penalty getPenalty(int row)
	{
		return penalties.get(row);
	}

	public void addPenalty(Penalty penalty)
	{
		insertPenalty(getRowCount(), penalty);
	}

	public void insertPenalty(int row, Penalty penalty)
	{
		penalties.add(row, penalty);
		fireTableRowsInserted(row, row);
	}

	public void removePenalty(int row)
	{
		penalties.remove(row);
		fireTableRowsDeleted(row, row);
	}
}