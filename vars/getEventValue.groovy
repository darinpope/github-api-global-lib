def call(Map config=[:]) {
  return currentBuild.getBuildCauses()[0]["event"]["${config.key}"].toString()
}