def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    echo "${pipelineParams}"
    
    def autoDeploy = getValueOrDefault("${pipelineParams.autoDeploy}","n")
    echo "${autoDeploy}"
    
    def runSmokeTests = getValueOrDefault("${pipelineParams.runSmokeTests}","n")
    echo "${runSmokeTests}"

    def notifyEmail = "${pipelineParams.notifyEmail}"
    echo "${notifyEmail}"
}
