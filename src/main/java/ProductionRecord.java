import java.util.Date;

public class ProductionRecord{


  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  /**
   *
   * @param productID
   */
  public ProductionRecord(int productID){

    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = new Date();


  }

  /**
   *
   * @param productionNumber
   * @param productID
   * @param serialNumber
   * @param dateProduced
   */
  public ProductionRecord(int productionNumber, int productID,
      String serialNumber, Date dateProduced){

    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;

  }

  /**
   *
   * @param newProduct
   * @param itemCounter
   */
  public ProductionRecord(Product newProduct, int itemCounter){
    String counter = String.format("%05d",itemCounter);

    serialNumber = newProduct.getProductManufacturer().substring(0,3) +
        newProduct.getItemType() + counter;

    dateProduced = new Date();

  }


  /**
   *
   * @return
   */
  public String toString(){
    String formatDate = String.format("Date: %tc",getProdDate());

    return "Prod. Num: " + getProductionNum() + " " +
        "Product ID: " + getProductID() + " " +
        "Serial Num: " + getSerialNum() + " " +
        formatDate;
  }



  // setters

  /**
   *
   * @param productID
   */
  public void setProductID(int productID){
    this.productID = productID;
  }

  /**
   *
   * @param productionNumber
   */
  public void setProductionNum(int productionNumber){
    this.productionNumber = productionNumber;
  }

  /**
   *
   * @param serialNumber
   */
  public void setSerialNum(String serialNumber){
    this.serialNumber = serialNumber;
  }

  /**
   *
   * @param date
   */
  public void setProdDate(Date date){
    dateProduced = date;
  }

  // getters

  /**
   *
   * @return
   */
  public int getProductionNum(){
    return productionNumber;
  }

  /**
   *
   * @return
   */
  public int getProductID(){
    return productID;
  }

  /**
   *
   * @return
   */
  public String getSerialNum(){
    return serialNumber;
  }

  /**
   *
   * @return
   */
  public Date getProdDate(){
    return dateProduced;
  }



}