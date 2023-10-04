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
             def mavenTool = 'Maven3.8.4'

        // Use the tool step to install Maven
        tool name: mavenTool, type: 'hudson.tasks.Maven$MavenInstallation'

        // Print Maven version to verify installation
        sh "${tool(name: mavenTool, type: 'hudson.tasks.Maven$MavenInstallation')}/bin/mvn --version"
        }
         stage('Build') {
               def projectDir = pwd()
        // Add commands or steps to build your code
        sh "cd ${targetDir}"
        sh "mvn clean install"  // Example: Building Java code with Maven
    }
    }
}
