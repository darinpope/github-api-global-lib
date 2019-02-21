def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def autoDeploy = getValueOrDefault("${pipelineParams.autoDeploy}","n")
    def runSmokeTests = getValueOrDefault("${pipelineParams.runSmokeTests}","n")
    def notifyEmail = "${pipelineParams.notifyEmail}"

    pipeline {
        agent none
        options {
            buildDiscarder(logRotator(numToKeepStr: '5'))
            preserveStashes(buildCount: 5)
            skipDefaultCheckout()
            durabilityHint('PERFORMANCE_OPTIMIZED')
        }
        stages {
            stage("Prepare Build Environment") {
                agent { label "linux" }
                steps {
                    sh """
                        echo My branch is: ${BRANCH_NAME}
                        echo My build is: ${BUILD_NUMBER}
                    """
                }
            }
            stage("big chunk") {
                agent {label "linux"}
                stages {
                    stage("Build & Unit Tests") {
                        steps {
                            checkout scm
                            sh "./mvnw clean test"
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
                    stage("Install") {
                        steps {
                            sh "./mvnw install"
                            stash name: "jar-is-built", includes: "target/*.jar"
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
                }
            }

            stage("Deploy") {
                when {
                    equals expected: "y", actual: "${autoDeploy}"
                }
                parallel {
                    stage("Pre-Production") {
                        stages {
                            stage("Deploy to Pre-Production") {
                                agent { label "linux" }
                                when {
                                    branch 'master'
                                    beforeAgent true
                                }
                                steps {
                                    sh "echo Deploy to Pre-Production"
                                }
                            }
                        }
                    }

                    stage("Dev") {
                        stages {
                            stage("Deploy to Dev") {
                                agent { label "linux" }
                                when {
                                    branch 'master'
                                    beforeAgent true
                                }
                                steps {
                                    sh "echo Deploy to Dev"
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
                            stage("Run GUI Smoketest") {
                                agent { label "linux" }
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
                            stage("Run Service Smoketest") {
                                agent { label "linux" }
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
        }
        post {
            always {
                sendIndigenousEmail(to: "${notifyEmail}")
            }
        }
    }
}
