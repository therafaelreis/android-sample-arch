package therafaelreis.com.sampleandroidarchcomp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_car.view.*
import kotlinx.android.synthetic.main.item_car.view.tv_car_name
import kotlinx.android.synthetic.main.item_car.view.tv_year
import therafaelreis.com.sampleandroidarchcomp.R
import therafaelreis.com.sampleandroidarchcomp.model.Car
import therafaelreis.com.sampleandroidarchcomp.util.getProgressDrawable
import therafaelreis.com.sampleandroidarchcomp.util.loadImage

class CarListAdapter(private val cars: ArrayList<Car>): RecyclerView.Adapter<CarListAdapter.CarViewHolder>(){

    fun updateCarList(newCarList: List<Car>){
        cars.clear()
        cars.addAll(newCarList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.itemView.tv_car_name.text = cars[position].name
        holder.itemView.tv_year.text = cars[position].year
        holder.itemView.iv_image_item.loadImage(cars[position].imageUrl, getProgressDrawable(holder.itemView.context))
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionListFragmentToDetailFragment(cars[position]))
        }
    }


    inner class CarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}