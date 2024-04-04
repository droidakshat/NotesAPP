package com.example.notesapp.fragment.reminder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bn_NoteReminder)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.person-> {
                    // Navigate to HomeFragment
                    findNavController().navigate(R.id.action_reminderFragment_to_homeFragment)
                    true
                }
                // Add more cases for other menu items if needed
                else -> false
            }
        }
    }
}