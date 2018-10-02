public class AddSidebarLinkAction implements hudson.model.Action,java.io.Serializable {
  private String displayName;
  private String iconFileName;
  private String urlName;
  
  public AddSidebarLinkAction(String displayName,String iconFileName,String urlName) {
    this.displayName = displayName;
    this.iconFileName = iconFileName;
    this.urlName = urlName;
  }
  
  @Override 
  public String getDisplayName() {
    return displayName;
  }
  
  @Override 
  public String getIconFileName() {
    return iconFileName;
  }
  
  @Override 
  public String getUrlName() {
    return urlName;
  }
}
