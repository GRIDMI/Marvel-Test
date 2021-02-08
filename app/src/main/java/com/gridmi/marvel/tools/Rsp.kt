package com.gridmi.marvel.tools

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class Rsp(

	@field:SerializedName("copyright")
	val copyright: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("attributionHTML")
	val attributionHTML: String? = null,

	@field:SerializedName("attributionText")
	val attributionText: String? = null,

	@field:SerializedName("etag")
	val etag: String? = null,

	@field:SerializedName("status")
	val status: String? = null

) {
	override fun toString(): String {
		return "Response(copyright=$copyright, code=$code, data=$data, attributionHTML=$attributionHTML, attributionText=$attributionText, etag=$etag, status=$status)"
	}
}

data class Data(

	@field:SerializedName("total")
	val total: String? = null,

	@field:SerializedName("offset")
	val offset: String? = null,

	@field:SerializedName("limit")
	val limit: String? = null,

	@field:SerializedName("count")
	val count: String? = null,

	@field:SerializedName("results")
	val results: List<JsonElement>? = null

) {
	override fun toString(): String {
		return "Data(total=$total, offset=$offset, limit=$limit, count=$count, results=$results)"
	}
	fun <T> getResultsList(tc:Class<T>) : List<T> {
		return (results ?: emptyList()).map {
			API.Companion.Callback.gson.fromJson(it, tc)
		}
	}
}
