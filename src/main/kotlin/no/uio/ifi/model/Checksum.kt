package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Checksum(
    @SerializedName("cs_hash")
    var csHash: String?,
    @SerializedName("cs_method")
    var csMethod: String?
)
