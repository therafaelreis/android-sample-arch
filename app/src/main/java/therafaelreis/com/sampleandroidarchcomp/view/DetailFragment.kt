package therafaelreis.com.sampleandroidarchcomp.view


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.tv_car_name
import kotlinx.android.synthetic.main.fragment_detail.tv_year
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_car.*
import therafaelreis.com.sampleandroidarchcomp.R
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.viewmodel.DetailViewModel

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var carUIID = 0

    private lateinit var viewModel: DetailViewModel
    private lateinit var car : Car

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            carUIID = DetailFragmentArgs.fromBundle(it).carUIID
         }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel(){
        arguments?.let {
            car = DetailFragmentArgs.fromBundle(it).car
        }

        tv_car_name.text  = car.name
        tv_year.text = car.year
        tv_make.text = car.make
        tv_millage.text = car.milage
    }
}
