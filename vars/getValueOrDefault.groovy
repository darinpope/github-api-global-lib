def call(String actualValue,String defaultValue) {  
    def trimmedValue = actualValue?.trim()
    if("null".equals(trimmedValue)) {
        trimmedValue = null
    }
    boolean containsData = (trimmedValue) as boolean
    if (containsData) {
        echo "containsData = ***" + trimmedValue + "***"
        return trimmedValue
    }
    return defaultValue?.trim()
}
