def call(String actualValue,String defaultValue) {  
    echo "actualValue = " + actualValue
    echo "defaultValue = " + defaultValue
    
    echo String.valueOf(actualValue)
    
    if (!actualValue) {
        return defaultValue?.trim()
    }
    return actualValue?.trim()
}
