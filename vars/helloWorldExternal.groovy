def call(Map config) {
    loadLinuxScript('hello-world.sh')
    bat "./hello-world.sh ${config.name}"
}
