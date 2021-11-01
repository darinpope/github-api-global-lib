def call(Map config=[:]) {
  def changeLogItems = currentBuild.getBuildCauses()[0]?.event?.changelog?.items
  if(0 == changeLogItems.size()) {
    println 'no items found in changelog'
    return
  }
  changeLogItems.each {
    println it.fromString + '; ' + it.toString
  }
}