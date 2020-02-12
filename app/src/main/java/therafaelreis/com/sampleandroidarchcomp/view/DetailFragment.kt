package therafaelreis.com.sampleandroidarchcomp.view


import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.telephony.SmsManager
import android.view.*
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
import therafaelreis.com.sampleandroidarchcomp.databinding.SendSmsDialogBinding
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.model.CarPalette
import therafaelreis.com.sampleandroidarchcomp.model.SMSInfo
import therafaelreis.com.sampleandroidarchcomp.util.getProgressDrawable
import therafaelreis.com.sampleandroidarchcomp.util.loadImage
import therafaelreis.com.sampleandroidarchcomp.viewmodel.DetailViewModel

class DetailFragment : Fragment() {

    private var isSMSStarted = false
    private var carUuid = 0

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: FragmentDetailBinding
    private var currentCar: Car? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

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
            currentCar = car
            car.imageUrl?.let { imageUrl ->
                setupBackgroundColor(imageUrl)
            }
        })
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            val myPalette = CarPalette(intColor)
                            binding.pallete = myPalette
                        }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send_sms -> {
                isSMSStarted = true
                (activity as MainActivity).checkSMSPermission()
            }

            R.id.action_share -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, "Checkout this car!")
                intent.putExtra(Intent.EXTRA_TEXT, "${currentCar?.name} - ${currentCar?.year}")
                intent.putExtra(Intent.EXTRA_STREAM, currentCar?.imageUrl)
                startActivity(Intent.createChooser(intent, "Share with"))

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun onPermissionResult(permissionGranted: Boolean) {
        if (isSMSStarted && permissionGranted) {
            context?.let {
                val smsInfo = SMSInfo(
                    "",
                    "${currentCar?.name} - ${currentCar?.year}",
                    currentCar?.imageUrl
                )
                val dialogBinding = DataBindingUtil.inflate<SendSmsDialogBinding>(
                    LayoutInflater.from(it),
                    R.layout.send_sms_dialog,
                    null,
                    false
                )

                AlertDialog.Builder(it)
                    .setView(dialogBinding.root)
                    .setPositiveButton("Send SMS") { _, _ ->
                        if(!dialogBinding.etSmsTo.text.isNullOrEmpty()){
                            smsInfo.to = dialogBinding.etSmsTo.text.toString()
                            sendSMS(smsInfo)
                        }
                    }
                    .setNegativeButton("Cancel"){_, _ ->}
                    .show()

                dialogBinding.smsInfo = smsInfo
            }
        }
    }

    private fun sendSMS(smsInfo: SMSInfo){
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val sms = SmsManager.getDefault()
        sms.sendTextMessage(smsInfo.to, null, smsInfo.text, pendingIntent, null)
    }
}
