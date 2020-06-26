package unifor.com.bluepocket.entity

data class Expense(
    var userId: String = "",
    var expenseId: String = "",
    var name: String = "",
    var expenseType: String = "",
    var occurenceDate: String = "",
    var expenseValue: String = ""
)