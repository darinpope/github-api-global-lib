def call(String actualValue,String defaultValue) {
    echo "actualValue = " + actualValue
    echo "defaultValue = " + defaultValue
    
    echo !actualValue?.trim().toString()
    
    if (!actualValue?.trim()) {
        return defaultValue?.trim()
    }
    return actualValue?.trim()
}
