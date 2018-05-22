def call(Map config) { 
  def scriptcontents = libraryResource "com/planetpope/scripts/linux/${config.name}"    
  echo $scriptcontents
  writeFile file: "${config.name}", text: scriptcontents 
  echo "finished write"
} 
