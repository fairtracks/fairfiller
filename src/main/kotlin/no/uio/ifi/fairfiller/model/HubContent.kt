package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class HubContent(
    @SerializedName("experiments")
    var experiments: List<Experiment?>?,
    @SerializedName("samples")
    var samples: List<Sample?>?,
    @SerializedName("@schema")
    var schema: String?,
    @SerializedName("studies")
    var studies: List<Study?>?,
    @SerializedName("tracks")
    var tracks: List<Track?>?
)
