package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class Checksum(
    @SerializedName("cs_hash")
    var csHash: String?,
    @SerializedName("cs_method")
    var csMethod: String?
)
