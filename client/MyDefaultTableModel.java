package client;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean[][] editable_cells; // 2d array to represent rows and columns

    MyDefaultTableModel(int rows, int cols) { // constructor
    	super(rows, cols);
        this.editable_cells = new boolean[rows][cols];
        for(int i=0;i<rows;i++)
        	for(int j=0;j<cols;j++) {
        		setCellEditable(i,j,true);
        	}
    }
    
    MyDefaultTableModel(Vector<Object> rows, Vector<String> cols) { // constructor
    	super(rows, cols);
        this.editable_cells = new boolean[rows.size()][cols.size()];
        for(int i=0;i<rows.size();i++)
        	for(int j=0;j<cols.size();j++) {
        		setCellEditable(i,j,false);
        	}
    }

    @Override
    public boolean isCellEditable(int row, int column) { // custom isCellEditable function
        return this.editable_cells[row][column];
    }

    public void setCellEditable(int row, int col, boolean value) {
        this.editable_cells[row][col] = value; // set cell true/false
        this.fireTableCellUpdated(row, col);
    }
}
