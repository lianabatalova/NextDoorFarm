package tech.volkov.nextdoorfarm.frontend.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.item_product.view.*
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.Product

class ProductItemAdapter(
    private val context: Context,
    private val products: List<Product>
) : RecyclerView.Adapter<ProductItemAdapter.MyViewHolder>() {

    companion object {
        val TAG = ProductItemAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val help = products[position]
        holder.setAppointment(help)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.addBtn.setOnClickListener {
                Toasty.info(context, "${itemView.productItemTitle.text} has been added to your order", Toast.LENGTH_SHORT, true).show()
            }

            itemView.removeBtn.setOnClickListener {
                Toasty.warning(context, "${itemView.productItemTitle.text} has been removed from your order", Toast.LENGTH_SHORT, true).show()
            }
        }

        fun setAppointment(product: Product) {
            setOnClickListenerBy(product)
            product.let {
                itemView.productItemTitle.text = it.name
                itemView.productItemDescription.text = "Description: " + it.description
                itemView.productItemPricePerKg.text = "Price per kg: " + it.pricePerKg.toString()
                itemView.productItemAmount.text = "Amount: " + it.amount.toString()
            }
        }

        private fun setOnClickListenerBy(Product: Product) {
//            val appointmentDetailsFragment = AppointmentDetailsFragment()
//            appointmentDetailsFragment.arguments = Bundle().also {
//                it.putString("title", appointment.Title)
//                it.putString("place", appointment.Place)
//                it.putString("date", appointment.Time)
//                it.putString("comment", appointment.Comment)
//                it.putString("status", appointment.Status)
//            }
//
//            itemView.appointmentItemLayout.setOnClickListener {
//                (context as FragmentActivity)
//                    .supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.mainContainer, appointmentDetailsFragment)
//                    .commit()
//            }
        }
    }
}