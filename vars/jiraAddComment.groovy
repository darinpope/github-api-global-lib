def renderTemplate(input, binding) {
    def engine = new groovy.text.GStringTemplateEngine()
    def template = engine.createTemplate(input).make(binding)
    return template.toString()
}

def call(Map config=[:]) {
  def issueId = getIssueIdFromCommitMessage()
  if( issueId?.trim() ) {
    return
  }
  def rawBody = libraryResource 'com/planetpope/api/jira/addComment.json'
  def binding = [
    body: "${config.body}"
  ]
  def render = renderTemplate(rawBody,binding)
  def jiraServer = "https://jiraserver"
  sh """
    curl -D- -u $JIRA_CREDENTIALS -X POST --data \"${render}\" -H \"Content-Type: application/json\" $jiraServer/rest/api/2/issue/${issueId}/comment
  """
}
