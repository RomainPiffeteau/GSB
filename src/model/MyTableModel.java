package model;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;*/
	private final String[] titles = {"","description","grade"};

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return titles.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return titles[columnIndex];
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
