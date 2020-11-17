def call(Map config=[:]) {
  def fileContents = libraryResource config.fileName
  def props = readProperties(text:fileContents)
  return props[config.key]
}