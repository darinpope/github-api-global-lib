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
       
         stage('Build') {
        
             echo "build step.............."
       
    }
          stage('readfile') {
        
             def filePath = 'files/test.txt'

                    // Use the 'readFile' step to read the file content and store it in a variable
                    def fileContent = readFile(filePath)

                    // Now 'fileContent' contains the content of the file
                    echo "File content: ${fileContent}"
       
    }
    }
}
