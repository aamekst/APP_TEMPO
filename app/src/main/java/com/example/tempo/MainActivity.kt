package com.example.tempo

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tempo.constantes.Const
import com.example.tempo.databinding.ActivityMainBinding
import com.example.tempo.model.Main
import com.example.tempo.services.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.trocarTema.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                binding.conteinerPrincipal.setBackgroundColor(Color.parseColor("#171739"))
            }else{
                binding.conteinerPrincipal.setBackgroundColor(Color.parseColor("#AADCF3"))
            }
        }


        binding.btBuscar.setOnClickListener {
            val cidade = binding.editBuscarCidade.text.toString()

            binding.preogressBar.visibility = View.VISIBLE

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .build()
                .create(Api::class.java)

            retrofit.weatherMap(cidade, Const.API_KEY).enqueue(object : Callback<Main>{
                override fun onResponse(p0: Call<Main>, response: Response<Main>) {
                    if(response.isSuccessful){
                        respostaServidor(response)
                    }else{
                        Toast.makeText(applicationContext, "Cidade não encontrada, digite novamente!", Toast.LENGTH_SHORT).show()
                        binding.preogressBar.visibility = View.GONE
                    }

                }

                override fun onFailure(p0: Call<Main>, p1: Throwable) {
                    Toast.makeText(applicationContext, "Erro de servidor", Toast.LENGTH_SHORT).show()
                    binding.preogressBar.visibility = View.GONE
                }


            })

        }
    }

    override fun onResume() {
        super.onResume()

    }
    @SuppressLint("SetTextI18n")
    private fun respostaServidor(response: Response<Main>){
        val main = response.body()!!.main

        val temp     = main.get("temp").toString()
        val tempMin  = main.get("temp_min").toString()
        val tempMax  = main.get("temp_max").toString()
        val humidity = main.get("humidity").toString()

        val sys = response.body()!!.sys
        val country = sys.get("country").asString
        var pais = ""

        val weather = response.body()!!.weather
        val main_weather  = weather[0].main
        val description   = weather[0].description

        val name = response.body()!!.name

        val K_C = 273.15

        val temp_c = (temp.toDouble() - K_C)
        val tempMin_c = (tempMin.toDouble() - K_C)
        val tempMax_c = (tempMax.toDouble() - K_C)
        val decimalFormat = DecimalFormat("00")

        if (country.equals("BR")){
            pais = "Brasil"

        }else if (country.equals("US")){
            pais = "E.U.A"

        }else if (country.equals("GB")){
            pais = "Inglaterra"
        }else if (country.equals("JP")){
            pais = "Japão"
        }else if (country.equals("ES")){
            pais = "Espanha"
        }else if (country.equals("DE")){
            pais = "Alemanha"
        }else if (country.equals("RU")){
            pais = "Russia"
        }else if (country.equals("AR")){
            pais = "Argentina"
        }else if (country.equals("CL")){
            pais = "Chile"
        }else if (country.equals("AU")){
            pais = "Austrália"
        }

        if(main_weather.equals("Clouds") && description.equals("few clouds")){
            binding.imgClima.setBackgroundResource(R.drawable.flewclouds)
        }else if(main_weather.equals("Clouds") && description.equals("scattered clouds")){
            binding.imgClima.setBackgroundResource(R.drawable.clouds)
        }else if(main_weather.equals("Clouds") && description.equals("broken clouds")){
            binding.imgClima.setBackgroundResource(R.drawable.brokenclouds)
        }else if(main_weather.equals("Clouds") && description.equals("overcast clouds")){
            binding.imgClima.setBackgroundResource(R.drawable.brokenclouds)
        }else if(main_weather.equals("Clear") && description.equals("clear sky")){
            binding.imgClima.setBackgroundResource(R.drawable.clearsky)
        }else if(main_weather.equals("Snow") && description.equals("snow")){
            binding.imgClima.setBackgroundResource(R.drawable.snow)
        }else if(main_weather.equals("Rain") && description.equals("rain")){
            binding.imgClima.setBackgroundResource(R.drawable.rain)
        }else if(main_weather.equals("Drizzle")&& description.equals("drizzle")){
            binding.imgClima.setBackgroundResource(R.drawable.drizzle)
        }else if(main_weather.equals("Thunderstorm") && description.equals("thunderstorm")){
            binding.imgClima.setBackgroundResource(R.drawable.trunderstorm)
        }else if(main_weather.equals("Thunderstorm") && description.equals("thunderstorm with rain")){
            binding.imgClima.setBackgroundResource(R.drawable.thunderstormr)
        }else if(main_weather.equals("Rain") && description.equals("moderate rain")){
            binding.imgClima.setBackgroundResource(R.drawable.rain)
        }else if(main_weather.equals("Rain") && description.equals("light rain")){
            binding.imgClima.setBackgroundResource(R.drawable.rain)
        }else if(main_weather.equals("Haze") && description.equals("haze")){
            binding.imgClima.setBackgroundResource(R.drawable.haze)
        }else if(main_weather.equals("Tornado") && description.equals("tornado")){
            binding.imgClima.setBackgroundResource(R.drawable.tornado)
        }else if(main_weather.equals("Clouds") && description.equals("overcast clouds")){
            binding.imgClima.setBackgroundResource(R.drawable.brokenclouds)
        }




        val descricaoClima = when(description){
            "clear sky" -> {
                "Céu Limpo"
            }
            "few clouds" -> {
                "Ensolarado"
            }
            "scattered clouds" -> {
                "Nublado"
            }
            "broken clouds" ->{
                "Parcialmente nublado"
            }
            "snow" ->{
                "Nevando"
            }
            "rain" ->{
                "Chovendo"
            }
            "thunderstorm" ->{
                "Tempestade"
            }
            "drizzle" ->{
                "Garoando"
            }
            "thunderstorm with rain" ->{
                "Tempestade com raio"
            }
            "moderate rain" ->{
                "Chuva moderada"
            }
            "light rain" ->{
                "Chuva Leve"
            }
            "haze" ->{
                "Neblina"
            }
            "overcast clouds" ->{
                "Nuvens nubladas"
            }

            else ->{
                "Carregando"
            }

        }

        binding.txtTemperatura.setText("${decimalFormat.format(temp_c)} ºC")

        binding.txtPaisCidade.setText("$pais - $name")

        binding.txtTituloInfo01.setText("Clima \n $descricaoClima \n\n Umidade \n $humidity %")
        binding.txtTituloInfo02.setText("Temp. Min \n ${decimalFormat.format(tempMin_c)} ºC \n\n Temp. Max \n ${decimalFormat.format(tempMax_c)} ºC ")

        binding.preogressBar.visibility = View.GONE

    }
}