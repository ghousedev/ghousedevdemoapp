package dev.ghouse.ghousedevdemoapp.data.formdemo

// Class that can hold input data for saving or saved data for displaying
data class FormDemoData(
    var id: Int,
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