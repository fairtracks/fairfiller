package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class HubContent(
    @SerializedName("@schema")
    var schema: String?,
    @SerializedName("experiments")
    var experiments: List<Experiment?>?,
    @SerializedName("samples")
    var samples: List<Sample?>?,
    @SerializedName("studies")
    var studies: List<Study?>?,
    @SerializedName("tracks")
    var tracks: List<Track?>?
)
