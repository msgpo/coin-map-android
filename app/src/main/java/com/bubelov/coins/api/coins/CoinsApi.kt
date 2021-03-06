package com.bubelov.coins.api.coins

import com.bubelov.coins.data.Currency
import com.bubelov.coins.data.CurrencyPlace
import com.bubelov.coins.data.Place
import com.bubelov.coins.data.PlaceCategory
import com.bubelov.coins.model.User
import com.bubelov.coins.util.Json
import org.joda.time.DateTime
import retrofit2.http.*

interface CoinsApi {

    @POST("tokens")
    suspend fun getToken(
        @Header("Authorization") authorization: String
    ): TokenResponse

    @POST("users")
    suspend fun createUser(
        @Json @Body args: CreateUserArgs
    ): User

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: String,
        @Header("Authorization") authorization: String
    ): User

    @GET("currencies")
    suspend fun getCurrencies(
        @Query("createdOrUpdatedAfter") createdOrUpdatedAfter: DateTime
    ): List<Currency>

    @GET("currenciesPlaces")
    suspend fun getCurrenciesPlaces(
        @Query("createdOrUpdatedAfter") createdOrUpdatedAfter: DateTime
    ): List<CurrencyPlace>

    @GET("places")
    suspend fun getPlaces(
        @Query("createdOrUpdatedAfter") createdOrUpdatedAfter: DateTime
    ): List<Place>

    @POST("places")
    suspend fun addPlace(
        @Header("Authorization") authorization: String,
        @Json @Body args: CreatePlaceArgs
    ): Place

    @PATCH("places/{id}")
    suspend fun updatePlace(
        @Path("id") id: String,
        @Header("Authorization") authorization: String,
        @Json @Body args: UpdatePlaceArgs
    ): Place

    @GET("placeCategories")
    suspend fun getPlaceCategories(
        @Query("createdOrUpdatedAfter") createdOrUpdatedAfter: DateTime
    ): List<PlaceCategory>
}