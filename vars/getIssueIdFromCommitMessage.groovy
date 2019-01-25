def call(Map config=[:]) {
  def rawIssueId = sh(returnStdout: true,script: "git log --oneline --format=%B -n 1 $GIT_COMMIT | head -n 1 | cut -d' ' -f1")
  //echo "rawIssueId = ${rawIssueId}"
  def issueId = rawIssueId.replaceAll("[^a-zA-Z0-9-]+","")
  if( issueId ==~ /((?<!([A-Za-z]{1,10})-?)[A-Z]+-\d+)/ ) {
    return issueId
  }
  return
}
