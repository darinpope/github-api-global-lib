def call(Map config) {
    sh(returnStdout: true, script: 'git log --format="%ae" | head -1').trim()
}