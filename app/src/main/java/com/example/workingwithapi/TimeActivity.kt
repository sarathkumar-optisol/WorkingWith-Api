    package com.example.workingwithapi

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.workingwithapi.databinding.ActivityTimeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.*

import java.time.format.DateTimeFormatter
import java.util.*



@AndroidEntryPoint
class TimeActivity : AppCompatActivity() {


   private lateinit var binding : ActivityTimeBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayCurrentTime()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayCurrentTime(){


        var currentDateTime : ZonedDateTime = ZonedDateTime.now()

        var aucklandTime : ZoneId = ZoneId.of("Pacific/Auckland")
        //var asiaTime : ZoneId = ZoneId.of("Asia/Dubai")

        var aucklandDateAndTime : ZonedDateTime = currentDateTime.withZoneSameInstant(aucklandTime)

        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")


        binding.tvCurrentTime.text = formatter.format(aucklandDateAndTime).toDate().formatTo("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone("Pacific/Auckland"))


    }

    fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("Pacific/Auckland")): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone


        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getTimeZone("Pacific/Auckland")): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }
}


//var now = LocalDateTime.now()
//
//var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//binding.tvCurrentTime.text = formatter.format(now)

    //Using Intance
//    var instant : Instant = Instant.now()
//    var zoneId = ZoneId.of("Europe/London")
//    var ztd : ZonedDateTime = instant.atZone(zoneId)
    //binding.tvCurrentTime1.text = formatter.format(ztd)

