package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("name")
    var name: String?,
    @SerializedName("orcid")
    var orcid: String?
)
