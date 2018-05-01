def call(Map config) {
    dir('build-support-scripts') {
        git url: 'https://github.com/darinpope/build-support-scripts.git'
    }
}