package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Study(
    @SerializedName("contact")
    var contact: Contact?,
    @SerializedName("global_id")
    var globalId: String?,
    @SerializedName("local_id")
    var localId: String?,
    @SerializedName("phenotype")
    var phenotype: Phenotype?,
    @SerializedName("publications")
    var publications: List<String?>?,
    @SerializedName("study_name")
    var studyName: String?
)