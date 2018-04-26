def call(Map config) {
    echo 'inside sendNotification'
    def rawBody = libraryResource 'com/planetpope/emailtemplate/build-results.html'
    rawBody.replace("#APPLICATION_NAME#",env.JOB_BASE_NAME).replace("#SOURCE_BRANCH#",env.BRANCH_NAME).replace("#BUILD_NUMBER#",env.BUILD_NUMBER).replace("#DEVELOPER#",gitAuthorName()).replace("#BUILD_URL#",env.BUILD_URL)
    echo rawBody
}