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
      stage('Build Frontend') {
        steps {
          dir("${pipelineParams.directory}") {
            sh '''
              npm --version
              node --version
              cat frontend.txt
            '''
          }
        }
      }
    }
  }
}
