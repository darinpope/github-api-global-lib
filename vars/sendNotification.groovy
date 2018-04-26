def call(Map config) {
    echo 'inside sendNotification'
    def rawBody = libraryResource 'com/planetpope/emailtemplate/build-results.html'
    echo rawBody
}