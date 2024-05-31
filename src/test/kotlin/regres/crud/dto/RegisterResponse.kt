package regres.crud.dto

data class RegisterResponse(
    val id:Int,
    val token:String
) {
    constructor():this(0,"")
}