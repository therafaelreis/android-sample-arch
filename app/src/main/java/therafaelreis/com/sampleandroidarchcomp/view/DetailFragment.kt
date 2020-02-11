package therafaelreis.com.sampleandroidarchcomp.view


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.tv_car_name
import kotlinx.android.synthetic.main.fragment_detail.tv_year
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_car.*
import therafaelreis.com.sampleandroidarchcomp.R
import therafaelreis.com.sampleandroidarchcomp.databinding.FragmentDetailBinding
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.model.CarPalette
import therafaelreis.com.sampleandroidarchcomp.util.getProgressDrawable
import therafaelreis.com.sampleandroidarchcomp.util.loadImage
import therafaelreis.com.sampleandroidarchcomp.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private var carUuid = 0

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            carUuid = DetailFragmentArgs.fromBundle(it).carUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetchById(carUuid)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.carLiveData.observe(viewLifecycleOwner, Observer { car ->
            binding.car = car
            car.imageUrl?.let{ imageUrl ->
                setupBackgroundColor(imageUrl)
            }
        })
    }

    fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>(){
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate{palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = CarPalette(intColor)
                            binding.pallete = myPalette
                        }
                }

            })
    }
}
