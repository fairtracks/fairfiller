package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class Study(
    @SerializedName("contact")
    var contact: Contact?,
    @SerializedName("global_id")
    var globalId: String?,
    @SerializedName("local_id")
    var localId: String?,
    @SerializedName("phenotype")
    var phenotype: Phenotype?
)
