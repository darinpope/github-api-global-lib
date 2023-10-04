// mySharedLibrary.groovy
def checkoutGitRepository(String repositoryUrl, String branch, String targetDir) {
    node {
        stage('SCM_Checkout') {
            
           // Checkout the Git repository using the 'checkout' step
   checkout([$class: 'GitSCM',
                branches: [[name: branch]],
                doGenerateSubmoduleConfigurations: false,
                extensions: [[$class: 'CleanBeforeCheckout'], [$class: 'RelativeTargetDirectory', relativeTargetDir: targetDir]],
                userRemoteConfigs: [[url: repositoryUrl]]])
            echo "success"
        }
        stage('Install maven'){
           

        // Use the tool step to install Maven
        def mvnHome = tool name: 'Maven3.8.4', type: 'hudson.tasks.Maven$MavenInstallation'
            sh "${mvnHome}/bin/mvn --version"
        // Print Maven version to verify installation
     
        }
         stage('Build') {
               def projectDir = pwd()
        // Add commands or steps to build your code
        sh "cd ${targetDir}"
        sh "mvn clean install"  // Example: Building Java code with Maven
    }
    }
}
