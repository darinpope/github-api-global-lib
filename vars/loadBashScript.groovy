def call(Map config) { 
  def scriptcontents = libraryResource "com/planetpope/bash/${config.name}"               
  writeFile file: "${config.name}", text: scriptcontents 
} 
