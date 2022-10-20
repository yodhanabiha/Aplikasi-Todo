package com.example.tryapp.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val FORMAT_DATE = "dd/MM/yyyy"
private const val FORMAT_TIME = "HH:mm"

fun dateTostring(date: Long?) : String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = date!!
    return calendar[Calendar.DAY_OF_MONTH].toString()+"/"+
            (calendar[Calendar.MONTH]+1).toString()+"/"+ calendar[Calendar.YEAR].toString()
}

fun timeTostring(time: Long?) : String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = time!!
    return calendar[Calendar.HOUR_OF_DAY].toString()+":"+calendar[Calendar.MINUTE].toString()
}

fun dateToLong(date: String) : Long {
    val dateFormat = SimpleDateFormat(FORMAT_DATE, Locale.getDefault())
    return  dateFormat.parse(date).time
}

fun timeToLong(time: String) : Long {
    val dateFormat = SimpleDateFormat(FORMAT_TIME, Locale.getDefault())
    return  dateFormat.parse(time).time
}

fun dateToday(): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[Calendar.DAY_OF_MONTH].toString()+"/"+
            (calendar[Calendar.MONTH]+1).toString()+"/"+ calendar[Calendar.YEAR].toString()

}

fun TimeNow(): String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()
    return calendar[Calendar.HOUR_OF_DAY].toString()+":"+calendar[Calendar.MINUTE].toString()
}

fun dateToDialog(context: Context, datePicker: DatePickerDialog.OnDateSetListener?): DatePickerDialog{
    val calendar = Calendar.getInstance()
    return  DatePickerDialog(
        context,
        datePicker,
        calendar[Calendar.YEAR],
        calendar[Calendar.MONTH],
        calendar[Calendar.DAY_OF_MONTH],
    )
}

fun timetoDialog(context: Context, TimePicker: TimePickerDialog.OnTimeSetListener?): TimePickerDialog {
    val cal = Calendar.getInstance()
    return TimePickerDialog(
        context,
        TimePicker,
        cal[Calendar.HOUR_OF_DAY],
        cal[Calendar.MINUTE],
        true,
    )
}

fun dateTostring(year:Int,month:Int,dayofmonth:Int) : String{
    return dayofmonth.toString()+"/"+(month+1)+"/"+year.toString()
}

fun timeTostring(hour:Int,minute:Int) : String{
    return hour.toString()+":"+minute.toString()
}

