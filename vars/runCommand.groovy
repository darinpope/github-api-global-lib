def call(Map config=[:]) {
  if(isUnix()) {
    sh(script:config.script)
  } else {
    bat(script:config.script)
  }
}
