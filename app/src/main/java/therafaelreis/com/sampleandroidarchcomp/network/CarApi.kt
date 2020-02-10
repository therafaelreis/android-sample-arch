package therafaelreis.com.sampleandroidarchcomp.network

import io.reactivex.Single
import retrofit2.http.GET
import therafaelreis.com.sampleandroidarchcomp.model.Car


interface CarApi{

    @GET("bins/s42go")
    fun retrieveCars() : Single<List<Car>>
}