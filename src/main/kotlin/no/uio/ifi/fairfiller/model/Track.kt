package no.uio.ifi.fairfiller.model


import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("checksum")
    var checksum: Checksum?,
    @SerializedName("content_type")
    var contentType: ContentType?,
    @SerializedName("experiment_ref")
    var experimentRef: String?,
    @SerializedName("file_format")
    var fileFormat: FileFormat?,
    @SerializedName("file_iri")
    var fileIri: String?,
    @SerializedName("genome_assembly")
    var genomeAssembly: String?,
    @SerializedName("label_long")
    var labelLong: String?,
    @SerializedName("label_short")
    var labelShort: String?,
    @SerializedName("local_id")
    var localId: String?
)
