package no.uio.ifi.fairfiller

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.google.gson.Gson
import no.uio.ifi.fairfiller.model.ContentType
import no.uio.ifi.fairfiller.model.HubContent
import org.jsoup.Jsoup.connect
import org.jsoup.select.Selector.select
import java.io.File
import java.net.URL

class FairFiller {

    private val GSON = Gson()

    private val ONTOLOGIES: LoadingCache<String, String> =
        Caffeine.newBuilder().maximumSize(Long.MAX_VALUE).build { key -> getOntologyValue(key) }

    fun fill(json: String) {
        val blueprint = GSON.fromJson(json, HubContent::class.java)

        print("Processing studies... ")

        for (study in blueprint?.studies.orEmpty()) {
            val phenotype = study?.phenotype
            val termIri = phenotype?.termIri
            val termValue = phenotype?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    phenotype.termValue = value
                    println(value)
                }
            }
        }

        println("Done!")
        print("Processing experiments... ")

        for (experiment in blueprint?.experiments.orEmpty()) {
            val technique = experiment?.technique
            val termIri = technique?.termIri
            val termValue = technique?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    technique.termValue = value
                }
            }
        }

        for (experiment in blueprint?.experiments.orEmpty()) {
            val target = experiment?.target
            val termIri = target?.termIri
            val termValue = target?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    target.termValue = value
                }
            }
        }

        println("Done!")
        print("Processing samples... ")

        for (sample in blueprint?.samples.orEmpty()) {
            val sampleType = sample?.sampleType
            val termIri = sampleType?.termIri
            val termValue = sampleType?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    sampleType.termValue = value
                }
            }
        }

        println("Done!")
        print("Processing tracks... ")

        for (track in blueprint?.tracks.orEmpty()) {
            val fileFormat = track?.fileFormat
            val termIri = fileFormat?.termIri
            val termValue = fileFormat?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    fileFormat.termValue = value
                }
            }
        }

        for (track in blueprint?.tracks.orEmpty()) {
            val contentType = track?.contentType
            val termIri = contentType?.termIri
            val termValue = contentType?.termValue
            if (termValue == null && termIri != null) {
                val value = ONTOLOGIES[termIri]
                if (value != null) {
                    contentType.termValue = value
                }
            }
        }

        for (track in blueprint?.tracks.orEmpty()) {
            if (track?.genomeAssembly.isNullOrEmpty()) {
                track?.genomeAssembly = "GRCh38"
            }
            if (track?.contentType == null) {
                track?.contentType =
                    ContentType("http://edamontology.org/data_3002", "Annotation track")
            }
        }

        println("Done!")

        File("blueprint.json").writeText(GSON.toJson(blueprint))
    }

    fun getOntologyValue(termIri: String): String? {
        return when {
            termIri.contains("obolibrary") -> getObolibraryValue(termIri)
            termIri.contains("www.ebi.ac.uk") -> getEBIValue(termIri)
            termIri.contains("edamontology") -> getEDAMValue(termIri)
            else -> throw RuntimeException(termIri)
        }
    }

    fun getObolibraryValue(termIri: String): String? {
        return URL(termIri).readText()
            .substringAfter("<Class rdf:about=\"$termIri\">")
            .substringAfter(">")
            .substringBefore("</rdfs:label>")
    }

    fun getEBIValue(termIri: String): String? {
        return connect(termIri).run {
            get().title()
        }
    }

    fun getEDAMValue(termIri: String): String? {
        return connect(termIri).run {
            select("span.prefLabel", get()).text()
        }
    }

}
