package com.mvvmmovies.notesappusingroomdb.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.mvvmmovies.notesappusingroomdb.R
import com.mvvmmovies.notesappusingroomdb.adapter.NotesListAdapter
import com.mvvmmovies.notesappusingroomdb.databinding.ActivityMainBinding
import com.mvvmmovies.notesappusingroomdb.room.database.RoomDB
import com.mvvmmovies.notesappusingroomdb.room.entity.Notes

class MainActivity : AppCompatActivity(), View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var mBinder: ActivityMainBinding
    private lateinit var notesListAdapter: NotesListAdapter
    var notes: MutableList<Notes> = mutableListOf()
    private lateinit var database: RoomDB
    private var REQUEST_CODE = 101
    var selectedNote = Notes()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
    }

    private fun initUI() {
        mBinder = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        database = RoomDB.getInstance(this@MainActivity)
        notes = database.mainDao().getAll()
        updateRecycler(notes)
        mBinder.btnAddNotes.setOnClickListener(this@MainActivity)
        mBinder.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                return true
            }
        })
    }

    private fun updateRecycler(notes: MutableList<Notes>) {
        mBinder.recyclerMain.setHasFixedSize(true)
        mBinder.recyclerMain.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        notesListAdapter = NotesListAdapter(this, notes, notesClickListener)
        notesListAdapter.notifyDataSetChanged()
        mBinder.recyclerMain.adapter = notesListAdapter
    }

    private val notesClickListener = object : NotesListAdapter.NotesClickListener {
        override fun onClick(notes: Notes) {
            var intent = Intent(this@MainActivity, AddNotesActivity::class.java)
            intent.putExtra("old_notes", notes)
            startActivityForResult(intent, 102)
        }

        override fun onLongClick(notes: Notes, container: CardView) {
//            selectedNote = notes
//            showpopup(container)
        }

        override fun onImageClick(notes: Notes, container: CardView) {
            selectedNote = notes
            showpopup(container)
        }

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnAddNotes -> {
                val intent = Intent(this@MainActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "androidx.appcompat.app.AppCompatActivity"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val notes_new = data!!.getSerializableExtra("note") as Notes
                database.mainDao().insert(notes_new)
                notes.clear()
                notes.addAll(database.mainDao().getAll())
                notesListAdapter.notifyDataSetChanged()
            }
        } else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                val notes_new = data!!.getSerializableExtra("note") as Notes
                database.mainDao().update(notes_new.id, notes_new.title, notes_new.notes)
                notes.clear()
                notes.addAll(database.mainDao().getAll())
                notesListAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun filter(newText: String) {
        var filteredList: MutableList<Notes> = mutableListOf()
        for (singleNote in notes) {
            if (singleNote.title.toLowerCase()
                    .contains(newText.toLowerCase()) || singleNote.notes.toLowerCase()
                    .contains(newText.toLowerCase())
            ) {
                filteredList.add(singleNote)

            } else {

            }
        }
        notesListAdapter.filteredList(filteredList)
    }

    private fun showpopup(cardView: CardView) {
        var popupMenu = PopupMenu(this, cardView)
        popupMenu.setOnMenuItemClickListener(this@MainActivity)
        popupMenu.inflate(R.menu.menu_popup)
        popupMenu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.pin -> {
                if (selectedNote.pinned) {
                    database.mainDao().pin(selectedNote.id, false)
                    Toast.makeText(this@MainActivity, "Unpinned", Toast.LENGTH_SHORT).show()
                } else {
                    database.mainDao().pin(selectedNote.id, true)
                    Toast.makeText(this@MainActivity, "Pinned", Toast.LENGTH_SHORT).show()
                }
                notes.clear()
                notes.addAll(database.mainDao().getAll())
                notesListAdapter.notifyDataSetChanged()
                return true
            }

            R.id.delete -> {
                database.mainDao().delete(selectedNote)
                notes.remove(selectedNote)
                notesListAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Note Deleted!", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> {
                return false
            }
        }
    }
}