package org.placard.config

object Permissions {
    const val CREATE_PROJECT = "CREATE_PROJECT"
    const val VIEW_PROJECT = "VIEW_PROJECT"
    const val EDIT_PROJECT = "EDIT_PROJECT"
    const val ARCHIVE_PROJECT = "ARCHIVE_PROJECT"

    val allPermissions = listOf(CREATE_PROJECT, VIEW_PROJECT, EDIT_PROJECT, ARCHIVE_PROJECT)
}