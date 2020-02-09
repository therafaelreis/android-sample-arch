package therafaelreis.com.sampleandroidarchcomp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import therafaelreis.com.sampleandroidarchcomp.model.Car

class ListViewModel : ViewModel() {

    val cars = MutableLiveData<List<Car>>()

    //notify when there is an error
    val carsLoadError = MutableLiveData<Boolean>()

    //notify when the system is loading
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val listOfCars = arrayListOf(
            Car("1", "BMW 428i", "2016", "BMW", "10k", ""),
            Car("2", "BMW M6", "2020", "BMW", "20k", ""),
            Car("3", "BMW 533i", "2018", "BMW", "30k", "")
        )

        cars.value = listOfCars
        carsLoadError.value = false
        loading.value = false
    }

}