package therafaelreis.com.sampleandroidarchcomp.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import therafaelreis.com.sampleandroidarchcomp.R
import therafaelreis.com.sampleandroidarchcomp.viewmodel.ListViewModel

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var viewModel: ListViewModel
    private val carListAdapter = CarListAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refreshFromCache()

        view.rv_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = carListAdapter
        }

        refresh_layout.setOnRefreshListener {
            rv_list.visibility = View.GONE
            tv_error.visibility = View.GONE
            pb_loading.visibility = View.VISIBLE
            viewModel.refresh()
            refresh_layout.isRefreshing = false
        }


        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.cars.observe(viewLifecycleOwner, Observer { cars ->
            rv_list.visibility = View.VISIBLE
            carListAdapter.updateCarList(cars)
        })

        viewModel.carsLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                tv_error.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                pb_loading.visibility = if (it) View.VISIBLE else View.GONE

                if (it) {
                    tv_error.visibility = View.GONE
                    rv_list.visibility = View.GONE
                }
            }
        })
    }

}
