def call(body) {
  def pipelineParams= [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = pipelineParams
  body()

  pipeline {
    agent {
      docker {
        image "${pipelineParams.agent}"
      }
    }
    stages {
      stage('Build Web') {
        steps {
          dir("${pipelineParams.directory}") {
            sh '''
              mvn --version
              cat web.txt
            '''
          }
        }
      }
    }
  }
}
