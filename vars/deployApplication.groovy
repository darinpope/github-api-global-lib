def call(Map config) {
    echo 'inside deployApplication'
    echo "${config.name}"
    sh 'env > env.txt'
    for (String i : readFile('env.txt').split("\r?\n")) {
        echo i
    }
    echo $GIT_AUTHOR_NAME
    echo $GIT_COMMITTER_NAME
    echo $GIT_AUTHOR_EMAIL
    echo $GIT_COMMITTER_EMAIL
}