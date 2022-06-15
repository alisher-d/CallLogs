package com.uznewmax.calllogs

import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.uznewmax.calllogs.databinding.ActivityMainBinding
import java.lang.Long
import java.util.*
import kotlin.String
import kotlin.toString

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var callAdapter: CallAdapter
    private lateinit var binding: ActivityMainBinding
    val maxCountCalls: MutableLiveData<String> = MutableLiveData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        callAdapter = CallAdapter(this)
        recyclerView.adapter = callAdapter
        getCallDetails()
        maxCountCalls.observe(this) {
            supportActionBar?.title = it
        }
    }

    private fun getCallDetails() {
        val calls = mutableListOf<Call>()
//        val title = findViewById<TextView>(R.id.title)
        val sb = StringBuffer()
        val managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null)
        val number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
        sb.append("Call Details :")
        while (managedCursor.moveToNext()) {
            val phNumber = managedCursor.getString(number)
            val callType = managedCursor.getString(type)
            val callDate = managedCursor.getString(date)
            val callDayTime = Date(Long.valueOf(callDate))
            val callDuration = managedCursor.getString(duration)
            var dir: String? = null
            val dircode = callType.toInt()
            when (dircode) {
                CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
                CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
                CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
            }
            val call = Call(phNumber, dir.toString(), callDayTime, callDuration)
//            Log.d("tekshirish", "$call")

            calls.add(call)

            if (calls.size == 2000) {
                Log.d("tekshirish", "${calls.size}")
                callAdapter.setData(calls)

                return
            }
//            sb.append("\nPhone Number:--- $phNumber \nCall Type:--- $dir \nCall Date:--- $callDayTime \nCall duration in sec :--- $callDuration")
//            sb.append("\n----------------------------------")
        }
        managedCursor.close()
//        title.text = sb
    }
}