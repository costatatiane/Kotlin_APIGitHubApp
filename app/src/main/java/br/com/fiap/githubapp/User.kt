package br.com.fiap.githubapp

import com.google.gson.annotations.SerializedName

// @SerializedName é o cara da biblioteca Gson de conversão de json pra objeto, onde é possível associar a propriedade da API com a variável do projeto (caso avatar)
// se o nome for o mesmo, não precisa deste tipo de parse (caso name)
data class User (
    val name: String,
    @SerializedName("avatar_url") val avatarUrl: String
)