package com.maimoona.firebase1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_notes_details.*

class NotesDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_details)

        var title = intent.extras?.getString("Title_Key")
        var details = intent.extras?.getString("Note_Key")

        txt_title_note.text=title
        txt_details_Note.text=details
    }
}