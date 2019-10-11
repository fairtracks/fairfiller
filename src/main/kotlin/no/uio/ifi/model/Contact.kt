package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("name")
    var name: String?,
    @SerializedName("orcid")
    var orcid: String?
)
