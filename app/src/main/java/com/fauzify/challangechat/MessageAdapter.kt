package com.fauzify.challangechat

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MessageAdapter(
    private val mContext: Context,
    private val mMessages: ArrayList<MessageDatasetResponse.Data?>,
    private val userId: String
) :
    RecyclerView.Adapter<MessageViewHolder>() {

    companion object {

        const val MSG_TYPE_LEFT = 0
        const val MSG_TYPE_RIGHT = 1

        val PESAN_TEKS = "PESAN_TEKS"
        val PESAN_GAMBAR = "PESAN_GAMBAR"
        val PESAN_DOC = "PESAN_DOC"
        val PESAN_KONTAK = "PESAN_KONTAK"
        val UNIDENTIFIED1 = "UNIDENTIFIED1"
        val UNIDENTIFIED2 = "UNIDENTIFIED2"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == MSG_TYPE_RIGHT) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false)
            return MessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false)
            return MessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bindItem(mMessages[position])
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMessages[position]?.from.equals(userId)) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }
}

class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val show_message: TextView = view.findViewById(R.id.show_message)
    val username_chat: TextView = view.findViewById(R.id.username_chat)
    private var groupMessage = ""
    private var pesan = ""

    fun bindItem(data: MessageDatasetResponse.Data?) {
        checkItem(data)
        showMessage(groupMessage, data)
        show_message.text = pesan
        username_chat.text = data?.from
    }

    //Grouping Meesage
    fun checkItem(data: MessageDatasetResponse.Data?) {
        Log.v("cek", data?.body)
        if (data?.body.equals("null")) {
            groupMessage = when (data?.attachment) {
                "image" -> MessageAdapter.PESAN_GAMBAR
                "contact" -> MessageAdapter.PESAN_KONTAK
                "document" -> MessageAdapter.PESAN_DOC
                else -> {
                    MessageAdapter.UNIDENTIFIED1
                }
            }
        } else {
            groupMessage = if (data?.attachment.isNullOrEmpty()) {
                Log.v("cek", data?.attachment)
                MessageAdapter.PESAN_TEKS
            } else {
                when (data?.attachment) {
                    "image" -> MessageAdapter.PESAN_GAMBAR
                    "contact" -> MessageAdapter.PESAN_KONTAK
                    "document" -> MessageAdapter.PESAN_DOC
                    "" -> MessageAdapter.PESAN_TEKS
                    "null" -> MessageAdapter.PESAN_TEKS
                    null -> MessageAdapter.PESAN_TEKS
                    else -> {
                        MessageAdapter.UNIDENTIFIED2
                    }
                }
            }
        }
    }

    //Menampilkan pesan berdasarkan jenis pesan
    fun showMessage(group: String, data: MessageDatasetResponse.Data?) {
        when (group) {
            MessageAdapter.PESAN_GAMBAR ->
                if (data?.body.equals("null")) {
                    pesan = "Pesan Gambar"
                } else {
                    pesan = "Pesan Gambar dan body: ${data?.body}"
                }
            MessageAdapter.PESAN_DOC ->
                if (data?.body.equals("null")) {
                    pesan = "Pesan Dokumen"
                } else {
                    pesan = "Pesan Dokumen dan body: ${data?.body}"
                }
            MessageAdapter.PESAN_KONTAK ->
                if (data?.body.equals("null")) {
                    pesan = "Pesan Kontak"
                } else {
                    pesan = "Pesan Kontak dan body: ${data?.body}"
                }
            MessageAdapter.PESAN_TEKS -> pesan = "Pesan Teks dan body: ${data?.body}"
            MessageAdapter.UNIDENTIFIED2 -> pesan = "Attachment tak dikenal dan body: ${data?.body}"
        }
    }
}