def call(body) {

  def pipelineParams= [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = pipelineParams
  body()

  pipeline {
    agent {
      label "${pipelineParams.baseAgent}"
    }
    stages {
      stage('Build Frontend') {
        agent { 
          docker { 
            image "${pipelineParams.frontendAgent}"
            reuseNode true
          }
        }
        when {
          changeset "**/frontend/*.*"
          beforeAgent true
        }
        steps {
          dir('frontend') {
            sh '''
              npm --version
              node --version
              cat frontend.txt
            '''
          }
        }
      }
      stage('Build Web') {
        agent { 
          docker { 
            image "${pipelineParams.backendWebAgent}"
            reuseNode true
          }
        }
        when {
          changeset "**/backend/web/*.*"
          beforeAgent true
        }
        steps {
          dir ('backend/web') {
            sh 'mvn --version'
            sh 'cat web.txt'
          }
        }
      }
      stage('Build REST API') {
        agent { 
          docker { 
            image "${pipelineParams.backendApiAgent}"
            reuseNode true
          }
        }
        when {
          changeset "**/backend/api/*.*"
          beforeAgent true
        }
        steps {
          dir ('backend/api') {
            sh 'go version'
            sh 'cat api.txt'
          }
        }
      }
    }
  }
}
