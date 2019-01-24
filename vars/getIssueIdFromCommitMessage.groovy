def call(Map config=[:]) {
  def rawIssueId = sh(returnStdout: true,script: "git log --oneline --format=%B -n 1 $GIT_COMMIT | head -n 1 | cut -d' ' -f1")
  return rawIssueId.replaceAll("[^a-zA-Z0-9-]+","")
}
