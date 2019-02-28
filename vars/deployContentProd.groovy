def call(Map config=[:]) {
  def application
  def version
  def event = currentBuild?.getBuildCauses()[0]?.event?.event?.toString()
  def causeClass = currentBuild?.getBuildCauses()[0]?._class
  if(event == "deploy-content-prod" && causeClass == "com.cloudbees.jenkins.plugins.pipeline.events.EventTriggerCause") {
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
