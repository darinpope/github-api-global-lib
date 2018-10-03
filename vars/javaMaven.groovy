def call(body) {

    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    // this found at https://stackoverflow.com/a/49132694
    def agentLabel = null

    node {
        stage('Checkout and set agent'){
            checkout scm
            agentLabel = getProperty(key:"agentLabel",file:"javaMaven.properties")
            echo "found the agentLabel value of: ${agentLabel}"
        }
    }

    pipeline {
        agent { label "$agentLabel"}
        stages {
            stage("echo parameters") {
                steps {
                    sh "env | sort"
                }
            }
            stage("Prepare Build Environment") {
                steps {
                    prepareBuildEnvironment()
                    helloWorld(name: "prepareBuildEnvironment")
                    helloWorldExternal()
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
                    sh "./mvnw clean install"
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
