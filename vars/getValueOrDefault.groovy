def call(String actualValue,String defaultValue) {  
    boolean containsData = (actualValue?.trim())
    if (containsData) {
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
