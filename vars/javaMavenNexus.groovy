def call(body) {
  def props = [:]

  node {
    stage('Read parameters from javaMavenNexus'){
      checkout scm
      def d = [
        mavenImage: "maven:3.6.2-jdk-8",
        buildahImage: "quay.io/buildah/stable:v1.11.4",
        rtiEnable: false,
        versionPrecheck: false,
        deployJobEnable: false,
        emailResults: false,
        nexusScan: false,
        coverityScan: false,
        sonarQubeScan: false
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
    image: ${props["mavenImage"]}
    command:
    - cat
    tty: true
  - name: buildah
    image: ${props["buildahImage"]}
    command:
    - cat
    tty: true
"""
      }
    }
    options {
      disableConcurrentBuilds()
      buildDiscarder(logRotator(numToKeepStr: '1'))
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
          expression { return props["rtiEnable"] }
          expression { return props["rtiFileList"] != null }
          not {
            expression { return fileExists(props["rtiFileList"]) }
          }
        }
        steps {
          error(message:"*** Expecting file ${props["rtiFileList"]} ***")
        }
      }
      stage("Pre-Check: Version and Branch") {
        when {
          expression { return props["versionPrecheck"] }
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
              container("maven") {
                sh "mvn clean"
              }
            }
          }
          stage("RTI") {
            when {
              expression { return props["rtiEnable"] }
            }
            steps {
              sh "echo rti"
            }
          }
          stage("Buildah") {
            steps {
              container("buildah") {
                sh """
                  buildah version
                  buildah unshare --help
                  buildah config --help
                  buildah bud --help
                  buildah bud -t foo:bar .
                  buildah tag --help
                  buildah push --help
                """
              }
            }
          }
        }
      }
    }
  }

}