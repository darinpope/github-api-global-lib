def call(body) {
  def pipelineParams = [:]
  body.resolveStrategy = Closure.DELEGATE_FIRST
  body.delegate = pipelineParams
  body()

  def props = [:]

  node {
    stage('Read parameters from javaMavenNexus'){
      checkout scm
      def d = [
        mavenImage: "maven:3.6.2-jdk-8",
        rtiEnable: false
      ]
      props = readProperties(defaults: d, file: 'javaMavenNexus.properties')
    }
  }

  pipeline {
    agent {
      kubernetes {
        label 'mvn-nexus'
        defaultContainer 'jnlp'
        yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: ${getPropValue("mavenImage",props)}
    command:
    - cat
    tty: true
"""
      }
    }
    options {
      disableConcurrentBuilds()
    }
    stages {
      stage("Initialize") {
        steps {
          container("maven") {
            sh """
              env
              mvn -v
              java -version
            """
          }
        }
      }
      stage("Pre-Check of Input Files") {
        when {
          not {
            allOf {
              expression { return getPropValue("rtiEnable",props) }
              expression { return fileExists(getPropValue("rtiFileList",props)) }
            }
          }
        }
        steps {
          error(message:"*** LIST OF FILES FOR RTI NOT FOUND. Expecting file ${getPropValue("rtiFileList",props)} ***")
        }
      }
    }
  }

}