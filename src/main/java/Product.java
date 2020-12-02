public class Product implements Item {


  private int id;
  private ItemType type;
  private String productManufacturer;
  private String productName;


  public Product(ItemType type, String productManufacturer,
      String productName) {
    this.type = type;
    this.productManufacturer = productManufacturer;
    this.productName = productName;
  }

  @Override
  public String toString() {
    return "Name: " + getProductName() +
        "\nManufacturer: " + getProductManufacturer() +
        "\nType: " + type;
  }


  public int getId() {
    return id;
  }

  public String getProductManufacturer() {
    return productManufacturer;
  }

  public String getProductName() {
    return productName;
  }

  public String getItemTypeCode(){
    return type.getCode();
  }


  public void setProductManufacturer(String productManufacturer) {
    this.productManufacturer = productManufacturer;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }
}
