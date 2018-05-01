def call(Map config) {
    // you can call any valid step functions from your code, just like you can from Pipeline scripts
    sh "echo Hello world, ${config.name}"
}
