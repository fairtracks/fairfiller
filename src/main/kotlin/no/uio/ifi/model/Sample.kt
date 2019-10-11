package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Sample(
    @SerializedName("biomaterial_type")
    var biomaterialType: String?,
    @SerializedName("donor_age")
    var donorAge: String?,
    @SerializedName("donor_ethnicity")
    var donorEthnicity: String?,
    @SerializedName("donor_health_status")
    var donorHealthStatus: String?,
    @SerializedName("donor_id")
    var donorId: String?,
    @SerializedName("donor_sex")
    var donorSex: String?,
    @SerializedName("global_id")
    var globalId: String?,
    @SerializedName("local_id")
    var localId: String?,
    @SerializedName("phenotype")
    var phenotype: String?,
    @SerializedName("sample_type")
    var sampleType: SampleType?,
    @SerializedName("tissue_type")
    var tissueType: String?
)
