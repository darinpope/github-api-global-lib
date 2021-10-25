def call() {
  def url = env.GIT_URL.split('/')
  def branch = env.GIT_BRANCH.split('/')
  def repoName = url[4].split('.')
  return url[3] + '/' + repoName[0] + '/' + branch[1]
}