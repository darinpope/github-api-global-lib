def call(String agentLabel,body) {
    
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent none
        stages {
            stage("echo parameters") {
                agent { label "${agentLabel}" }
                steps {
                    sh "env | sort"
                    echo "${agentLabel}"
                    echo "${pipelineParams.osConfiguration}"
                    echo "${pipelineParams.osConfiguration.OS_VERSION}"
                    echo "${pipelineParams.osConfiguration.DIR_TYPE}"                    
                }
            }
            stage("Prepare Build Environment") {
                agent { label "${agentLabel}" }
                steps {
                    prepareBuildEnvironment()
                    helloWorld(name: "prepareBuildEnvironment")
                    helloWorldExternal()
                }
            }
            stage("Source Code Checkout") {
                agent { label "${agentLabel}" }
                steps {
                    echo 'scc'
                }
            }
            stage("SonarQube Scan") {
                agent { label "${agentLabel}" }
                when {
                    branch 'master'
                }
                steps {
                    echo 'scan'
                }
            }
            stage("Build Application") {
                agent { label "${agentLabel}" }
                steps {
                    echo 'build'
                }
            }
            stage("Publish Artifacts") {
                agent { label "${agentLabel}" }
                steps {
                    publishArtifacts(name: "publishArtifacts")
                }
            }
            stage("Deploy Application") {
                agent { label "${agentLabel}" }
                steps {
                    deployApplication(name: "deployApplication")
                }
            }
            //stage("Long Running Stage") {
            //    agent { label "${agentLabel}" }
            //    steps {
            //        script {
            //            hook = registerWebhook()
            //        }
            //    }
            //}
            //stage("Waiting for Webhook") {
            //    steps {
            //        echo "Waiting for POST to ${hook.getURL()}"
            //        script {
            //            data = waitForWebhook hook
            //        }
            //        echo "Webhook called with data: ${data}"
            //    }
            //}         
        }
        //post {
        //    always {
        //        sendNotification()
        //    }
        //}
        post {
            always {
              addSidebarLink()
            }
        }
    }
}
