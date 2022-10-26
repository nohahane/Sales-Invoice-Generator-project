
package fwd.sig.controller;

import fwd.sig.model.InvoiceLinesTable;
import fwd.sig.view.SalesInvoiceWindow;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import fwd.sig.model.InvoiceHeader;


public class TablesListner implements ListSelectionListener {
    private SalesInvoiceWindow win;

    public TablesListner (SalesInvoiceWindow win){
        this.win = win;
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = win.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1){
        InvoiceHeader currentInvoice = win.getInvoices().get(selectedIndex);
        win.getNumberLabel().setText(""+currentInvoice.getItemName());
        win.getDateLabel().setText(currentInvoice.getItemNum());
        win.getCustomerLabel().setText(currentInvoice.getCustomerName());
        win.getTotalLabel().setText(""+currentInvoice.getTotalInvoice());
        InvoiceLinesTable LinesTableModel = new InvoiceLinesTable(currentInvoice.getInvoiceLines());
        win.getLineTable().setModel(LinesTableModel);
        LinesTableModel.fireTableDataChanged();
    }}
    
}
