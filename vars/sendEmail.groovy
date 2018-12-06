def call(String to, String subject, String message) {
    sh """
        echo ${to}
        echo ${subject}
        echo ${message}
    """
}