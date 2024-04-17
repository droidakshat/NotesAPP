package com.example.notesapp.fragment.reminder
import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
 import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
 import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
 import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
 import com.example.notesapp.R
import com.example.notesapp.activites.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
 import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.Locale

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private val NOTIFICATION_PERMISSION_REQUEST_CODE = 101



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bn_NoteReminder)
        floatingActionButton = view.findViewById(R.id.addNoteFab)
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager




        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.person -> {
                    findNavController().navigate(R.id.action_reminderFragment_to_homeFragment)
                    true
                }

                else -> false
            }
        }
        floatingActionButton.setOnClickListener {
            showbottomsheetdialog()
        }
        checkPermission()


    }
    private fun checkPermission() {
         if (ContextCompat.checkSelfPermission(
                requireContext(),
                  Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
             requestNotificationPermission()
        } else {
             setupReminder()
        }
    }

     private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS,android.Manifest.permission.SCHEDULE_EXACT_ALARM),
            NOTIFICATION_PERMISSION_REQUEST_CODE
        )
    }

     override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    requireContext(),
                    "Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
                 setupReminder()
            } else {
                 Toast.makeText(
                    requireContext(),
                    "Permission denied. Cannot set alarm.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

     private fun setupReminder() {

        createNotificationchannel()

    }

    private fun showbottomsheetdialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_reminder)
        bottomSheetDialog.setCanceledOnTouchOutside(false)

        val reminderclock = bottomSheetDialog.findViewById<ImageButton>(R.id.ib_reminder_clock)
        val closeBottomSheet = bottomSheetDialog.findViewById<ImageView>(R.id.iv_bottomviewClose)
        val savesetalarm = bottomSheetDialog.findViewById<Button>(R.id.alarmButton)
        val reminderTitle=bottomSheetDialog.findViewById<EditText>(R.id.editNoteTitle)
        var hour: Int=0
        var min: Int=0

        savesetalarm?.setOnClickListener {
            if (hour != null && min != null) {
                 if (reminderTitle.isNotBlank()) {
                    setAlarm( hour!!, min!!)
                    bottomSheetDialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Please enter a reminder title", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Please select a time", Toast.LENGTH_SHORT).show()
            }
        }
        closeBottomSheet?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        reminderclock?.setOnClickListener {
            showTimePickerDialog { selectedHour, selectedMinute ->
                hour = selectedHour
                min = selectedMinute
            }
        }


        val bottomSheet: View? = bottomSheetDialog.findViewById(R.id.bottom_sheet)

        bottomSheet?.let { sheet ->
            val initialTranslation =
                -sheet.height.toFloat()
            val finalTranslation = 0f // Final translation value (onscreen)

             sheet.translationY = initialTranslation

             sheet.animate()
                .translationY(finalTranslation)
                .setDuration(300) // Set duration of animation (adjust as needed)
                .start()
        }

        bottomSheetDialog.show()
    }


    private fun showTimePickerDialog(onTimeSetListener: (hourOfDay: Int, minute: Int) -> Unit) {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHourOfDay, selectedMinute ->
                 onTimeSetListener(selectedHourOfDay, selectedMinute)
            },
            hourOfDay,
            minute,
            true
        )

        timePickerDialog.show()
    }
    private fun setAlarm(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)



        }

        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(
            requireContext(),
            "Reminder set for ${String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)}",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun createNotificationchannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val name:CharSequence="akReminderChannel"
            val description="channel for Alarm manager"
            val importance=NotificationManager.IMPORTANCE_HIGH
            val channel=NotificationChannel("ak1",name,importance)
            channel.description=description
            val notificationManager=  requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}

private fun EditText?.isNotBlank(): Boolean {
    return this?.text?.isNotBlank() ?: false
}
