def call(Map config) {
    sh(returnStdout: true, script: './build-support-scripts/linux/hello-world.sh').trim()
}