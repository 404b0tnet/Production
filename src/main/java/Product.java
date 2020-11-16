public abstract class Product implements Item {

  private int id;
  private String manufacturer, name;
  private ItemType type;


  /*
        Constructor
   */
  public Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;

  }


  /*
        Interface methods
   */
  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  public String getManufacturer() {
    return manufacturer;
  }


  /*
      toString method to return the following"
      Name:
      Manufacturer:
      Type:
   */
  public String toString() {
    String output = "Name: " + getName()
        + "\nManufacturer: " + getManufacturer()
        + "\nType.code: " + type.getCode()
        + "\nType: " + type;

    return output;
  }
}
