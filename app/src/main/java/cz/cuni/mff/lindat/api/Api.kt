package cz.cuni.mff.lindat.api

import android.util.Log
import cz.cuni.mff.lindat.main.viewmodel.Language
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

/**
 * @author Tomas Krabac
 */
class Api : IApi {

    private val client = HttpClient(Android) {
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("HTTP:", message)
                }

            }
            level = LogLevel.BODY
        }

        defaultRequest {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.FormUrlEncoded)
        }

        install(HttpRedirect) {
            checkHttpMethod = false
        }
    }
    private val baseUrl = "https://lindat.cz/translation/api/v2"

    override suspend fun translate(inputLanguage: Language, outputLanguage: Language, text: String): Result<String> {
        return withContext(Dispatchers.IO) {
            val url = createTranslateUrl(inputLanguage, outputLanguage)
            val data = "input_text=$text"

            try {
                val response: HttpResponse = client.post(url) {
                    body = data
                }

                if (response.status == HttpStatusCode.OK) {
                    return@withContext Result.success(parseResponse(response.readText()))
                } else {
                    return@withContext Result.failure(Exception("Bad status - ${response.status} - ${response.readText()}"))
                }
            } catch (ex: Throwable) {
                return@withContext Result.failure(ex)
            }
        }
    }

    private fun createTranslateUrl(inputLanguage: Language, outputLanguage: Language): String{
       return "$baseUrl/languages?src=${inputLanguage.code}&tgt=${outputLanguage.code}&logInput=false&author=u4uAndroidApp"
    }

    private fun parseResponse(rawData: String): String {
        val result = StringBuilder()
        val array = JSONArray(rawData)
        for (i in 0 until array.length()) {
            result.append(array.get(i).toString())
        }

       return result.toString()
    }

}