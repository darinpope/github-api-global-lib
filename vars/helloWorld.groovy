def call(Map config=[:]) {
    sh "echo Hello world, ${config.name}"
}
