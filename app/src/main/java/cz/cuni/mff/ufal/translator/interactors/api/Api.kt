package cz.cuni.mff.ufal.translator.interactors.api

import android.util.Log
import cz.cuni.mff.ufal.translator.BuildConfig
import cz.cuni.mff.ufal.translator.interactors.preferences.IUserDataStore
import cz.cuni.mff.ufal.translator.ui.translations.models.Language
import cz.cuni.mff.ufal.translator.ui.translations.models.TextSource
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
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
    private val baseUrl = "https://lindat.cz/translation/api/v2"

    override suspend fun translate(
        inputLanguage: Language,
        outputLanguage: Language,
        text: String,
        textSource: TextSource
    ): Result<String> {
        return withContext(Dispatchers.IO) {
            val logInput = userDataStore.agreeWithDataCollection().first()
            val url = createTranslateUrl(
                inputLanguage = inputLanguage,
                outputLanguage = outputLanguage,
                logInput = logInput,
                textSource = textSource,
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
        logInput: Boolean,
        textSource: TextSource,
    ): String {
        return "$baseUrl/languages/?src=${inputLanguage.code}&tgt=${outputLanguage.code}&logInput=$logInput&inputType=${textSource.api}"
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

}