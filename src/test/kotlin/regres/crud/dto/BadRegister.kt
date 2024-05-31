package regres.crud.dto

data class BadRegister(
    val error: String? = null,
) {
    constructor() : this("") {}
}