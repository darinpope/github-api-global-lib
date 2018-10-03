def call(Map config) {
    def props = readProperties(file: "${config.file}")
    return props["${config.key}"]
}