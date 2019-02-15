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

    def notifyEmail = getValueOrDefault("${pipelineParams.notifyEmail}","a@a.com")
    echo "${notifyEmail}"
}
