def call(Map config = [:]) {
    sh "echo Hello ${config.name}. Tomorrow is ${config.dayOfWeek}."
}

