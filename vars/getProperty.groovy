def call(Map config) {
    //def props = readProperties(file: "${config.file}")
    Properties props = new Properties()
    File propertiesFile = new File("${config.file}")
    propertiesFile.withInputStream {
        props.load(it)
    }
    return props["${config.key}"]
}