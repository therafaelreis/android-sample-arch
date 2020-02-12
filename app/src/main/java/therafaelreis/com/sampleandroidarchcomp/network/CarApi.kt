package therafaelreis.com.sampleandroidarchcomp.network

import io.reactivex.Single
import retrofit2.http.GET
import therafaelreis.com.sampleandroidarchcomp.model.Car


interface CarApi{
    
    @GET("bins/1b8onw")
    fun retrieveCars() : Single<List<Car>>
}