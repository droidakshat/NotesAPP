package com.example.notesapp.fragment

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
import com.example.notesapp.MainActivity

import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddBinding
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NoteViewMOdelfactory
import com.example.notesapp.viewmodel.NotesViewModel

class AddFragment : Fragment(R.layout.fragment_add), MenuProvider {

    private var addNoteBinding: FragmentAddBinding? = null
    private val binding get() = addNoteBinding!!

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var addNoteView: View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addNoteBinding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)


        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner)
//        notesViewModel= (activity as MainActivity).
        addNoteView = view
        binding.editNoteFab.setOnClickListener {
            val noteTitle= binding.addNoteTitle.text.toString().trim()
            val notedecs= binding.addNoteDesc.text.toString().trim()

            if(noteTitle.isNotEmpty()){
                 saveNote(addNoteView)
                view.findNavController().popBackStack(R.id.homeFragment,false)

            }else{
                Toast.makeText(context, "Add title..", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun saveNote(view: View) {
        val notetitle = binding.addNoteTitle.text.toString().trim()
        val notedesc = binding.addNoteDesc.text.toString().trim()

        if (notetitle.isNotEmpty()) {
            val note = Note(0, notetitle, notedesc)
            notesViewModel.addNote(note)

            Toast.makeText(addNoteView.context, "note saved", Toast.LENGTH_SHORT).show()
            view.findNavController().popBackStack(R.id.homeFragment, false)

        } else {
            Toast.makeText(addNoteView.context, "Add title..", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_add_notes, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.saveMenu -> {
                saveNote(addNoteView)
                true
            }

            else ->
                false

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        addNoteBinding = null
    }
}
