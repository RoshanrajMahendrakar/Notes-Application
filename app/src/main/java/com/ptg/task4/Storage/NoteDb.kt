package com.ptg.task4.Storage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class NoteDb(context:Context):SQLiteOpenHelper(context,"notes",null,1) {

    override fun onCreate(db: SQLiteDatabase?)
    {
        db?.execSQL("CREATE TABLE ${NoteDb} " +
                "(" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$TITLE TEXT, " +
                "$DESC TEXT)")}


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }


    fun addNote( title: String, desc: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(TITLE, title)
        contentValues.put(DESC, desc)
        db.insert(TABLE_NOTES, null, contentValues)
    }

    fun getNote(): List<Note> {
        val noteList = mutableListOf<Note>()

        val db = this.readableDatabase
        val cursor = db.query(TABLE_NOTES, null, null, null, null, null, null, null)
        //val notes = StringBuilder()

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(ID)
            val id=cursor.getInt(idIndex)
            val nameIndex = cursor.getColumnIndex(TITLE)
            val name = cursor.getString(nameIndex)
            val desc = cursor.getColumnIndex(DESC)
            val description = cursor.getString(desc)

            Log.e(TAG, "Id=$id, Title = $name,Description= $description")
            noteList.add(Note(id, name, description))
        }
        cursor.close()
        return noteList
    }

    fun updateNote(id: Int, title: String, description: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
//        contentValues.put(ID,id)
        contentValues.put(TITLE, title)
        contentValues.put(DESC, description)

        val whereClause = "$ID = ?"
        val whereArgs = arrayOf(id.toString())

        Log.d("Database", "Updating note: WHERE $whereClause with args ${whereArgs.joinToString()}")

        val result = database.update(TABLE_NOTES, contentValues, whereClause, whereArgs)
        if (result == 0) {
            Log.e("Database", "Failed to update note with ID: $id")
        } else {
            Log.e("Database", "Note with ID: $id updated successfully.")
        }
    }


    fun deleteNote(id: Int) {
        val database = this.writableDatabase

        // Define the WHERE clause and arguments to specify which note to delete
        val whereClause = "$ID = ?"
        val whereArgs = arrayOf(id.toString())
        Log.d("Database", "Deleting note: WHERE $whereClause with args ${whereArgs.joinToString()}")


        val result = database.delete(TABLE_NOTES, whereClause, whereArgs)
        if (result == 0) {
            Log.e("Database", "Failed to delete note with ID: $id")
        } else {
            Log.d("Database", "Note with ID: $id deleted successfully.")
        }
    }


    companion object
    {
        const val TAG="NoteDb"
        const val TABLE_NOTES="Notes_Table"
        const val ID="ID"
        const val TITLE="TITLE"
        const val DESC="DESCRIPTION"
    }

    data class Note(
        val id:Int,
        var title:String,
        var description:String
    )
}