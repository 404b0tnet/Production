import java.util.Date;

public class ProductionRecord{


  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;


  public ProductionRecord(int productID){

    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();

  }


  public ProductionRecord(int productionNumber, int productID,
      String serialNumber, Date dateProduced){

    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;

  }

  public ProductionRecord(Product newProduct, int itemCounter){
    String counter = String.format("%05d",itemCounter);

    serialNumber = newProduct.getProductManufacturer().substring(0,3) +
        newProduct.getItemType() + counter;

    dateProduced = new Date();

  }



  public String toString(){
    String formatDate = String.format("Date: %tc",getProdDate());

    return "Prod. Num: " + getProductionNum() + " " +
        "Product ID: " + getProductID() + " " +
        "Serial Num: " + getSerialNum() + " " +
        formatDate;
  }



  // setters
  public void setProductID(int productID){
    this.productID = productID;
  }
  public void setProductionNum(int productionNumber){
    this.productionNumber = productionNumber;
  }
  public void setSerialNum(String serialNumber){
    this.serialNumber = serialNumber;
  }
  public void setProdDate(Date date){
    dateProduced = date;
  }

  // getters
  public int getProductionNum(){
    return productionNumber;
  }
  public int getProductID(){
    return productID;
  }
  public String getSerialNum(){
    return serialNumber;
  }
  public Date getProdDate(){
    return dateProduced;
  }



}