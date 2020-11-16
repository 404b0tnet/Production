public class MoviePlayer extends Product implements MultimediaControl{

  private Screen screen;
  private MonitorType monitorType;


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
