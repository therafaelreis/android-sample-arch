package therafaelreis.com.sampleandroidarchcomp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.network.CarService

class ListViewModel : ViewModel() {

    val cars = MutableLiveData<List<Car>>()

    //notify when there is an error
    val carsLoadError = MutableLiveData<Boolean>()

    //notify when the system is loading
    val loading = MutableLiveData<Boolean>()

    private val carService = CarService()
    private val disposable = CompositeDisposable()

    fun refresh() {
        fetchRemote()
    }

    private fun fetchRemote() {
        loading.value = true

        disposable.add(
            carService.retrieveCars().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Car>>(){
                    override fun onSuccess(listOfCar: List<Car>) {
                        cars.value = listOfCar
                        carsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        carsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}