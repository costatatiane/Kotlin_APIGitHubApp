package br.com.fiap.githubapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btSearch.setOnClickListener{
            find()
        }

        loadImage("https://image.flaticon.com/icons/svg/25/25231.svg")
    }

    private fun getOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build();
    }

    private fun find() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()

        val service = retrofit.create(GitHubService::class.java)

        service.findUser(etUsername.text.toString())
            .enqueue(object : Callback<User>{
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    // pois erro 400 cai aqui como resposta, não como failure, por isso verifica se isSuccessful = família dos 200
                    if(response.isSuccessful) {
                        val user = response.body()
                        tvUser.text = user?.name
                        loadImage(user?.avatarUrl?: "")
                    } else {
                        tvUser.text = "Erro ao buscar o usuário"
                    }
                }
            })
    }

    private fun loadImage(url: String) {
        Picasso.get()
            .load(url)
            .into(ivUser)
    }
}
