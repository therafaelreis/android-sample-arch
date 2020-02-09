package therafaelreis.com.sampleandroidarchcomp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Car(
    val id: String?,
    val name: String?,
    val year: String?,
    val make: String?,
    val milage: String?,
    val imageUrl: String?
): Parcelable