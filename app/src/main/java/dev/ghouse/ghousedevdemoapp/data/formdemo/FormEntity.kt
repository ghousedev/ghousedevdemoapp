package dev.ghouse.ghousedevdemoapp.data.formdemo

import androidx.room.Entity
import androidx.room.PrimaryKey

// Class that can hold input data for saving or saved data for displaying
// Annotated as an entity for use with the Room architecture component
@Entity(tableName = "Forms")
data class FormEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var dropdownSelected: String,
    var inputOne: String,
    var inputTwo: String,
    var inputThree: String,
    var inputFour: String,
    var inputFive: String,
    var checkBox: Boolean,
    var toggle: Boolean
) {
    constructor() : this(0, "", "", "", "", "", "", false, false)
}