package therafaelreis.com.sampleandroidarchcomp.network

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import therafaelreis.com.sampleandroidarchcomp.model.Car

class CarService {
    private val BASE_URL = "https://api.myjson.com"

    private val api =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
            .create(CarApi::class.java)

    fun retrieveCars(): Single<List<Car>>{
        return api.retrieveCars()
    }
}