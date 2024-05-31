package regres.crud.dto

data class UserData(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)
{
    constructor() : this(0, "", "", "", "")
}