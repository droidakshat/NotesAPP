package com.example.notesapp.fragment.reminder
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notesapp.R
import com.example.notesapp.activites.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar
import java.util.Locale
import kotlin.properties.Delegates

class ReminderFragment : Fragment(R.layout.fragment_reminder) {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationView = view.findViewById(R.id.bn_NoteReminder)
        floatingActionButton=view.findViewById(R.id.addNoteFab)
        alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager




        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.person-> {
                     findNavController().navigate(R.id.action_reminderFragment_to_homeFragment)
                    true
                }
                 else -> false
            }
        }
        floatingActionButton.setOnClickListener {
            showbottomsheetdialog()
        }
        createNotificationchannel()


    }
    private fun showbottomsheetdialog() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(R.layout.bottomsheet_reminder)
        bottomSheetDialog.setCanceledOnTouchOutside(false)

        val reminderclock = bottomSheetDialog.findViewById<ImageButton>(R.id.ib_reminder_clock)
        val closeBottomSheet=bottomSheetDialog.findViewById<ImageView>(R.id.iv_bottomviewClose)
        val savesetalarm=bottomSheetDialog.findViewById<Button>(R.id.alarmButton)


        savesetalarm?.setOnClickListener(){
            val calendar = Calendar.getInstance()
            val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val reminderTitle = bottomSheetDialog.findViewById<EditText>(R.id.editNoteTitle)
            val title = reminderTitle?.text.toString().trim()

            if (title.isNotEmpty()) {
                setAlarm(hourOfDay, minute)
                bottomSheetDialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please add a title to set the alarm", Toast.LENGTH_SHORT).show()
            }
        }
        closeBottomSheet?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        reminderclock?.setOnClickListener {

            showTimePickerDialog()
        }


        val bottomSheet: View? = bottomSheetDialog.findViewById(R.id.bottom_sheet)

        bottomSheet?.let { sheet ->
            val initialTranslation = -sheet.height.toFloat()
            val finalTranslation = 0f

             sheet.translationY = initialTranslation


            sheet.animate()
                .translationY(finalTranslation)
                .setDuration(300)
                .start()
        }

        bottomSheetDialog.show()
    }
    private fun showTimePickerDialog() {
     val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHourOfDay, selectedMinute ->
                 calendar.set(Calendar.HOUR_OF_DAY, selectedHourOfDay)
                calendar.set(Calendar.MINUTE, selectedMinute)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
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
            "Alarm set for ${String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)}",
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