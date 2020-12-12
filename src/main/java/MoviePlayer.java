/**
 * @author Justin Smith
 */
public class MoviePlayer extends Product implements MultimediaControl{

  private Screen screen;
  private MonitorType monitorType;

  /**
   *
   * @param name - Type String. Name of movie player
   * @param manufacturer Type String. Name of manufacturer
   * @param screen - Type Screen. Screen object
   * @param monitorType - Type MonitorType. Monitor type enum.
   */
  public MoviePlayer(String name, String manufacturer,
      Screen screen, MonitorType monitorType){

    super(ItemType.VISUAL, manufacturer, name);

    this.screen = screen;
    this.monitorType = monitorType;
  }


  @Override
  public void play(){
    System.out.println("Playing");
  }

  @Override
  public void stop(){
    System.out.println("Stopping");
  }

  @Override
  public void previous(){
    System.out.println("Previous");
  }

  @Override
  public void next(){
    System.out.println("Next");
  }


  public String toString(){
    return super.toString() +
        "\nMonitor Type: " + monitorType +
        screen.toString();
  }
}
