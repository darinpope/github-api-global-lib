def call(Map config) {
  def action = new AddSidebarLinkAction()
  currentBuild.rawBuild.getActions().add(action)
}
