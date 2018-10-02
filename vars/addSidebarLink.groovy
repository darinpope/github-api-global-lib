def call(Map config) {
  def action = new hudson.plugins.sidebar_link.LinkAction("http://www.darinpope.com/","testLinkFromDarin","star.gif")
  //action = [
  //  displayName: "testdarin",
  //  urlName: "http://www.darinpope.com",
  //  iconFileName: "star.gif"
  //] as hudson.model.PermalinkProjectAction
  currentBuild.rawBuild.addAction(action)
}
