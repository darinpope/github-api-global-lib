def call(Map config=[:]) {
  checkout scm
  sh "env | sort"
}
