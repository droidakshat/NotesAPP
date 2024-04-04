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
import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.adapter.NoteAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.fragment.reminder.ReminderFragment
import com.example.notesapp.model.Note
import com.example.notesapp.viewmodel.NotesViewModel


class HomeFragment : Fragment(R.layout.fragment_home),SearchView.OnQueryTextListener,MenuProvider, NoteAdapter.OnClickListner {

    private var homeBinding:FragmentHomeBinding?=null
    private lateinit var noteAdapter: NoteAdapter
    private val binding get() = homeBinding!!
    private lateinit var notesViewModel: NotesViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment\
        homeBinding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner)

    //   notesViewModel=(activity as MainActivity).NotesViewModel
        binding.bnNoteReminder.setOnItemSelectedListener { menuItem->
            when(menuItem.itemId) {
                R.id.person -> {
                   findNavController().navigate(R.id.homeFragment)
                    true
                }
                R.id.home->{
                    findNavController().navigate(R.id.reminderFragment)
                     true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }
        notesViewModel = ViewModelProvider(requireActivity()).get(NotesViewModel::class.java)



        binding.addNoteFab.setOnClickListener{
           it.findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }

        setupHomeRecyclerView()
    }
    private fun updateUI(note:List<Note>?){
        if(note!=null){
            if(note.isNotEmpty()){
                binding.emptyNotesImage.visibility=View.GONE
                binding.homeRecyclerView.visibility=View.VISIBLE
                noteAdapter.differ.submitList(note)
            }
            else{
                binding.emptyNotesImage.visibility=View.VISIBLE
                binding.homeRecyclerView.visibility=View.GONE
            }
        }
    }

    private fun setupHomeRecyclerView(){
        noteAdapter= NoteAdapter()
        noteAdapter.setOnClickListener(this)
        binding.homeRecyclerView.apply {
            layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter=noteAdapter
        }
        activity?.let {
            notesViewModel.getallnotes().observe(viewLifecycleOwner){ note ->

                updateUI(note)
            }
        }
    }
    private fun searchnote(query: String?){
        val searchQuery="%$query"

        notesViewModel.searchNotes(searchQuery).observe(this){list->
            noteAdapter.differ.submitList(list)
        }

    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
         if(newText!=null){
             searchnote(newText)
         }
        val result:Boolean=true
        return result
        //return true
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.homemenu,menu)

        val menuSearch=menu.findItem(R.id.searchMenu).actionView as SearchView
        menuSearch.isSubmitButtonEnabled=false
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean
    {
        return false
    }

    override fun deleteNote(currentNote: Note)
    {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewModel.deleteNote(currentNote)
                Toast.makeText(context, "note deleted", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("cancel",null)
        }.create().show()
    }

    override fun onNoteClicked(note: Note) {
        val direction=HomeFragmentDirections.actionHomeFragmentToEditFragment(note)
        binding.root.findNavController().navigate(direction)
    }

    override fun onDestroy() {
        super.onDestroy()
        homeBinding=null
    }

}