def call(String gitUrl, String branch) {
    def gitCommand = """git clone ${gitUrl} -b ${branch}"""
    
    try {
        sh(script: gitCommand, returnStatus: true)
        echo "Git clone succeeded."
    } catch (Exception e) {
        error "Git clone failed: ${e.message}"
    }
}
