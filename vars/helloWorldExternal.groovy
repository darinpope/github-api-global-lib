def call(Map config) {
    def text = sh(returnStdout: true, script: './build-support-scripts/linux/hello-world.sh').trim()
    echo text
}