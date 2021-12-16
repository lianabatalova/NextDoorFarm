package tech.volkov.nextdoorfarm.frontend.fragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.item_order.view.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.productItemTitle
import tech.volkov.nextdoorfarm.R
import tech.volkov.nextdoorfarm.backend.model.CustomerOrder
import tech.volkov.nextdoorfarm.backend.model.Product

class OrderItemAdapter(
    private val context: Context,
    private val orders: List<CustomerOrder>
) : RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>() {

    companion object {
        val TAG = OrderItemAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_order, viewGroup, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = orders.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val help = orders[position]
        holder.setAppointment(help)
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
//            itemView.addBtn.setOnClickListener {
//                Toasty.info(context, "${itemView.productItemTitle.text} has been added to your order", Toast.LENGTH_SHORT, true).show()
//            }
        }

        fun setAppointment(customerOrder: CustomerOrder) {
            customerOrder.let {
                val productsDescription = it.products
                    .joinToString { p -> p.name + " (" + p.amount + ")" }
                val totalPrice = it.products
                    .map { p -> p.pricePerKg * p.amount }
                    .sum()

                itemView.orderItemStatus.text = customerOrder.status
                itemView.orderItemProducts.text = "Products: $productsDescription"
                itemView.orderItemTotalAmount.text = "Total price: $totalPrice₽"
            }
        }
    }
}