package therafaelreis.com.sampleandroidarchcomp.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import therafaelreis.com.sampleandroidarchcomp.dao.CarDatabase
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.network.CarService
import therafaelreis.com.sampleandroidarchcomp.util.SharedPreferencesHelper

class ListViewModel(application: Application) : BaseViewModel(application) {

    val cars = MutableLiveData<List<Car>>()

    //notify when there is an error
    val carsLoadError = MutableLiveData<Boolean>()

    //notify when the system is loading
    val loading = MutableLiveData<Boolean>()

    private var prefsHelper: SharedPreferencesHelper = SharedPreferencesHelper(getApplication())
    private val carService = CarService()
    private val disposable = CompositeDisposable()
    // 5 = minutes
    // 60 = seconds
    // 1000 = milliseconds
    // 1000 = microseconds
    // 1000 = nano seconds
    // 5 minutes
    private var refreshTimeInNanoSeconds = 5 * 60 * 1000 * 1000 * 1000L

    fun refreshFromCache() {
        val updateTime = prefsHelper.getUpdateTime()
        if (updateTime != null
            && updateTime != 0L
            && System.nanoTime() - updateTime < refreshTimeInNanoSeconds
        ) {
            fetchFromDatabase()
        } else {
            fetchRemote()
        }
    }

    fun refresh(){
        fetchRemote()
    }

    private fun fetchRemote() {
        loading.value = true

        disposable.add(
            carService.retrieveCars().subscribeOn(Schedulers.newThread()).observeOn(
                AndroidSchedulers.mainThread()
            )
                .subscribeWith(object : DisposableSingleObserver<List<Car>>() {
                    override fun onSuccess(cars: List<Car>) {
                        localStorageCar(cars)
                        Toast.makeText(
                            getApplication(),
                            "Car Retrieved from remote",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(e: Throwable) {
                        carsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun retrieveCar(cars: List<Car>) {
        this.cars.value = cars
        carsLoadError.value = false
        loading.value = false
    }

    private fun fetchFromDatabase() {
        loading.value = true

        launch {
            val cars = CarDatabase(getApplication()).carDAO().retrieveAllCars()
            retrieveCar(cars)
            Toast.makeText(getApplication(), "Car Retrieved from database", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun localStorageCar(cars: List<Car>) {
        launch {
            val dao = CarDatabase(getApplication()).carDAO()
            dao.deleteAllCar()

            val result: List<Long> = dao.insertAll(*cars.toTypedArray())
            var i = 0
            while (i < cars.size) {
                cars[i].uuid = result[i].toInt()
                ++i
            }

            retrieveCar(cars)
        }

        prefsHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}