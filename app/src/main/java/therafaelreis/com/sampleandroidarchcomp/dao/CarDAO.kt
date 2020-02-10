package therafaelreis.com.sampleandroidarchcomp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import therafaelreis.com.sampleandroidarchcomp.model.Car


@Dao
interface CarDAO {

    @Insert
    suspend fun insertAll(vararg  cars: Car): List<Long>

    @Query("SELECT * FROM car")
    suspend fun retrieveAllCars(): List<Car>

    @Query("SELECT * FROM car WHERE uuid = :carId")
    suspend fun carById(carId: Int): Car

    @Query("DELETE FROM car")
    suspend fun deleteAllCar()

}