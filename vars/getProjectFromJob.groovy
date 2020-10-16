def call(Map config=[:]) {
  def projectJobMapping = [
    "QA_BTrack_MIAM_API_Services_Deployment_Pipeline_Job":["projectId":"project-a","gitUrl":"https://github.com/darinpope/topbean-cb-primary.git","credentialsId":"","subdirectory":"project-a"],
    "QA_BTrack_MIAM_UI_Deployment_Pipeline_Job":["projectId":"project-b","gitUrl":"https://github.com/darinpope/topbean-cb-primary.git","credentialsId":"","subdirectory":"project-b"],
    "QA_BTrack_CMS_Deployment_Pipeline_Job":["projectId":"project-c","gitUrl":"https://github.com/darinpope/topbean-cb.git","credentialsId":"","subdirectory":"project-c"],
    "QA_BTrack_Adapters_Group_Deployment_Pipeline_Job":["projectId":"project-d","gitUrl":"https://github.com/darinpope/topbean-cb.git","credentialsId":"","subdirectory":"project-d"]
  ]
  deployableJobs = sh (script: '''awk '{split(\$0,a,":"); print a[2]}' controlfile_bts.txt''',returnStdout: true).trim()
  deployableJobList = deployableJobs.split("\\r\\n|\\n|\\r")
  echo "Deployable Job List: ${deployableJobList}"
  deployableJobList.each {
    def projectId = projectJobMapping["${it}"]["projectId"]
    def gitUrl = projectJobMapping["${it}"]["gitUrl"]
    def credentialsId = projectJobMapping["${it}"]["credentialsId"]
    def subdirectory = projectJobMapping["${it}"]["subdirectory"]
    echo "sending " + projectId
    publishEvent(event:jsonEvent("""{"event":"${projectId}","gitUrl":"${gitUrl}","credentialsId":"${credentialsId}","subdirectory":"${subdirectory}","projectId":"${projectId}"}"""),verbose: true)
  }
}
