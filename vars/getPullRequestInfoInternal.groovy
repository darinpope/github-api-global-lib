def call(Map config=[:]) {
  String url = "https://api.github.com/repos/${config.owner}/${config.repo}/pulls/${config.pullNumber}"
  //return sh(returnStdout: true, script: """curl -H "Authorization: token ${OAUTH-TOKEN}" $url""")
  return sh(returnStdout: true, script: """curl $url""")
}
