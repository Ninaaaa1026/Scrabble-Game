/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
package client;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
    /**
	 * This class stores information of cell editable using a two-dimension array,
     * and provides get/set methods (isCellEditable and setCellEditable) for cell editable.
     * There are two constructors to initialize editable information with all false or all true.
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
    }
}
