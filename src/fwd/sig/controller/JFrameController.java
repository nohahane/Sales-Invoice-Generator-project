
package fwd.sig.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import fwd.sig.model.InvoiceHeader;
import fwd.sig.model.InvoiceLines;
import fwd.sig.model.InvoiceHeaderTable;
import fwd.sig.model.InvoiceLinesTable;
import fwd.sig.view.SalesInvoiceWindow;
import fwd.sig.view.InvoiceHeaderWin;
import fwd.sig.view.InvoiceLineWin;



public class JFrameController implements ActionListener {
    
    private SalesInvoiceWindow win;
    private InvoiceHeaderWin InvoiceHeaderDialog;
    private InvoiceLineWin InvoiceLineDialog;
    
    public JFrameController (SalesInvoiceWindow win){
        this.win = win;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String actioncommand = e.getActionCommand();
        System.err.println("Action : " + actioncommand);
        switch (actioncommand){
            case "Load File":
                loadFile();
                break;
                
            case "Save File":
                saveFile();
                break;
                
            case "Create New Invoice": 
                createNewInvoice();
                break;
                
            case "Delete Invoice": 
                deleteInvoice();
                break;
                
                
            case "createInvoiceCancel":
                newInvoiceCancel();
                break;
                
            case "createInvoiceOK":
                newInvoiceOK();
                break;
                
            case "createLineOK":
                newLineOK();
                break;
                
            case "createLineCancel":
                newLineCancel();
                break;    
            
                
        }
            
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        try{
        int Result = fc.showOpenDialog(win);
        if (Result == JFileChooser.APPROVE_OPTION){
            File headerFile = fc.getSelectedFile();
            Path headerPath = Paths.get(headerFile.getAbsolutePath());
            List<String> headerLines =Files.readAllLines(headerPath);
            ArrayList<InvoiceHeader> invoicesArray = new ArrayList<>();
            for(String headerLine : headerLines){
                String[]headerInfo = headerLine.split(",");
                int invoiceNumber = Integer.parseInt(headerInfo[0]);
                String invoiceDate = headerInfo[1];
                String customerName = headerInfo[2];
                
                InvoiceHeader Invoice = new InvoiceHeader(invoiceNumber , invoiceDate, customerName);
                invoicesArray.add(Invoice);
            }
            Result = fc.showOpenDialog(win);
            if (Result == JFileChooser.APPROVE_OPTION){
                File lineFile = fc.getSelectedFile();
                Path linePath = Paths.get(lineFile.getAbsolutePath());
                List<String> lineLines = Files.readAllLines(linePath);
                for(String lineLine : lineLines){
                    String[]lineDetails = lineLine.split(",");
                    int invoiceNumber = Integer.parseInt(lineDetails[0]);
                    String itemName = lineDetails[1];
                    double itemPrice = Double.parseDouble(lineDetails[2]);
                    int count = Integer.parseInt(lineDetails[3]);
                    InvoiceHeader inv = null;
                    for (InvoiceHeader invoice : invoicesArray){
                        if(invoice.getItemName()== invoiceNumber){
                            inv = invoice;
                            break;
                        }
                    }
                    
                    InvoiceLines line = new InvoiceLines(itemName, itemPrice, count, inv);
                    inv.getInvoiceLines().add(line);
                }
            }
            
            win.setInvoices(invoicesArray);
            InvoiceHeaderTable invoiceTableModel = new InvoiceHeaderTable(invoicesArray);
            win.setInvoicesTableModel(invoiceTableModel);
            win.getInvoiceTable().setModel(invoiceTableModel);
            win.getInvoicesTableModel().fireTableDataChanged();
        }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        
    }

    private void saveFile() {
        ArrayList<InvoiceHeader> Invoices = win.getInvoices();
        String Headers = "";
        String Lines = "";
        for (InvoiceHeader Invoice : Invoices){
            String invoiceCSV = Invoice.getHeaderInfo();
            Headers += invoiceCSV;
            Headers += "\n";
            
            for (InvoiceLines Line : Invoice.getInvoiceLines()){
                String csvLine = Line.getLinesInfo();
                Lines += csvLine;
                Lines += "\n";
            }
        }
        try {
        JFileChooser FC = new JFileChooser();
        int Result = FC.showSaveDialog(win);
        if (Result == JFileChooser.APPROVE_OPTION){
            File HeaderFile = FC.getSelectedFile();
            FileWriter HeadersFW = new FileWriter(HeaderFile);
            HeadersFW.write(Headers);
            HeadersFW.flush();
            HeadersFW.close();
            
            Result = FC.showSaveDialog(win);
            if (Result == JFileChooser.APPROVE_OPTION){
            File LineFile = FC.getSelectedFile();
            FileWriter LinesFW = new FileWriter(LineFile);
            LinesFW.write(Lines);
            LinesFW.flush();
            LinesFW.close();
            }
        }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            }
        
   
    }

    private void createNewInvoice() {
        InvoiceHeaderDialog = new InvoiceHeaderWin(win);
        InvoiceHeaderDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int SelectedRow = win.getInvoiceTable().getSelectedRow();
        if (SelectedRow != -1){
            win.getInvoices().remove(SelectedRow);
            win.getInvoicesTableModel().fireTableDataChanged();
        }
        
    }


    private void newInvoiceCancel() {
        InvoiceHeaderDialog.setVisible(false);
        InvoiceHeaderDialog.dispose();
        InvoiceHeaderDialog = null ;
    }

    private void newInvoiceOK() {
        String date = InvoiceHeaderDialog.getInvoiceDateField().getText();
        String customer = InvoiceHeaderDialog.getCustNameField().getText();
        int number = win.getNextInvoiceNumber();     
        InvoiceHeader Invoice = new InvoiceHeader(number, date, customer);
        win.getInvoices().add(Invoice);
        win.getInvoicesTableModel().fireTableDataChanged();
        InvoiceHeaderDialog.setVisible(false);
        InvoiceHeaderDialog.dispose();
        InvoiceHeaderDialog = null;
        
    }

 private void newLineCancel() {
        InvoiceLineDialog.setVisible(false);
        InvoiceLineDialog.dispose();
        InvoiceLineDialog = null;
   }
    private void newLineOK() {
        String item = InvoiceLineDialog.getItemNameField().getText();
        int count = Integer.parseInt(InvoiceLineDialog.getItemCountField().getText());
        double price = Double.parseDouble(InvoiceLineDialog.getItemPriceField().getText());
        int selectedInvoice = win.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1){
        InvoiceHeader invoice = win.getInvoices().get(selectedInvoice);
        InvoiceLines line = new InvoiceLines(item, price, count, invoice);
        invoice.getInvoiceLines().add(line);
        InvoiceLinesTable LinesTableModel = (InvoiceLinesTable) win.getLineTable().getModel();
        LinesTableModel.fireTableDataChanged();
        win.getInvoicesTableModel().fireTableDataChanged();
        }
        InvoiceLineDialog.setVisible(false);
        InvoiceLineDialog.dispose();
        InvoiceLineDialog = null;
    }
   
}
