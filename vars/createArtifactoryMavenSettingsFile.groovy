def call(Map config=[:]) {
    def rawBody = libraryResource 'com/planetpope/maven-settings/artifactory-settings.xml'
    def binding = [
      artifactoryMavenUser : env.ARTIFACTORY_MAVEN_USR,
      artifactoryMavenPassword : env.ARTIFACTORY_MAVEN_PSW
    ]
    println binding
    def render = renderTemplate(rawBody,binding)
    writeFile(file:"artifactory-settings.xml", text: render)
}
