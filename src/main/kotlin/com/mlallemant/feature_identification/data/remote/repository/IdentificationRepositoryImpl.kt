package com.mlallemant.feature_identification.data.remote.repository

import com.mlallemant.feature_identification.data.remote.api.PlantNetApi
import com.mlallemant.feature_identification.domain.model.IdentificationData
import com.mlallemant.feature_identification.domain.repository.IdentificationRepository
import com.mlallemant.feature_identification.routing.IdentifyPlantRequest

class IdentificationRepositoryImpl(
    private val plantNetApi: PlantNetApi
) : IdentificationRepository {

    companion object {
        const val NUMBER_OF_RESULT = 3
    }

    override suspend fun identifyPlant(identifyPlantRequest: IdentifyPlantRequest): List<IdentificationData>? {
        try {

            val apiResponse = plantNetApi.identify(identifyPlantRequest)
            if (apiResponse == null || apiResponse.results.isNullOrEmpty()) {
                throw Exception("Exception from plantNet Api")
            } else {
                val list: ArrayList<IdentificationData> = arrayListOf()

                repeat(NUMBER_OF_RESULT) { index ->
                    val apiResult = apiResponse.results[index]

                    val scientificName = apiResult.species?.scientificNameWithoutAuthor
                    val commonName =
                        if (apiResult.species?.commonNames.isNullOrEmpty()) "" else apiResult.species?.commonNames?.get(
                            0
                        )
                    val organs = apiResult.images?.get(0)?.organ
                    val url = apiResult.images?.get(0)?.url?.m

                    list.add(
                        IdentificationData(
                            scientificName = scientificName ?: "",
                            commonName = commonName ?: "",
                            score = apiResult.score ?: 0f,
                            organs = organs ?: "",
                            imageUrl = url ?: ""
                        )
                    )
                }
                return list
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}