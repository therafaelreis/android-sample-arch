package therafaelreis.com.sampleandroidarchcomp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import therafaelreis.com.sampleandroidarchcomp.model.Car

@Database(entities = [Car::class], version = 1)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDAO(): CarDAO

    companion object {
        @Volatile
        private var instance: CarDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room
            .databaseBuilder(
                context.applicationContext,
                CarDatabase::class.java,
                "cardatabase"
            )
            .build()
    }
}
