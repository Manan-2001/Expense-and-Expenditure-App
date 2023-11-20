package com.example.expensemanager.Model

data class Data (
    var amount: Int = 0,
    var type: String? = null,
    var note: String? = null,
    var id: String? = null,
    var date: String? = null
)

package com.example.expensemanager.Model

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Data(
    var amount: Int = 0,
    var type: String? = null,
    var note: String? = null,
    var id: String? = null,
    var date: String? = null
)

// Extension function to save Data object to Firebase
fun Data.saveToFirebase() {
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val reference: DatabaseReference = database.getReference("expenses")

    val dataId = reference.push().key ?: return // Generate a unique key for the data
    id = dataId // Assign the generated key to the object

    // Create a map of data to be saved
    val dataMap = HashMap<String, Any?>()
    dataMap["amount"] = amount
    dataMap["type"] = type
    dataMap["note"] = note
    dataMap["id"] = id
    dataMap["date"] = date

    // Save data to Firebase under "expenses" node with the generated key
    reference.child(dataId).setValue(dataMap)
}
