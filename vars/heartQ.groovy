def call(Map pipelineParams) {

    pipeline {
        agent {
            label "${pipelineParams.agentLabel}"
        }
        stages {
            stage("Prepare Build Environment") {
                steps {
                    prepareBuildEnvironment()
                    helloWorld(name: "prepareBuildEnvironment")
                    helloWorldExternal(name: "prepareBuildEnvironment External")
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
