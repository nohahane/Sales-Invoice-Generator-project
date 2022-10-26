
package fwd.sig.model;

import java.util.ArrayList;


public class InvoiceHeader {
    private int itemName;
    private String itemNum;
    private String customerName;
    private ArrayList<InvoiceLines> invoiceLines;

    public InvoiceHeader() {
    }

    public InvoiceHeader(int Number, String Date, String Customer) {
        this.itemName = Number;
        this.itemNum = Date;
        this.customerName = Customer;
    }
    
 

    public ArrayList<InvoiceLines> getInvoiceLines() {
        if (invoiceLines == null){
            invoiceLines = new ArrayList<>();
        }
        return invoiceLines;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getItemName() {
        return itemName;
    }

    public void setItemName(int itemName) {
        this.itemName = itemName;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }
    
       public double getTotalInvoice(){
        double total = 0.0;
        for(InvoiceLines line : getInvoiceLines()){
            total += line.getLineTotal();
        }
        return total;
    }
    public String getHeaderInfo(){
     return itemName + "," + itemNum + "," + customerName;
            
    }
    
}
