
package fwd.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InvoiceLinesTable extends AbstractTableModel{
    private ArrayList<InvoiceLines> lines;
    private String[] Columns = {"Number","Item Name","Item Price","Count","Item Total"};

    public InvoiceLinesTable(ArrayList<InvoiceLines> lines) {
        this.lines = lines;
    }
    
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return Columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return Columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceLines line = lines.get(rowIndex);
        
        switch (columnIndex){
            case 0 : return line.getInvoice().getItemName();
            case 1 : return line.getItemName();
            case 2 : return line.getItemPrice();
            case 3 : return line.getItemCount();
            case 4 : return line.getLineTotal();
            
            default : return "";
        }
    }
    
}
