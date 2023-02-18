package com.volokhinaleksey.materialdesign.ui

/**
 * This is class for prioritizing tasks
 *
 * @param priority - Task priority from 1 to 3
 * @param name - Priority name
 */

sealed class Priority(val priority: Int, val name: String) {

    object High : Priority(priority = 3, name = "High")
    object Medium : Priority(priority = 2, name = "Medium")
    object Low : Priority(priority = 1, name = "Low")

}