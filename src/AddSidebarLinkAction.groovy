class AddSidebarLinkAction implements hudson.model.Action {
  private String displayName;
  private String iconFileName;
  private String urlName;
  
  public AddSidebarLinkAction(String displayName,String iconFileName,String urlName) {
    this.displayName = displayName;
    this.iconFileName = iconFileName;
    this.urlName = urlName;
  }
  
  public String getDisplayName() {
    return displayName;
  }
  
  public String getIconFileName() {
    return iconFileName;
  }
  
  public String getUrlName() {
    return urlName;
  }
}
