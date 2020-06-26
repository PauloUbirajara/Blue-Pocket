package unifor.com.bluepocket.entity

data class Recipe(
    var userId: String = "",
    var recipeId: String = "",
    var name: String = "",
    var recipeType: String = "",
    var occurenceDate: String = "",
    var recipeValue: String = ""
)