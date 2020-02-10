package therafaelreis.com.sampleandroidarchcomp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Car(

    @ColumnInfo(name = "car_id")
    val id: String?,

    @ColumnInfo(name = "car_name")
    val name: String?,

    @ColumnInfo(name = "car_year")
    val year: String?,

    @ColumnInfo(name = "car_make")
    val make: String?,

    @ColumnInfo(name = "car_millage")
    val millage: String?,

    @ColumnInfo(name = "car_url")
    @SerializedName("url")
    val imageUrl: String?
): Parcelable{

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}