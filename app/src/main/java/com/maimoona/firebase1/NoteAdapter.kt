package com.maimoona.firebase1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.note_list_item.view.*

class NoteAdapter(context: Context,noteList:ArrayList<Note>): ArrayAdapter<Note>(context,0,noteList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

      val view=  LayoutInflater.from(context).inflate(R.layout.note_list_item,parent,false)
        val note: Note? =getItem(position)
        view.text_title.text=note?.title
       // view.text_details.text=note?.details
        view.text_date.text=note?.date.toString()

        return view;
    }
}