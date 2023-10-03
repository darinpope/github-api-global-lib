// mySharedLibrary.groovy
def checkoutGitRepository(String repositoryUrl, String branch) {
    node {
        stage('Checkout') {
            
           // Checkout the Git repository using the 'checkout' step
   checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              userRemoteConfigs: [[url: repositoryUrl]]])
              def checkoutDir = "${WORKSPACE}/my-repo-directory"
              def targetDir = "${WORKSPACE}/my-target-directory"
             sh "mkdir -p ${targetDir}"
             // Create the target directory if it doesn't exist
             sh "mkdir -p ${targetDir}"

              // Move the checked-out code to the target directory
                  sh "mv ${checkoutDir}/* ${targetDir}/"
            echo "success"
        }
    }
}
