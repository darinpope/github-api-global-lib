def call(Map config=[:]) {
    def emailBody = "Please go to ${env.BUILD_URL} to verify the build."
    def emailSubject = "[Jenkins] ${env.JOB_NAME} - Build# ${env.BUILD_NUMBER}"
    sendEmail("${config.to}","${emailSubject}","${emailBody}")
}