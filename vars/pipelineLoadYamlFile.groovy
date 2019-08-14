def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent {
            kubernetes {
                label "plyf-agent"
                defaultContainer "jnlp"
                yamlFile "mycontainers.yml"
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
        }
    }
}