package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class TableUtils {

    public static void autoSizeCols(JTable table) {
        if (table == null || table.getColumnCount() == 0) {
            return;
        }

        // strategy - get max width for cells in column and
        // make that the preferred width
        TableColumnModel columnModel = table.getColumnModel();

        // For each col
        for (int col = 0; col < table.getColumnCount(); col++) {
            int maxwidth = 0;

            // For each row
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer rend = table.getCellRenderer(row, col);
                Object value = table.getValueAt(row, col);
                Component comp = rend.getTableCellRendererComponent(table,
                        value, false, false, row, col);
                maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
            }

            TableColumn column = columnModel.getColumn(col);
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = table.getTableHeader().getDefaultRenderer();
            }
            Object headerValue = column.getHeaderValue();
            Component headerComp = headerRenderer
                    .getTableCellRendererComponent(table, headerValue, false,
                            false, 0, col);
            maxwidth = Math.max(maxwidth, headerComp.getPreferredSize().width);

            column.setPreferredWidth(maxwidth);
        }
    }

}
