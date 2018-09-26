package client;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

public class ButtonMouseListener extends MouseAdapter {
	private final JTable table;

	public ButtonMouseListener(JTable table) {
		this.table = table;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int column = table.getColumnModel().getColumnIndexAtX(e.getX());
		int row = e.getY() / table.getRowHeight();

		if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
			Object value = table.getValueAt(row, column);
			if (value instanceof JButton) {
				try {
					ScrabbleClient.remoteServer.invitePlayer(table.getValueAt(row, column - 1).toString());
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				// ((JButton)value).doClick();
			}
		}
	}
}
