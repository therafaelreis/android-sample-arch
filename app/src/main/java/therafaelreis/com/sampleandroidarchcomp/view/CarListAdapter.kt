package therafaelreis.com.sampleandroidarchcomp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_car.view.*
import therafaelreis.com.sampleandroidarchcomp.databinding.ItemCarBinding
import therafaelreis.com.sampleandroidarchcomp.model.Car

class CarListAdapter(private val cars: ArrayList<Car>) :
    RecyclerView.Adapter<CarListAdapter.CarViewHolder>(), CarClickListener {

    fun updateCarList(newCarList: List<Car>) {
        cars.clear()
        cars.addAll(newCarList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemCarBinding.inflate(inflater, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.binding.car = cars[position]
        holder.binding.listener = this
    }

    override fun onCarClicked(v: View) {
        val uuid = v.carId.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.carUuid = uuid
        Navigation.findNavController(v).navigate(action)
    }

    inner class CarViewHolder(@NonNull val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root)
}