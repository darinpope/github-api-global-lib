def call(Map config=[:]) {
  def curl = 'curl -H "Content-Type: application/json" -H "Authorization: Bearer ' + env.CBFM_USER_TOKEN + '" https://x-api.rollout.io/public-api/applications/'+config.applicationId+'/'+config.environment+'/flags/'+config.flagName+"| jq 'if .enabled == true then .value else empty end'"
  
  def flagValue = sh(returnStdout:true,script:curl).trim()
  return flagValue;
}