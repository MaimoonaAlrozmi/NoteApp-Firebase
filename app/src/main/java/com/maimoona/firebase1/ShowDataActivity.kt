package com.maimoona.firebase1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data_show.*
import kotlinx.android.synthetic.main.add_new_note.view.*
import kotlinx.android.synthetic.main.delete_note.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ShowDataActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var mRef: DatabaseReference? = null
    var mNotesList: ArrayList<Note>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_show)

        mAuth = FirebaseAuth.getInstance()
        var dataBase = FirebaseDatabase.getInstance()
        mRef = dataBase.getReference("Notes")
        mNotesList = ArrayList()

        add_new_note.setOnClickListener {
            showDialogAddNotes()
        }

        note_list_View.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                var myNotes: Note = mNotesList?.get(p2)!!
                var title = myNotes.title
                var details = myNotes.details

                var noteIntent = Intent(this@ShowDataActivity, NotesDetailsActivity::class.java)
                noteIntent.putExtra("Title_Key", title)
                noteIntent.putExtra("Note_Key", details)
                startActivity(noteIntent)
            }
        }

        note_list_View.onItemLongClickListener =
            AdapterView.OnItemLongClickListener { p0, p1, p2, p3 ->

                var alertBuilder = AlertDialog.Builder(this)
                var view = layoutInflater.inflate(R.layout.delete_note, null)
                val alertDialog: AlertDialog = alertBuilder.create()
                alertDialog.setView(view)
                alertDialog.show()

                var myNote: Note? = mNotesList?.get(p2)
                view.delete_title.setText(myNote?.title)
                view.delete_details.setText(myNote?.details)

                view.btn_update.setOnClickListener {
                    var afterUpdate = Note(
                        myNote?.id!!, view.delete_title.text.toString()
                        , view.delete_details.text.toString()
                        , getCurrentDate()
                    )

                    mRef?.child(myNote?.id!!)?.setValue(afterUpdate)
                    alertDialog.dismiss()
                }

                view.btn_delete.setOnClickListener {
                    mRef?.child(myNote?.id!!)?.removeValue()
                    alertDialog.dismiss()
                    Toast.makeText(this, "The note deleted successful", Toast.LENGTH_LONG).show()
                }
                false
            }
    }

    override fun onStart() {
        super.onStart()
        mRef?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mNotesList?.clear()
                for (n in snapshot.children) {
                    var note = n.getValue(Note::class.java)
                    mNotesList!!.add(0, note!!)
                }

                val noteAdapter = NoteAdapter(applicationContext, mNotesList!!)
                note_list_View.adapter = noteAdapter
            }
        })

        if (mAuth?.currentUser == null) {
            var intentToAuth = Intent(this, AuthenticationActivity::class.java)
            startActivity(intentToAuth)
        }
    }

    fun showDialogAddNotes() {
        var alertBuilder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_new_note, null)

        alertBuilder.setView(view)
        var alertDialog = alertBuilder.create()
        alertDialog.show()

        view.btn_save.setOnClickListener {

            val title = view.txt_title.text.toString()
            val details = view.txt_details.text.toString()

            if (title.isNotEmpty() && details.isNotEmpty()) {
                var id = mRef!!.push().key

                var myNote = Note(id, title, details, getCurrentDate())
                mRef!!.child(id!!).setValue(myNote)

                Toast.makeText(this, "Note Added Successful", Toast.LENGTH_LONG).show()
                alertDialog.dismiss()

            } else {
                Toast.makeText(this, "empty", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val mdformat = SimpleDateFormat("EEE hh:mm a")
        val strDate = mdformat.format(calender.time)
        return strDate
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id = item?.itemId
        if (id == R.id.logout) {

            FirebaseAuth.getInstance().signOut()
            var intentToAuth = Intent(this, AuthenticationActivity::class.java)
            intentToAuth.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intentToAuth)
            finish()
        }
        return true
    }
}