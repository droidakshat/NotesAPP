package com.example.notesapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentEditBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NotesViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class EditFragment : Fragment(R.layout.fragment_edit),MenuProvider {

 private var editNoteBinding:FragmentEditBinding?=null
    private val binding get() = editNoteBinding!!
    private lateinit var noteViewModel:NotesViewModel
    private lateinit var currentNote: Note
    private val args:EditFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        editNoteBinding= FragmentEditBinding.inflate(inflater,container,false)
         return binding.root
     }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost =requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner)
       // noteViewModel=(activity as MainActivity).NotesViewModel
        noteViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)

        currentNote=args.note!!

        binding.editNoteTitle.setText(currentNote.noteTile)
        binding.editNoteDesc.setText(currentNote.notedesc)
        binding.editNoteFab.setOnClickListener {
            val noteTitle= binding.editNoteTitle.text.toString().trim()
            val notedecs= binding.editNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                val note=Note(currentNote.id, noteTitle,notedecs)
                noteViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context, "Add title..", Toast.LENGTH_SHORT).show()

            }
        }

    }
    private fun deleteNote()
    {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                noteViewModel.deleteNote(currentNote)
                Toast.makeText(context, "note deleted",Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("cancel",null)
        }.create().show()
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
         menu.clear()
        menuInflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu->{
                deleteNote()
                true
            } else-> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editNoteBinding=null
    }

}
