def call(Map config) {
    echo 'inside sendNotification'
    def rawBody = libraryResource 'com/planetpope/emailtemplate/build-results.html'
    def binding = [
            applicationName: env.JOB_BASE_NAME,
            sourceBranch   : env.GIT_BRANCH,
            buildNumber    : env.BUILD_NUMBER,
            developer      : gitAuthorName(),
            buildUrl       : env.BUILD_URL
    ]

    def render = renderTemplate(rawBody,binding)
    echo render
    def subjectLine = env.JOB_BASE_NAME + ' - ' + env.BUILD_NUMBER + ' - ' + currentBuild.currentResult
    echo subjectLine
}
