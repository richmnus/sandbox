package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Table extends JTable {

    private static final long serialVersionUID = 1L;

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Resize a column to fit the column header
        // TableColumn continueCol = getColumnModel().getColumn(1);
        // continueCol.setHeaderRenderer(getTableHeader().getDefaultRenderer());
        // continueCol.sizeWidthToFit();
        // continueCol.setMinWidth(0);

    }

    public void autoSizeCols() {

        // strategy - get max width for cells in column and
        // make that the preferred width
        TableColumnModel columnModel = getColumnModel();

        // For each column
        for (int col = 0; col < getColumnCount(); col++) {
            int maxwidth = 0;
           
            // For each row
            for (int row = 0; row < getRowCount(); row++) {
                TableCellRenderer rend = getCellRenderer(row, col);
                Object value = getValueAt(row, col);
                Component comp = rend.getTableCellRendererComponent(this,
                        value, false, false, row, col);
                maxwidth = Math.max(comp.getPreferredSize().width, maxwidth);
            }

            TableColumn column = columnModel.getColumn(col);
            TableCellRenderer headerRenderer = column.getHeaderRenderer();
            if (headerRenderer == null) {
                headerRenderer = getTableHeader().getDefaultRenderer();
            }
            Object headerValue = column.getHeaderValue();
            Component headerComp = headerRenderer
                    .getTableCellRendererComponent(this, headerValue, false,
                            false, 0, col);
            maxwidth = Math.max(maxwidth, headerComp.getPreferredSize().width);

            column.setPreferredWidth(maxwidth);
        }
    }

    public void autoSizeCol(int columnIndex) {

    }

}
