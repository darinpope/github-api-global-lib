def call(Map config=[:]) {
  def changeLogItems = currentBuild.getBuildCauses()[0]?.event?.changelog?.items
  if(0 == changeLogItems.size()) {
    println 'no items found in changelog'
    return
  }
  changeLogItems.each {
    // println it.fromString + '; ' + it.toString
    def fromTo = it.fromString+"||"+it.toString
    switch(fromTo) {
      case "Backlog||Selected for Development":
        def component = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.components[0]?.name
        def fixVersion = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.fixVersions[0]?.name
        def jsonString = '{"event":"deploy-to-qa","component":"'+component+'","version":"'+fixVersion+'"}'
        publishEvent(jsonEvent(jsonString))
        break;
      case "Selected for Development||In Progress":
        def component = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.components[0]?.name
        def fixVersion = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.fixVersions[0]?.name
        def jsonString = '{"event":"deploy-to-stage","component":"'+component+'","version":"'+fixVersion+'"}'
        publishEvent(jsonEvent(jsonString))
        break;
    }
  }
}