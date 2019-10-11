package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Experiment(
    @SerializedName("aggregated_from")
    var aggregatedFrom: List<Any?>?,
    @SerializedName("compute_protocol_description")
    var computeProtocolDescription: String?,
    @SerializedName("global_id")
    var globalId: String?,
    @SerializedName("lab_protocol_description")
    var labProtocolDescription: String?,
    @SerializedName("local_id")
    var localId: String?,
    @SerializedName("sample_ref")
    var sampleRef: String?,
    @SerializedName("study_ref")
    var studyRef: String?,
    @SerializedName("target")
    var target: Target?,
    @SerializedName("technique")
    var techType: TechType?
)
