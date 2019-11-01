package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class Phenotype(
    @SerializedName("term_iri")
    var termIri: String?,
    @SerializedName("term_value")
    var termValue: String?
)
