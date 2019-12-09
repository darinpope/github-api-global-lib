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
        rtiEnable: false,
        versionPrecheck: false
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
              env | sort
              mvn -v
              java -version
            """
          }
        }
      }
      stage("Pre-Check: Input Files") {
        when {
          expression { return getPropValue("rtiEnable",props) }
          expression { return getPropValue("rtiFileList",props) != null }
          not {
            expression { return fileExists(getPropValue("rtiFileList",props)) }
          }
        }
        steps {
          error(message:"*** Expecting file ${getPropValue("rtiFileList",props)} ***")
        }
      }
      stage("Pre-Check: Version and Branch") {
        when {
          expression { return getPropValue("versionPrecheck",props) }
          not {
            branch "release"
          }
        }
        steps {
          error(message:"*** can't do this ***")
        }
      }
      stage("Build") {
        stages {
          stage("Build") {
            steps {
              sh "mvn clean"
            }
          }
          stage("RTI") {
            when {
              expression { return getPropValue("rtiEnable",props) }
            }
            steps {
              sh "echo rti"
            }
          }
        }
      }
    }
  }

}