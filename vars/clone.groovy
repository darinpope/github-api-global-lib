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
           

       checkout scm
        
        // Use the predefined Maven installation configured in Jenkins
        def mvnHome = tool name: 'Maven3.8.4', type: 'hudson.tasks.Maven$MavenInstallation'

        // Print Maven version to verify the tool is used correctly
        sh "${mvnHome}/bin/mvn --version"
        
        // Perform your Maven build using the predefined Maven installation
        sh "${mvnHome}/bin/mvn clean install"
     
        }
         stage('Build') {
               def projectDir = pwd()
        // Add commands or steps to build your code
        sh "cd ${targetDir}"
        sh "mvn clean install"  // Example: Building Java code with Maven
    }
    }
}
