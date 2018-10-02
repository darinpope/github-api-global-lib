def call(Map config) {
  //def action = new AddSidebarLinkAction("testLinkFromDarin",null,"http://www.darinpope.com/",null)
  action = [
    displayName: "testdarin",
    urlName: "http://www.darinpope.com",
    iconFileName: "foo"
  ] as hudson.model.ProminentProjectAction
  currentBuild.rawBuild.addAction(action)
}
