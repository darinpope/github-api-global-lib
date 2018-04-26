def call(Map config) {
    sh(returnStdout: true, script: 'git log --format="%an" | head -1').trim()
}