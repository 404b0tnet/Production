public class Product implements Item {


  private int id;
  private ItemType itemType;
  private String productManufacturer;
  private String productName;

  public Product() {
    productManufacturer = "";
    productName = "";
    id = 0;
  }

  /**
   * @param type
   * @param productManufacturer
   * @param productName
   */
  public Product(ItemType type, String productManufacturer,
      String productName) {

    itemType = type;
    this.productManufacturer = productManufacturer;
    this.productName = productName;
  }

  /**
   * @return
   */
  @Override
  public String toString() {
    return "Name: " + getProductName() +
        "\nManufacturer: " + getProductManufacturer() +
        "\nType: " + itemType;
  }

  /**
   * @return
   */
  public int getId() {
    return id;
  }

  /**
   * @return
   */
  public String getProductManufacturer() {
    return productManufacturer;
  }

  /**
   * @return
   */
  public String getProductName() {
    return productName;
  }

  /**
   * @return
   */
  public String getItemType() {
    return itemType.toString();
  }

  /**
   *
   * @param id
   */
  public void setID(int id) {
    this.id = id;
  }

  /**
   * @param itemType
   */
  public void setItemType(ItemType itemType) {
    this.itemType = itemType;
  }

  /**
   * @param productManufacturer
   */
  public void setProductManufacturer(String productManufacturer) {
    this.productManufacturer = productManufacturer;
  }

  /**
   * @param productName
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }
}
