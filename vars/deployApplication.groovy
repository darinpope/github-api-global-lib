def call(Map config) {
    echo 'inside deployApplication'
    echo "${config.name}"
    sh 'env > env.txt'
    for (String i : readFile('env.txt').split("\r?\n")) {
        echo i
    }
    echo gitAuthorName()
    echo gitAuthorEmail()
}