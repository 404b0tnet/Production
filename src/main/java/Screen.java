public class Screen implements ScreenSpec {

  private String resolution = "";
  private int refreshRate;
  private int responseTime;


  public Screen(String resolution, int refreshRate, int responseTime){
    this.refreshRate = refreshRate;
    this.resolution = resolution;
    this.responseTime = responseTime;
  }


  @Override
  public String getResolution() {
    return resolution;
  }

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  @Override
  public int getResponseTime() {
    return responseTime;
  }


  @Override
  public String toString() {
    return "\nScreen:" +
        "\nResolution: " + getResolution() +
        "\nRefresh Rate: " + getRefreshRate() +
        "\nResponse Time: " + getResponseTime();
  }

}
