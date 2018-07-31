def call(Map config) {
    if(env.BUILD_NUMBER % 2 == 0) {
        return true
    }
    return false
}