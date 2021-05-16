package com.nha.myprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.nha.myprovider.roomdb.MyDatabase


class MyProvider : ContentProvider() {

    private val authority = "com.nha.myprovider.MyProvider"

    private val ALL_STUDENTS = 1

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        /*
         * The calls to addURI() go here, for all of the content URI patterns that the provider
         * should recognize. For this snippet, only the calls for table 3 are shown.
         */

        /*
         * Sets the integer value for multiple rows in table 3 to 1. Notice that no wildcard is used
         * in the path
         */
        addURI(authority, "Student", ALL_STUDENTS)

        /*
         * Sets the code for a single row to 2. In this case, the "#" wildcard is
         * used. "content://com.example.app.provider/table3/3" matches, but
         * "content://com.example.app.provider/table3 doesn't.
         */
 /*       addURI(authority, "table3/#", 2)*/
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun getType(uri: Uri): String? {
        return when (sUriMatcher.match(uri)) {
            ALL_STUDENTS -> "vnd.android.cursor.dir/vnd.com.nha.myprovider.Student"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var cursor : Cursor? = null
        when (sUriMatcher.match(uri)) {
            ALL_STUDENTS -> context?.apply {
                cursor = MyDatabase.getDatabase(this).studentDao().getAll()
            }
            else -> {
            }
        }
        cursor?.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}