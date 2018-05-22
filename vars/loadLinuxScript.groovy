def call(Map config) { 
  def scriptcontents = libraryResource "com/planetpope/scripts/linux/${config.name}"    
  echo $scriptcontent
  writeFile file: "${config.name}", text: scriptcontents 
} 
