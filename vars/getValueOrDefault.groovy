def call(String actualValue,String defaultValue) {
    echo "actualValue = " + actualValue
    echo "defaultValue = " + defaultValue
    
    if (!actualValue?.trim()) {
        return defaultValue?.trim()
    }
    return actualValue?.trim()
}
