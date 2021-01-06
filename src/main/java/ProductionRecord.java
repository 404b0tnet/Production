/**
 * @author Justin Smith
 */
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class ProductionRecord{


  private int productionNumber;
  private int productID;
  private String serialNumber;
  private ZonedDateTime dateProduced;
  private ZoneId timeZone;

  /**
   *
   * @param productID
   */
  public ProductionRecord(int productID){

    this.productID = productID;
    productionNumber = 0;
    serialNumber = "0";
    dateProduced = ZonedDateTime.now();
  }

  /**
   *
   * @param productionNumber
   * @param productID
   * @param serialNumber
   * @param dateProduced
   */
  public ProductionRecord(int productionNumber, int productID,
      String serialNumber, ZonedDateTime dateProduced){

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


    dateProduced = ZonedDateTime.now();

  }


  /**
   *
   * @return
   */
  public String toString(){
    // Example Date Format
    // Date: Mon Oct 14 10:29:48 UTC 2019"
    DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy");
    String formattedDate = getProdDate().format(formatPattern);



    return "Prod. Num: " + getProductionNum() + " " +
        "Product ID: " + getProductID() + " " +
        "Serial Num: " + getSerialNum() + " " +
        "Date: " + formattedDate;
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
  public void setProdDate(ZonedDateTime date){
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
  public ZonedDateTime getProdDate(){
    return dateProduced;
  }



}