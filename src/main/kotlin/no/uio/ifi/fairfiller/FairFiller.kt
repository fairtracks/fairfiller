package no.uio.ifi.fairfiller

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.jsoup.Jsoup.connect
import org.jsoup.select.Selector.select
import org.slf4j.LoggerFactory
import java.net.URL

class FairFiller {

    private val log = LoggerFactory.getLogger(javaClass)

    private val ONTOLOGIES: LoadingCache<String, String> =
        Caffeine.newBuilder().maximumSize(Long.MAX_VALUE).build { key -> getOntologyValue(key) }

    fun fill(map: MutableMap<String, Any>) {
        if ((map.containsKey("term_url") || map.containsKey("term_iri")) && !map.containsKey("term_value")) {
            val termURL = if (map.containsKey("term_url")) map["term_url"] else map["term_iri"]
            val termValue = ONTOLOGIES[termURL.toString()]
            if (termValue != null) {
                map["term_value"] = termValue
            }
        }

        if ((map.containsKey("file_url") || map.containsKey("file_iri")) && !map.containsKey("file_name")) {
            val fileURL = if (map.containsKey("file_url")) map["file_url"] else map["file_iri"]
            val fileName = fileURL.toString().substringAfterLast("/")
            map["file_name"] = fileName
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

    fun getOntologyValue(termURL: String): String? {
        return try {
            when {
                termURL.contains("obolibrary") -> getObolibraryValue(termURL)
                termURL.contains("www.ebi.ac.uk") -> getEBIValue(termURL)
                termURL.contains("edamontology") -> getEDAMValue(termURL)
                else -> throw RuntimeException(termURL)
            }
        } catch (e: Exception) {
            log.error(e.message + ": " + termURL)
            null
        }
    }

    fun getObolibraryValue(termURL: String): String? {
        return URL(termURL).readText()
            .substringAfter("<Class rdf:about=\"$termURL\">")
            .substringAfter(">")
            .substringBefore("</rdfs:label>")
    }

    fun getEBIValue(termURL: String): String? {
        return connect(termURL).run {
            get().title()
        }
    }

    fun getEDAMValue(termURL: String): String? {
        return connect(termURL).run {
            select("span.prefLabel", get()).text()
        }
    }

}
