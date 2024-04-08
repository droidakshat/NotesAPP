package com.example.notesapp.fragment.reminder
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.Locale

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bn_NoteReminder)
        floatingActionButton=view.findViewById(R.id.addNoteFab)


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
        floatingActionButton.setOnClickListener {
            showbottomsheetdialog()
        }


    }
    private fun showbottomsheetdialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_reminder)

        val reminderclock = bottomSheetDialog.findViewById<ImageButton>(R.id.ib_reminder_clock)

        reminderclock?.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    // Handle selected time here
                    val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
                    // You can now use selectedTime for further processing, e.g., setting an alarm
                    Toast.makeText(requireContext(), "Selected time: $selectedTime", Toast.LENGTH_SHORT).show()
                },
                // Set the current time as the default selection
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true // 24-hour format
            )

            // Show the TimePickerDialog
            timePickerDialog.show()

            bottomSheetDialog.dismiss()
        }

        val bottomSheet: View? = bottomSheetDialog.findViewById(R.id.bottom_sheet)

        bottomSheet?.let { sheet ->
            val initialTranslation = -sheet.height.toFloat() // Initial translation value (offscreen)
            val finalTranslation = 0f // Final translation value (onscreen)

            // Set initial translation
            sheet.translationY = initialTranslation

            // Animate translation to final position
            sheet.animate()
                .translationY(finalTranslation)
                .setDuration(300) // Set duration of animation (adjust as needed)
                .start()
        }

        bottomSheetDialog.show()
    }
    private fun openSystemClockForAlarm() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(intent)
    }
}