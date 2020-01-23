def call(Map config=[:]) {
  def projectJobMapping = [
    "QA_BTrack_MIAM_API_Services_Deployment_Pipeline_Job":"project-a",
    "QA_BTrack_MIAM_Authorization_API_Services_Deployment_Pipeline_Job":"project-a",
    "QA_BTrack_MIAM_User_Management_UI_Deployment_Pipeline_Job":"project-a",
    "QA_BTrack_MIAM_UI_Deployment_Pipeline_Job":"project-b",
    "QA_BTrack_CMS_Deployment_Pipeline_Job":"project-c",
    "QA_BTrack_Adapters_Group_Deployment_Pipeline_Job":"project-d",
    "QA_BTrack_Clientline_Adapters_Group_Deployment_Pipeline_Job":"project-d",
    "QA_BTrack_Notification_Adapters_Group_Deployment_Pipeline_Job":"project-d"
  ]
  deployableJobs = sh (script: '''awk '{split(\$0,a,":"); print a[2]}' controlfile_bts.txt''',returnStdout: true).trim()
  deployableJobList = deployableJobs.split("\\r\\n|\\n|\\r")
  echo "Deployable Job List: ${deployableJobList}"
  deployableJobList.each {
    def projectId = projectJobMapping["${it}"]
    echo "sending " + projectId
    publishEvent(event:jsonEvent("""{"event":"${projectId}"}"""),verbose: true)
  }
}
