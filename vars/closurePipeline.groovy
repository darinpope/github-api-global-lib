def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent { label "master" }
        stages {
            stage("echo parameters") {
                steps {
                    sh "env | sort"
                }
            }
            stage("Prepare Build Environment") {
                steps {
                  echo 'prepare'
                }
            }
            stage("Source Code Checkout") {
                steps {
                    echo 'scc'
                }
            }
            stage("SonarQube Scan") {
                when {
                    equals expected: "toomuch", actual: pipelineParams?.sonar?.sing
                }
                steps {
                    echo 'scan'
                }
            }
            stage("Build Application") {
                steps {
                    echo 'build'
                }
            }
            stage("Publish Artifacts") {
                steps {
                    echo 'publish'
                }
            }
            stage("Deploy Application") {
                steps {
                    echo 'deploy'
                }
            }
        }
    }
}
