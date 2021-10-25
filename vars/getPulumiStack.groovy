def call() {
  def url = env.GIT_URL.split('/')
  echo "url = " + url
  def branch = env.GIT_BRANCH.split('/')
  echo "branch = " + branch
  def repoName = url[4].split('.git')
  echo "repoName = " + repoName
  return url[3] + '/' + repoName[0] + '/' + branch[1]
}