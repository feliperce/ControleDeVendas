object Config {
    const val namespace = "br.com.teste.controledevendas"
    const val compileSdk = 33
    const val minSdk = 21
    const val targetSdk = 33
    const val versionCode = 1
    const val versionName = "0.5"

    fun getNameSpaceByModuleName(moduleName: String): String {
        return "${namespace}.$moduleName"
    }
}