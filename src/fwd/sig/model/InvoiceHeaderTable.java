
package fwd.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class InvoiceHeaderTable extends AbstractTableModel{
    private ArrayList<InvoiceHeader> invoices;
    private String[] columns = {"Number","Date","Customer Name","Total"};

    public InvoiceHeaderTable(ArrayList<InvoiceHeader> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column]; 
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceHeader invoice = invoices.get(rowIndex);
        
        switch (columnIndex){
            case 0 : return invoice.getItemName();
            case 1 : return invoice.getItemNum();
            case 2 : return invoice.getCustomerName();
            case 3 : return invoice.getTotalInvoice();
            
            default : return "";
    }
    }
    
    
}
