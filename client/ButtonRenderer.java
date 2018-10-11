/***
 * author: Chenjing Yu, Jinnan Li, Mochuan Wang, Mengwen Ma
 * email:
 * chenjingy@student.unimelb.edu.au
 * mochuanw@student.unimelb.edu.au
 * mengwenm@student.unimelb.edu.au
 * jinnanl@student.unimelb.edu.au
 */
package client;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/***
 * This class is the renderer for the invitation buttons.
 */
public class ButtonRenderer implements TableCellRenderer {
    @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JButton button = (JButton)value;
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }
        return button;
    }
}
