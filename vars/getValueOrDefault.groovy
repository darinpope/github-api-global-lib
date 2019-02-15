def call(String actualValue,String defaultValue) {  
    echo "actualValue = " + actualValue
    echo "defaultValue = " + defaultValue
    
    echo String.valueOf(actualValue?.trim())
    
    if (actualValue?.trim()) {
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
