class AudioPlayer extends Product implements MultimediaControl{

  private String supportedAudioFormats = "";
  private String supportedPlaylistFormats = "";


  public AudioPlayer(String name, String manufacturer,
      String supportedAudioFormats, String supportedPlaylistFormats){

    super(ItemType.AUDIO, manufacturer, name);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }


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


  public void play(){
    System.out.println("Playing");
  }

  public void stop(){
    System.out.println("Stopping");
  }

  public void previous(){
    System.out.println("Previous");
  }

  public void next(){
    System.out.println("Next");
  }

}
