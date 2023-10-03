// mySharedLibrary.groovy
def checkoutGitRepository(String repositoryUrl, String branch, String targetDir) {
    node {
        stage('Checkout') {
            
           // Checkout the Git repository using the 'checkout' step
    checkout([$class: 'GitSCM',
              branches: [[name: branch]],
              userRemoteConfigs: [[url: repositoryUrl]],
              extensions: [[$class: 'CleanCheckout'], [$class: 'RelativeTargetDirectory', relativeTargetDir: targetDir]]])

            echo "success"
        }
    }
}
