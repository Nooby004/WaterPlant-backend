package com.mlallemant.feature_identification.data.remote.api

import com.mlallemant.feature_identification.data.remote.dto.IdentifyResponse
import com.mlallemant.feature_identification.routing.IdentifyPlantRequest
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*

class PlantNetApiImpl(
    private val client: HttpClient
) : PlantNetApi {

    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val apiKey = appConfig.property("plantnet.api_key").getString()

    override suspend fun identify(identifyPlantRequest: IdentifyPlantRequest): IdentifyResponse? {

        try {
            val response: HttpResponse =
                client.post(PlantNetApi.URL + "?include-related-images=${identifyPlantRequest.includeRelatedPhoto}&no-reject=false&lang=${identifyPlantRequest.language}&api-key=$apiKey") {
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                identifyPlantRequest.images.forEachIndexed { index, image ->
                                    append("images", image.first, Headers.build {
                                        append(HttpHeaders.ContentType, "image/png")
                                        append(HttpHeaders.ContentDisposition, "filename=\"plant$index.png\"")
                                    })

                                    append("organs", image.second)
                                }
                            },
                            boundary = "WebAppBoundary"
                        )
                    )
                    onUpload { bytesSentTotal, contentLength ->
                        println("Sent $bytesSentTotal bytes from $contentLength")
                    }
                }

            return response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }


}