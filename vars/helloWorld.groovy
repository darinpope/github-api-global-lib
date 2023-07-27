def call(Map config = [:]) {
    sh "echo Hello ${config.name}. Yesterday was ${config.dayOfWeek}."
}

