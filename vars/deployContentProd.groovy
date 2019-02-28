def call() {
  def application
  def version
  def event = currentBuild?.getBuildCauses()[0]?.event?.event?.toString()
  if(event == "deploy-content-prod") {
    application = currentBuild?.getBuildCauses()[0]?.event?.application?.toString()
    version = currentBuild?.getBuildCauses()[0]?.event?.version?.toString()
  } else {
    application = params.application
    version = params.version
  }
  sh """
    echo version = $version
    echo application = $application
  """
}
