package com.fauzify.challangechat

import com.google.gson.annotations.SerializedName

data class MessageDatasetResponse(
    @SerializedName("data")
    var `data`: List<Data> = listOf()
) {
    data class Data(
        @SerializedName("id")
        var id: Int?,
        @SerializedName("body")
        var body: String?,
        @SerializedName("attachment")
        var attachment: String?,
        @SerializedName("timestamp")
        var timestamp: String?,
        @SerializedName("from")
        var from: String?,
        @SerializedName("to")
        var to: String?
    )
}