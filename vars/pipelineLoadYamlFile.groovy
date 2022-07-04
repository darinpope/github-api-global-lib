def call(Map pipelineParams=[:]) {

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
                            mvn --version
                        """
                    }
                }
            }
        }
    }
}
