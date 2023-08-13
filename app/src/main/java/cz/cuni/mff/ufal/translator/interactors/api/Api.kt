package cz.cuni.mff.ufal.translator.interactors.api

import android.util.Log
import cz.cuni.mff.ufal.translator.BuildConfig
import cz.cuni.mff.ufal.translator.extensions.logE
import cz.cuni.mff.ufal.translator.interactors.api.data.NotImplementedData
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONArray
import java.util.*
import javax.inject.Inject

/**
 * @author Tomas Krabac
 */


class Api @Inject constructor(
    private val userDataStore: IUserDataStore,
) : IApi {

    private val client = HttpClient(Android) {
        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP:", message)
                    }

                }
                level = LogLevel.ALL
            }
        }

        defaultRequest {
            accept(ContentType.Application.Json)
            contentType(ContentType.Application.FormUrlEncoded)
            header("X-Frontend", "android-app")
            header("X-App-Version", BuildConfig.VERSION_NAME)
            header("X-User-Language", Locale.getDefault().language)
        }

        install(HttpRedirect) {
            checkHttpMethod = false
        }
    }

    private val baseUrl = "https://lindat.cz/services/translation/api/v2"

    override suspend fun translate(
        inputLanguage: Language,
        outputLanguage: Language,
        text: String,
        textSource: TextSource
    ): Result<String> {
        return withContext(Dispatchers.IO) {
            val logInput = userDataStore.agreeWithDataCollection.first()
            val organizationName = userDataStore.organizationName.first()

            val url = createTranslateUrl(
                inputLanguage = inputLanguage,
                outputLanguage = outputLanguage,
                logInput = logInput,
                textSource = textSource,
                organizationName = organizationName,
            )

            val data = "input_text=$text"

            try {
                val response: HttpResponse = client.post(url) {
                    setBody(data)
                }

                when (response.status) {
                    HttpStatusCode.OK -> {
                        return@withContext Result.success(parseSuccessResponse(response.body()))
                    }
                    else -> {
                        return@withContext Result.failure(Exception("Bad status - ${response.status} - $response"))
                    }
                }
            } catch (serverEx: ServerResponseException) {
                return@withContext when (serverEx.response.status) {
                    HttpStatusCode.NotImplemented -> {
                        Result.failure(parseNotImplementedResponse(serverEx.response.body()))
                    }
                    else -> {
                        Result.failure(serverEx)
                    }
                }
            } catch (ex: Throwable) {
                return@withContext Result.failure(ex)
            }
        }
    }

    private fun createTranslateUrl(
        inputLanguage: Language,
        outputLanguage: Language,
        logInput: Boolean,
        textSource: TextSource,
        organizationName: String,
    ): String {
        val builder = StringBuilder().apply {
            append("$baseUrl/languages/?")
            append("src=${inputLanguage.code}")
            append("&")
            append("tgt=${outputLanguage.code}")
            append("&")
            append("logInput=$logInput")
            append("&")
            append("inputType=${textSource.key}")

            if (organizationName.isNotBlank()) {
                append("&")
                append("author=${organizationName}")
            }
        }

        return builder.toString()
    }

    private fun parseSuccessResponse(rawData: String): String {
        val result = StringBuilder()
        val array = JSONArray(rawData)

        for (i in 0 until array.length()) {
            var text = array.get(i).toString()
            text = if (i == array.length() - 1) {
                removeLastNewLines(text)
            } else {
                "$text "
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

    private fun parseNotImplementedResponse(rawData: String): UnsupportedApiException {
        var data = NotImplementedData("", "")
        try {
            data = Json.decodeFromString(rawData)
        } catch (throwable: Throwable) {
            logE("error parsing", throwable)
        }

        return UnsupportedApiException(data)
    }


}