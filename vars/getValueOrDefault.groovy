def call(String actualValue,String defaultValue) {  
    if (!(actualValue?.trim())) {
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
