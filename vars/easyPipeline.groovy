def call(body) {
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent {
            label "${pipelineParams.agentLabel}"
        }
        stages {
            stage("echo parameters") {
                steps {
                    echo "${pipelineParams.agentLabel}"
                    echo "${pipelineParams.osConfiguration}"
                    echo "${pipelineParams.osConfiguration.OS_VERSION}"
                    echo "${pipelineParams.osConfiguration.DIR_TYPE}"                    
                }
            }
            stage("Prepare Build Environment") {
                steps {
                    prepareBuildEnvironment()
                    helloWorld(name: "prepareBuildEnvironment")
                    helloWorldExternal()
                }
            }
            stage("Source Code Checkout") {
                steps {
                    echo 'scc'
                }
            }
            stage("SonarQube Scan") {
                when {
                    branch 'master'
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
                    publishArtifacts(name: "publishArtifacts")
                }
            }
            stage("Deploy Application") {
                steps {
                    deployApplication(name: "deployApplication")
                }
            }
        }
        post {
            always {
                sendNotification()
            }
        }
    }
}
