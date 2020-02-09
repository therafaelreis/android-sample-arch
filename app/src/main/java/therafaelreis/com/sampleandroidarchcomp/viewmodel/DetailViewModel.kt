package therafaelreis.com.sampleandroidarchcomp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import therafaelreis.com.sampleandroidarchcomp.model.Car

class DetailViewModel : ViewModel(){

    val car = MutableLiveData<Car>()
}