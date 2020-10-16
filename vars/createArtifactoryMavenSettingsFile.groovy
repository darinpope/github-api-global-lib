def call(Map config=[:]) {
    def rawBody = libraryResource 'com/planetpope/maven-settings/artifactory-settings.xml'
    def binding = [
      artifactoryMavenUser : env.ARTIFACTORY_MAVEN_USR,
      artifactoryMavenPassword : env.ARTIFACTORY_MAVEN_PSW,
      artifactoryId : config.id
    ]
    def render = renderTemplate(rawBody,binding)
    writeFile(file:"artifactory-settings.xml", text: render)
}
