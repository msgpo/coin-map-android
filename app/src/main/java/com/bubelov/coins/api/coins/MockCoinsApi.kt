package com.bubelov.coins.api.coins

import com.bubelov.coins.data.Currency
import com.bubelov.coins.data.CurrencyPlace
import com.bubelov.coins.data.Place
import com.bubelov.coins.data.PlaceCategory
import com.bubelov.coins.model.User
import com.bubelov.coins.repository.currency.BuiltInCurrenciesCache
import com.bubelov.coins.repository.currencyplace.BuiltInCurrenciesPlacesCache
import com.bubelov.coins.repository.place.BuiltInPlacesCache
import com.bubelov.coins.repository.placecategory.BuiltInPlaceCategoriesCache
import com.bubelov.coins.repository.synclogs.LogsRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import retrofit2.mock.BehaviorDelegate
import java.util.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MockCoinsApi(
    private val delegate: BehaviorDelegate<CoinsApi>,
    currenciesCache: BuiltInCurrenciesCache,
    placesCache: BuiltInPlacesCache,
    currenciesPlacesCache: BuiltInCurrenciesPlacesCache,
    placeCategoriesCache: BuiltInPlaceCategoriesCache,
    logsRepository: LogsRepository
) : CoinsApi {
    private val currencies = mutableListOf<Currency>()
    private val places = mutableListOf<Place>()
    private val currenciesPlaces = mutableListOf<CurrencyPlace>()
    private val placeCategories = mutableListOf<PlaceCategory>()

    private val date1 = DateTime.parse("2019-03-01T20:10:14+07:00")

    private val user1 = User(
        id = "E5B65104-60FF-4EE4-8A38-36144F479A93",
        email = "foo@bar.com",
        emailConfirmed = false,
        firstName = "Foo",
        lastName = "Bar",
        avatarUrl = "",
        createdAt = date1,
        updatedAt = date1
    )

    private val users = mutableListOf(user1)

    init {
        GlobalScope.launch {
            logsRepository.append("api", "Initializing mock API")
            currencies.addAll(currenciesCache.currencies)
            places.addAll(placesCache.places)
            currenciesPlaces.addAll(currenciesPlacesCache.currenciesPlaces)
            placeCategories.addAll(placeCategoriesCache.placeCategories)
        }
    }

    override suspend fun getToken(authorization: String): TokenResponse {
        return TokenResponse(
            token = UUID.randomUUID().toString(),
            user = user1
        )
    }

    override suspend fun createUser(args: CreateUserArgs): User {
        val user = User(
            id = UUID.randomUUID().toString(),
            email = args.email,
            emailConfirmed = false,
            firstName = args.firstName,
            lastName = args.lastName,
            avatarUrl = "",
            createdAt = DateTime.now(),
            updatedAt = DateTime.now()
        )

        users += user
        return user
    }

    override suspend fun getUser(id: String, authorization: String): User {
        return users.firstOrNull { it.id == id } ?: throw Exception("No user with id: $id")
    }

    override suspend fun getCurrencies(createdOrUpdatedAfter: DateTime): List<Currency> {
        return emptyList()
    }

    override suspend fun getCurrenciesPlaces(createdOrUpdatedAfter: DateTime): List<CurrencyPlace> {
        return emptyList()
    }

    override suspend fun getPlaces(createdOrUpdatedAfter: DateTime): List<Place> {
        return emptyList()
    }

    override suspend fun addPlace(authorization: String, args: CreatePlaceArgs): Place {
        places += args.place
        return args.place
    }

    override suspend fun updatePlace(
        id: String,
        authorization: String,
        args: UpdatePlaceArgs
    ): Place {
        val existingPlace = places.find { it.id == id }
        places.remove(existingPlace)
        places += args.place
        return args.place
    }

    override suspend fun getPlaceCategories(createdOrUpdatedAfter: DateTime): List<PlaceCategory> {
        return emptyList()
    }
}