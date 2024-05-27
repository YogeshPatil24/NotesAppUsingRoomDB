package com.mvvmmovies.notesappusingroomdb.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mvvmmovies.notesappusingroomdb.R
import com.mvvmmovies.notesappusingroomdb.databinding.ActivityAddNotesBinding
import com.mvvmmovies.notesappusingroomdb.room.entity.Notes
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
class AddNotesActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinder: ActivityAddNotesBinding
    private lateinit var notes: Notes
    private var isOldNotes = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinder = DataBindingUtil.setContentView(this@AddNotesActivity, R.layout.activity_add_notes)
        initUI()
    }

    private fun initUI() {
        mBinder.imageViewSave.setOnClickListener(this@AddNotesActivity)
        setNotesDataForUpdate()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imageView_Save -> {
                saveNotes()
            }
        }
    }

    private fun saveNotes() {
        val title = mBinder.edtTitle.text.toString()
        val note = mBinder.edtNotes.text.toString()

        if (note.isEmpty()) {
            Toast.makeText(this, "Add Some Description", Toast.LENGTH_SHORT).show()
            return
        }
        val simpleDateFormat = SimpleDateFormat("EEE, d MMM  yyyy HH:mm a")
        val date = Date()

        if (!isOldNotes) {
            val notes = Notes()
        }
        notes.title = title
        notes.notes = note
        notes.date = simpleDateFormat.format(date)

        val intent = Intent()
        intent.putExtra("note", notes)
        setResult(
            Activity.RESULT_OK, intent
        )
        finish()
    }

    private fun setNotesDataForUpdate() {
        notes = Notes()
        try {
            notes = intent.getSerializableExtra("old_notes") as Notes
            mBinder.edtTitle.setText(notes.title)
            mBinder.edtNotes.setText(notes.notes)

            isOldNotes = true

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}