package br.com.fiap.githubapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    // a variável a ser passada no get deve ser a mesma mapeada no path!!
    @GET("/users/{name}")
    fun findUser(@Path("name") userName: String): Call<User>
}