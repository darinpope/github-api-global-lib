def call(Map config=[:]) {
  def flagValue = sh(returnStdout:true,script:"""
  https --print 'b' --pretty none GET x-api.rollout.io/public-api/applications/${config.applicationId}/${config.environment}/flags/${config.flagName} 'authorization: Bearer ${env.CBFM_USER_TOKEN}'  | jq 'if .enabled == true then .value else empty end'
  """).trim()
  return flagValue;
}