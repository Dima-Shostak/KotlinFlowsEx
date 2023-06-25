package com.ds.kotlinflows.domain.user_search

data class UserItem(
	val name: String,
	val email: String
) {

	override fun toString(): String {
		return "name: $name, email: $email\n"
	}
}