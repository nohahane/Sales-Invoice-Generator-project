
package fwd.sig.model;


public class InvoiceLines {
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private InvoiceHeader Invoice;

    public InvoiceLines() {
    }

    public InvoiceLines(String Item, double Price, int Count, InvoiceHeader Invoice) {
        this.itemName = Item;
        this.itemPrice = Price;
        this.itemCount = Count;
        this.Invoice = Invoice;
    }
    
   
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public InvoiceHeader getInvoice() {
        return Invoice;
    }
    
     public double getLineTotal(){
        return itemPrice*itemCount;
    }
    
    public String getLinesInfo(){
        return Invoice.getItemName() + "," + itemName + "," + itemPrice + "," + itemCount ;
    }
}
