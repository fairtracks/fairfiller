package no.uio.ifi.fairfiller

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.jsoup.Jsoup.connect
import org.jsoup.select.Selector.select
import java.net.URL

class FairFiller {

    private val ONTOLOGIES: LoadingCache<String, String> =
        Caffeine.newBuilder().maximumSize(Long.MAX_VALUE).build { key -> getOntologyValue(key) }

    fun fill(map: MutableMap<String, Any>) {
//        if (map.containsKey("file_name") && map.containsKey("checksum")) { //track
//            if (!map.containsKey("genome_assembly")) {
//                map["genome_assembly"] = "GRCh38"
//            }
//            if (!map.containsKey("content_type")) {
//                map["content_type"] = mapOf(
//                    "term_url" to "http://edamontology.org/data_3002",
//                    "term_value" to "Annotation track"
//                )
//            }
//        }

        if (map.containsKey("term_url") && !map.containsKey("term_value")) {
            val termURL = map["term_url"]
            val termValue = ONTOLOGIES[termURL.toString()]
            if (termValue != null) {
                map["term_value"] = termValue
            }
            return
        }

        for ((_, value) in map) {
            if (value is MutableMap<*, *>) {
                fill(value as MutableMap<String, Any>)
            } else if (value is Collection<*>) {
                for (any in value) {
                    if (any is MutableMap<*, *>) {
                        fill(any as MutableMap<String, Any>)
                    }
                }
            }
        }
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
