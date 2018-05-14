def call(Map config) {
    loadLinuxScript('hello-world.sh')
    sh "./hello-world.sh ${config.name}"
}
