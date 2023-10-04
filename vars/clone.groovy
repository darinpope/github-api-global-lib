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
           

 
     // Define the desired Maven version
        def mavenVersion = '3.8.4'

        // Define the URL for downloading Maven
        def mavenUrl = "https://downloads.apache.org/maven/maven-3/${mavenVersion}/binaries/apache-maven-${mavenVersion}-bin.zip"

        // Define the installation directory
        def mavenHome = tool name: 'maven', type: 'ToolInstallation'

     
        }
         stage('Build') {
               def projectDir = pwd()
        // Add commands or steps to build your code
        sh "cd ${targetDir}"
        sh "mvn clean install"  // Example: Building Java code with Maven
    }
    }
}
