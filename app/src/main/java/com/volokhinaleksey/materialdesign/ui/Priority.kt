package com.volokhinaleksey.materialdesign.ui

sealed class Priority(val priority: Int, val name: String) {

    object High : Priority(priority = 3, name = "High")
    object Medium : Priority(priority = 2, name = "Medium")
    object Low : Priority(priority = 1, name = "Low")

}