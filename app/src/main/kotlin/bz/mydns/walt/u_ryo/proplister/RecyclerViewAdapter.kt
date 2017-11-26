package bz.mydns.walt.u_ryo.proplister

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by u-ryo on 17/11/26.
 */
class RecyclerViewAdapter(data: List<String>):
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    val data: List<String> = data

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.mTextView?.text = data[position]
    }

    override fun onCreateViewHolder(
            parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView? = itemView.findViewById(R.id.rowtext)
        companion object {
            fun create(v: TextView): ViewHolder = ViewHolder(v)
        }
    }
}