def call(def actualValue,def defaultValue) {  
    boolean containsData = (actualValue?.trim()) as boolean
    if (containsData) {
        echo "containsData = ***" + actualValue?.trim() + "***"
        return actualValue?.trim()
    }
    return defaultValue?.trim()
}
