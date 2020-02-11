package therafaelreis.com.sampleandroidarchcomp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.launch
import therafaelreis.com.sampleandroidarchcomp.dao.CarDatabase
import therafaelreis.com.sampleandroidarchcomp.model.Car

class DetailViewModel(application: Application) : BaseViewModel(application){

    var carLiveData = MutableLiveData<Car>()

    fun fetchById(uuid: Int){
        launch {
            val car =  CarDatabase(getApplication()).carDAO().carById(uuid)
            carLiveData.value = car
        }

    }
}