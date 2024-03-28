package com.example.notesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteLayoutBinding
import com.example.notesapp.fragment.HomeFragmentDirections
import com.example.notesapp.model.Note

class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){
    class NoteViewHolder(val itemBinding: NoteLayoutBinding):RecyclerView.ViewHolder(itemBinding.root)

   private var onClickListener: OnClickListner? = null

        private  val differCallback=object :DiffUtil.ItemCallback<Note>(){
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                 return oldItem.id==newItem.id &&
                         oldItem.notedesc==newItem.notedesc&&
                         oldItem.noteTile==newItem.noteTile
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                 return oldItem==newItem
            }
        }
        val differ= AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        return NoteViewHolder(
        NoteLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        var currentNote= differ.currentList[position]
        holder.itemBinding.noteTitle.text=currentNote.noteTile
        holder.itemBinding.noteDesc.text=currentNote.notedesc
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
          //  Toast.makeText(this,"pressed",Toast.LENGTH_SHORT).show()
            onClickListener?.deleteNote(currentNote)
            return@OnLongClickListener true
        })



        holder.itemView.setOnClickListener{
            onClickListener?.onNoteClicked(currentNote)
        }
    }
    fun setOnClickListener(onClickListener: OnClickListner) {
        this.onClickListener = onClickListener
    }
    interface OnClickListner{
        fun deleteNote(note: Note)
        fun onNoteClicked(note: Note)
    }

}