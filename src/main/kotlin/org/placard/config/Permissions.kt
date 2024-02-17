package org.placard.config

object Permissions {
    private const val CREATE_PROJECT = "CREATE_PROJECT"
    private const val VIEW_PROJECT = "VIEW_PROJECT"
    private const val EDIT_PROJECT = "EDIT_PROJECT"
    private const val ARCHIVE_PROJECT = "ARCHIVE_PROJECT"

    val allPermissions = listOf(CREATE_PROJECT, VIEW_PROJECT, EDIT_PROJECT, ARCHIVE_PROJECT)
}