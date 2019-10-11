package no.uio.ifi.model


import com.google.gson.annotations.SerializedName

data class Technique(
    @SerializedName("term_iri")
    var termIri: String?,
    @SerializedName("term_value")
    var termValue: String?
)
