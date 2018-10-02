def call(Map config) {
  //def action = new AddSidebarLinkAction("testLinkFromDarin",null,"http://www.darinpope.com/",null)
  action = [
    "testdarin",
    "http://www.darinpope.com",
    "foo1",
    "foo2"
  ] as hudson.model.Action
  currentBuild.rawBuild.addAction(action)
}
