def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def autoDeploy = getValueOrDefault("${pipelineParams.autoDeploy}","n")
    def runSmokeTests = getValueOrDefault("${pipelineParams.runSmokeTests}","n")
    def notifyEmail = "${pipelineParams.notifyEmail}"

    pipeline {
        agent {
            kubernetes {
                label 'indigenous-agent'
                defaultContainer 'jnlp'
                yaml """
                    apiVersion: v1
                    kind: Pod
                    spec:
                      containers:
                      - name: maven
                        image: maven:3-ibmjava
                        command:
                            - cat
                        tty: true
                      - name: sonarqube
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                      - name: xray
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                      - name: artifactory 
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                      - name: deployer 
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                      - name: guismoketest 
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                      - name: servicesmoketest 
                        image: gcc:8.1.0
                        command:
                            - cat
                        tty: true
                """
            }
        }
        options {
            buildDiscarder(logRotator(numToKeepStr: '5'))
            durabilityHint('PERFORMANCE_OPTIMIZED')
        }
        stages {
            stage("Prepare Build Environment") {
                steps {
                    container("maven") {
                        sh """
                            echo My branch is: ${BRANCH_NAME}
                            echo My build is: ${BUILD_NUMBER}
                        """
                    }
                }
            }

            stage("Build & Unit Tests") {
                steps {
                    container("maven") {
                        sh "mvn clean compile"
                    }
                }
            }

            stage('SonarQube analysis') {
                when {
                    anyOf {
                        branch 'master'
                        branch 'pod-test'
                    }
                }
                steps {
                    container("sonarqube") {
                        sh "echo SonarQube analysis"
                    }
                }
            }
            stage("Upload to Binary Repository") {
                steps {
                    container("artifactory") {
                        sh "echo Upload to Binary Repository"
                    }
                }
            }

            stage("Xray Scan") {
                steps {
                    container("xray") {
                        sh "echo Xray Scan"
                    }
                }
            }

            stage("Deploy") {
                when {
                    equals expected: "y", actual: "${autoDeploy}"
                }
                parallel {
                    stage("Pre-Production") {
                        stages {
                            stage("Pre-Production checkpoint") {
                                agent none
                                when {
                                    anyOf {
                                        branch 'master'
                                        branch 'pod-test'
                                    }
                                    beforeAgent true
                                }
                                steps {
                                    checkpoint "deployToPreProduction"
                                }
                            }
                            stage("Deploy to Pre-Production") {
                                when {
                                    anyOf {
                                        branch 'master'
                                        branch 'pod-test'
                                    }
                                }
                                steps {
                                    container("deployer") {
                                        sh "echo Deploy to Pre-Production"
                                    }
                                }
                            }
                        }
                    }

                    stage("Dev") {
                        stages {
                            stage("Dev checkpoint") {
                                agent none
                                when {
                                    anyOf {
                                        branch 'master'
                                        branch 'pod-test'
                                    }
                                    beforeAgent true
                                }
                                steps {
                                    checkpoint "deployToDev"
                                }
                            }
                            stage("Deploy to Dev") {
                                when {
                                    anyOf {
                                        branch 'master'
                                        branch 'pod-test'
                                    }
                                }
                                steps {
                                    container("deployer") {
                                        sh "echo Deploy to Dev"
                                    }
                                }
                            }
                        }
                    }
                }
            }

            stage("Smoketests") {
                when {
                    equals expected: "y", actual: "${runSmokeTests}"
                }
                parallel {
                    stage("GUI") {
                        stages {
                            stage("Checkpoint before GUI") {
                                agent none
                                steps {
                                    checkpoint "Run GUI Smoketest"
                                }
                            }
                            stage("Run GUI Smoketest") {
                                steps {
                                    container("guismoketest") {
                                        sh "echo Run GUI Smoketest"
                                    }
                                }
                                post {
                                    failure {
                                        sendIndigenousEmail(to: "${notifyEmail}")
                                    }
                                }
                            }
                        }
                    }
                    stage("Service") {
                        stages {
                            stage("Checkpoint before Service") {
                                agent none
                                steps {
                                    checkpoint "Run Service Smoketest"
                                }
                            }
                            stage("Run Service Smoketest") {
                                steps {
                                    container("servicesmoketest") {
                                        sh "echo Run Service Smoketest"
                                    }
                                }
                                post {
                                    failure {
                                        sendIndigenousEmail(to: "${notifyEmail}")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            stage('Publish deploy event') {
                when {
                    anyOf {
                        branch 'master'
                        branch 'pod-test'
                    }
                }
                steps {
                    publishEvent simpleEvent('indigenousDeploy')
                }
            }
        }
        post {
            success {
                sendIndigenousEmail(to: "${notifyEmail}")
            }
        }
    }
}