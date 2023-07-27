def call(Map config = [:]) {
    sh "echo Hello ${config.name}. Next tomorrow is ${config.dayOfWeek}."
}

