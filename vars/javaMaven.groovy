def call(body) {

    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    // this found at https://stackoverflow.com/a/49132694
    def agentLabel = null

    node('shared') {
        stage('Checkout and set agent'){
            checkout scm
            agentLabel = getProperty(key:"agentLabel",file:"javaMaven.properties")
        }
    }

    pipeline {
        agent { label "$agentLabel"}
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
    }
}
