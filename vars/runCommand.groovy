def call(Map config=[returnStatus:false,returnStdout:false]) {
  if(isUnix()) {
    sh(script:config.script, returnStatus:config.returnStatus, returnStdout:config.returnStdout)
  } else {
    bat(script:config.script, returnStatus:config.returnStatus, returnStdout:config.returnStdout)
  }
}
