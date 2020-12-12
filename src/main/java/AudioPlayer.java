/**
 * @author Justin Smith
 */
class AudioPlayer extends Product implements MultimediaControl{

  private String supportedAudioFormats = "";
  private String supportedPlaylistFormats = "";

  /**
   *
   * @param name - Name of audio player
   * @param manufacturer - name of audio manufacturer
   * @param supportedAudioFormats - Type String of support audio formats
   * @param supportedPlaylistFormats - Type String of supported playlist formats
   */
  public AudioPlayer(String name, String manufacturer,
      String supportedAudioFormats, String supportedPlaylistFormats){

    super(ItemType.AUDIO, manufacturer, name);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }


  /**
   *
   * @return This returns the toString
   */
  @Override
  public String toString(){
    if(supportedAudioFormats.equals("") || supportedPlaylistFormats.equals(""))
      return  super.toString();
    else {
      return super.toString() +
          "\nSupported Audio Formats: " + supportedAudioFormats +
          "\nSupported Playlist Formats: " + supportedPlaylistFormats;
    }

  }

  /**
   *
   */
  public void play(){
    System.out.println("Playing");
  }

  /**
   *
   */
  public void stop(){
    System.out.println("Stopping");
  }

  /**
   *
   */
  public void previous(){
    System.out.println("Previous");
  }

  /**
   *
   */
  public void next(){
    System.out.println("Next");
  }

}
