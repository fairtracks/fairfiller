package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Contact(
    @SerializedName("e-mail")
    var eMail: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("orcid_id")
    var orcidId: String?
)
