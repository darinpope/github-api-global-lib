def call(body) {

    def pipelineParams = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    def autoDeploy = getValueOrDefault("${pipelineParams.autoDeploy}","n")
    def runSmokeTests = getValueOrDefault("${pipelineParams.runSmokeTests}","n")
    def notifyEmail = "${pipelineParams.notifyEmail}"

    echo "${autoDeploy}"
    echo "${runSmokeTests}"
    echo "${notifyEmail}"
}
