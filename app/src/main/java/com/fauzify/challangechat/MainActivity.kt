package com.fauzify.challangechat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private var messageList: ArrayList<MessageDatasetResponse.Data?> = ArrayList()
    private lateinit var messageAdapter: MessageAdapter

    private val userId = "A"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadDataJson("message_dataset.json")
        messageList.sortedWith(compareBy({ it?.timestamp }))

        for (obj in messageList) {
            Log.v("hiappSorted", "id ${obj?.id}")
            Log.v("hiappSorted", "timestamp ${obj?.timestamp}")
        }

        rv_message.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        linearLayoutManager.stackFromEnd = true
        rv_message.layoutManager = linearLayoutManager
        messageAdapter = MessageAdapter(this, messageList, userId)
        rv_message.adapter = messageAdapter
    }

    private fun loadDataJson(assetName: String){
        val inputStream = assets.open(assetName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val charset = Charsets.UTF_8
        val json = String(buffer, charset)
        val jsonObject = JSONObject(json)
        val messages = jsonObject.getJSONArray("data")

        for (i in 0 until messages.length()) {
            val jsonObject = messages.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val body = jsonObject.getString("body")
            val attachment = jsonObject.getString("attachment")
            val timestamp = jsonObject.getString("timestamp")
            val from = jsonObject.getString("from")
            val to = jsonObject.getString("to")

            Log.v("hiapp", "id $id")
            Log.v("hiapp", "timestamp $timestamp")

            val item = MessageDatasetResponse.Data(
                    id, body, attachment, timestamp, from, to
            )
            messageList.add(item)

        }
    }
}
