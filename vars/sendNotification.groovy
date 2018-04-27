def call(Map config) {
    echo 'inside sendNotification'
    def rawBody = libraryResource 'com/planetpope/emailtemplate/build-results.html'
    rawBody.replaceAll("#APPLICATION_NAME#",env.JOB_BASE_NAME)
    rawBody.replaceAll("#SOURCE_BRANCH#",env.BRANCH_NAME)
    rawBody.replaceAll("#BUILD_NUMBER#",env.BUILD_NUMBER)
    rawBody.replaceAll("#DEVELOPER#",gitAuthorName())
    rawBody.replaceAll("#BUILD_URL#",env.BUILD_URL)
    echo rawBody
}