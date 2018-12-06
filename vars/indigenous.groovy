def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def autoDeploy = getValueOrDefault("${pipelineParams.autoDeploy}","n")
    def runSmokeTests = getValueOrDefault("${pipelineParams.runSmokeTests}","n")
    def notifyEmail = "${pipelineParams.notifyEmail}"

    pipeline {
        agent { label "linux" }
        options {
            buildDiscarder(logRotator(numToKeepStr: '5'))
        }
        stages {
            stage("Prepare Build Environment") {
                steps {
                    sh """
                        echo My branch is: ${BRANCH_NAME}
                        echo My build is: ${BUILD_NUMBER}
                    """
                }
            }
            stage("Build & Unit Tests") {
                steps {
                    sh "echo build and unit tests"
                }
            }

            stage('SonarQube analysis') {
                when {
                    branch 'master'
                }
                steps {
                    sh "echo SonarQube analysis"
                }
            }
            stage("Upload to Binary Repository") {
                steps {
                    sh "echo Upload to Binary Repository"
                }
            }

            stage("Xray Scan") {
                steps {
                    sh "echo Xray Scan"
                }
            }

            stage("Deploy") {
                when {
                    equals expected: "y", actual: "${autoDeploy}"
                }
                parallel {
                    stage("Deploy to Pre-Production") {
                        when {
                            branch 'master'
                        }
                        steps {
                            checkpoint "deployToPreProduction"
                            sh "echo Deploy to Pre-Production"
                        }
                    }

                    stage("Deploy to Dev") {
                        when {
                            not { branch 'master' }
                        }
                        steps {
                            checkpoint "deployToDev"
                            sh "echo Deploy to Dev"
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
                                steps {
                                    checkpoint "Run GUI Smoketest"
                                }
                            }
                            stage("Run GUI Smoketest") {
                                steps {
                                    sh "echo Run GUI Smoketest"
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
                                steps {
                                    checkpoint "Run Service Smoketest"
                                }
                            }
                            stage("Run Service Smoketest") {
                                steps {
                                    sh "echo Run Service Smoketest"
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
                    branch 'master'
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