def call(Map config) {
  def action = new AddSidebarLinkAction("testLinkFromDarin",null,"http://www.darinpope.com/")
  currentBuild.rawBuild.getActions().add(action)
}
