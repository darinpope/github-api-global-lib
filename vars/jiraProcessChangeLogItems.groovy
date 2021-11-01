def call(Map config=[:]) {
  def changeLogItems = currentBuild.getBuildCauses()[0]?.event?.changelog?.items
  if(0 == changeLogItems.size()) {
    return
  }
  echo changeLogItems.size()
}