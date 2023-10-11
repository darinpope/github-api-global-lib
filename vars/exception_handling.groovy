 try {
   sh 'success'
 }
catch (Exception e) {
        echo "An error occurred: ${e.getMessage()}"
        currentBuild.result = 'FAILURE'
    } finally {
        // Cleanup or final tasks
        echo "Pipeline completed with result: ${currentBuild.result}"
    }
