def call(String actualValue,String defaultValue) {  
    boolean containsData = (actualValue?.trim())
    if (containsData) {
        echo "containsData = ***" + actualValue?.trim() + "***"
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
