def call(Map config) {
    echo 'inside sendNotification'
    def rawBody = libraryResource 'com/planetpope/emailtemplate/build-results.html'
    rawBody = rawBody.replaceAll("#APPLICATION_NAME#",env.JOB_BASE_NAME)
    rawBody = rawBody.replaceAll("#SOURCE_BRANCH#",env.GIT_BRANCH)
    rawBody = rawBody.replaceAll("#BUILD_NUMBER#",env.BUILD_NUMBER)
    rawBody = rawBody.replaceAll("#DEVELOPER#",gitAuthorName())
    rawBody = rawBody.replaceAll("#BUILD_URL#",env.BUILD_URL)
    echo rawBody
    def subjectLine = env.JOB_BASE_NAME + ' - ' + env.BUILD_NUMBER + ' - ' + currentBuild.currentResult
    echo subjectLine
}