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
  return projectJobMapping["${config.jobName}"]
}
