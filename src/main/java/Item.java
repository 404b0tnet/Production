public interface Item {

  /**
   *
   * @return
   */
  int getId();                                   // return int

  /**
   *
   * @param name
   */
  void setProductName(String name);
  String getProductName();                              // return string

  /**
   *
   * @param manufacturer
   */
  void setProductManufacturer(String manufacturer);
  String getProductManufacturer();                      // return string

}
