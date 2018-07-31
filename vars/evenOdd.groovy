def call(Map config) {
    if(env.BUILD_NUMBER.toInteger() % 2 == 0) {
        return true
    }
    return false
}