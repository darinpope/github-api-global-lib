def call(Map config) {
  def action = new AddSidebarLinkAction("testLinkFromDarin",null,"http://www.darinpope.com/",null)
  currentBuild.rawBuild.addAction(action)
}
