package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Sample(
    @SerializedName("biomaterial_type")
    var biomaterialType: String?,
    @SerializedName("global_id")
    var globalId: String?,
    @SerializedName("local_id")
    var localId: String?,
    @SerializedName("sample_type")
    var sampleType: SampleType?
)
