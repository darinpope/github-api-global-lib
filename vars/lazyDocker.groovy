def call(Map config) {
  def containerId = (env.JOB_NAME+env.BUILD_NUMBER).toLowerCase()
  def workspace = env.WORKSPACE
  sh """
    docker build --tag ${config.imageName} --build-arg FORCE_TESTS=`date +%s` --target testing .
    docker run --name ${containerId} -t -d -u 1000:1000 -w ${workspace} -v ${workspace}:${workspace}:rw,z -v ${workspace}@tmp:${workspace}@tmp:rw,z --entrypoint cat ${config.imageName}
    docker cp ${containerId}:${workspace}/tests build/tests/
    docker stop --time=1 ${containerId}
    docker rm -f ${containerId}
  """
}
