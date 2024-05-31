package regres.crud.dto

data class RegRequest(
    val email: String,
    val password: String
) {
    constructor(): this("", "")
}