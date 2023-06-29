package com.ds.kotlinflows.data.user_search

import android.content.Context
import com.ds.kotlinflows.domain.user_search.UserItem
import kotlinx.coroutines.delay
import org.json.JSONObject
import kotlin.random.Random

class UserApi(context: Context) {

	private val usersMap = context.assets.open("fake_users.json").bufferedReader().use { it.readText() }.let {
		convertJsonToMap(it)
	}

	private fun convertJsonToMap(jsonString: String): List<UserItem> {
		val usersJson = JSONObject(jsonString)
		val usersArray = usersJson.getJSONArray("results")

		val resultMap = mutableMapOf<String, String>()

		for (i in 0 until usersArray.length()) {
			val userObject = usersArray.getJSONObject(i)
			val userName = userObject.getJSONObject("login").getString("username")
			val userEmail = userObject.getString("email")
			resultMap[userName] = userEmail
		}

		return resultMap.map { UserItem(name = it.key, email = it.value) }
	}

	suspend fun searchQuery(query: String): List<UserItem> {
		if (query.isBlank()) {
			return emptyList()
		}

		val randomServerDelay = Random.nextInt(100, 1_000 + 1).toLong()
		delay(randomServerDelay)

		return usersMap
			.asSequence()
			.filter { it.name.contains(query) || it.email.contains(query) }
			.toList()
	}

}