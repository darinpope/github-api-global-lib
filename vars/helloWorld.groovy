def call(Map config = [:]) {
    sh "echo Hello ${config.name}. Next tomorrow will be ${config.dayOfWeek}."
}

