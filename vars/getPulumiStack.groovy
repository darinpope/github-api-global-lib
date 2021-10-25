def call() {
  def url = env.GIT_URL.split('/')
  def branch = env.GIT_BRANCH.split('/')
  return url[3] + '/' + url[4] + '/' + branch[1]
}