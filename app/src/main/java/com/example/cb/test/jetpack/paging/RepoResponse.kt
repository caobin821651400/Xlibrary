package com.example.cb.test.jetpack.paging

import com.google.gson.annotations.SerializedName

  class RepoResponse {

    @SerializedName("items")
    val items: List<Repo> = emptyList()


    data class Repo(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,
            @SerializedName("description") val description: String?,
            @SerializedName("stargazers_count") val starCount: Int
    )

}