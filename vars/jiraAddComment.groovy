def call(Map config=[:]) {
  def body = libraryResource "com/planetpope/api/jira/addComment.json"
  def jiraServer = "https://jiraserver"
  sh """
    curl -D- -u $JIRA_CREDENTIALS -X POST --data ${body} -H "Content-Type: application/json" $jiraServer/rest/api/2/issue/${config.issueId}/comment
  """
}
