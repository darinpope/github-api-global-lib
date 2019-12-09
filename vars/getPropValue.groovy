def call(Map config=[:]) {
  return currentBuild.props["${config.key}"]
}