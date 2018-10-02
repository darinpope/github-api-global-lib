def call(Map config) {
  //def action = new AddSidebarLinkAction("testLinkFromDarin","24x24","http://www.darinpope.com/",null)
  action = [
    displayName: "testdarin",
    urlName: "http://www.darinpope.com",
    iconFileName: "24x24"
  ] as hudson.model.PermalinkProjectAction
  currentBuild.rawBuild.addAction(action)
}
