package com.jzamit.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import timber.log.Timber
import java.time.LocalDate
import java.time.Period
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.plant(Timber.DebugTree());

        val btnDatePicker: Button = findViewById(R.id.btnSelectDate)
        btnDatePicker.setOnClickListener{
            clickDatePicker()

        }
    }

     private fun clickDatePicker() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog ( this,
            { _, year1, month1, day1 ->
                Timber.d("Some log to test: $year1 $month1 $day1")
                if (year1 != null) {
                    addSelectedText()

                }

                val selDate = Date(year1,month1,day1)
                val curDate = Date(year,month,day)
                val age = getAge(selDate, curDate)
                addBirthDateToLabel("$day1 / ${month1 +1 } / $year1")
                addAgeToLabel("You have $age years old")
            },
            year,
            month,
            day)


         calendar.add(Calendar.DAY_OF_MONTH, +1)
         dpd.datePicker.maxDate = calendar.timeInMillis
         dpd.show()
    }

    private fun addAgeToLabel(text:String) {
        val labelAge: TextView = findViewById(R.id.label_age)
        labelAge.text = text

    }
    private fun addBirthDateToLabel(text:String) {
        val labelBirthDate: TextView = findViewById(R.id.label_birthdate)
        labelBirthDate.text = text

    }
    private fun addSelectedText() {
        val labelSelected: TextView = findViewById(R.id.label_selected)
        labelSelected.text = "Selected date"

    }

    private fun getAge(selectedDate: Date, currentDate:Date): Int {
        val (selYear, selMonth, selDay) = selectedDate
        val (currYear, currMonth, currDay) = currentDate


        var age = currYear - selYear
        if (selMonth > currMonth || (selMonth == currMonth && selDay > currDay)) {
           age--
        }
        return age
    }
}


data class Date(val year: Int, val month: Int, val day: Int)
