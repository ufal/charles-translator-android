package cz.cuni.mff.lindat.api

import cz.cuni.mff.lindat.main.viewmodel.Language
import cz.cuni.mff.lindat.preferences.IUserDataStore
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */


class Api @Inject constructor(
    private val userDataStore: IUserDataStore,
) : IApi {

    private val client = HttpClient(Android) {
        //sometimes causing "Mutex is not locked"
        /* install(Logging) {
             logger = object : Logger {
                 override fun log(message: String) {
                     Log.d("HTTP:", message)
                 }

             }
             level = LogLevel.BODY
         }*/

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
            val logInput = userDataStore.agreeWithDataCollection().first()
            val url = createTranslateUrl(
                inputLanguage = inputLanguage,
                outputLanguage = outputLanguage,
                logInput = logInput
            )

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

    private fun createTranslateUrl(
        inputLanguage: Language,
        outputLanguage: Language,
        logInput: Boolean
    ): String {
        return "$baseUrl/languages?src=${inputLanguage.code}&tgt=${outputLanguage.code}&logInput=$logInput&frontend=$FRONTED"
    }

    private fun parseResponse(rawData: String): String {
        val result = StringBuilder()
        val array = JSONArray(rawData)
        for (i in 0 until array.length()) {
            var text = array.get(i).toString()
            if (i == array.length() - 1) {
                text = removeLastNewLines(text)
            }
            result.append(text)
        }

        return result.toString()
    }

    private fun removeLastNewLines(text: String): String {
        return if (text.endsWith(System.lineSeparator())) {
            text.replace(System.lineSeparator(), "")
        } else {
            text
        }
    }

    companion object {
        const val FRONTED = "AndroidApp"
    }

}