def call(Map config=[:]) {
  pipeline {
    agent {
      docker {
        image "${pipelineParams.agent}"
      }
    }
    stages {
      stage('Build REST API') {
        steps {
          dir("${pipelineParams.directory}") {
            sh '''
              go version
              cat api.txt
            '''
          }
        }
      }
    }
  }
}
