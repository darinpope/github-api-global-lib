def call(String actualValue,String defaultValue) {  
    if (actualValue != null && actualValue?.trim() != null) {
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
