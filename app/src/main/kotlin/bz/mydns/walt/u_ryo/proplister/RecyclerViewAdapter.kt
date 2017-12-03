package bz.mydns.walt.u_ryo.proplister

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by u-ryo on 17/11/26.
 */
class RecyclerViewAdapter(val data: List<String>):
        RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), Parcelable {
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.mTextView?.text = data[position]
    }

    override fun onCreateViewHolder(
            parent: ViewGroup?, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent?.context)
                .inflate(android.R.layout.simple_list_item_1,
                        parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView? = itemView.findViewById(android.R.id.text1)
        companion object {
            fun create(v: TextView): ViewHolder = ViewHolder(v)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(data)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<RecyclerViewAdapter> {
        override fun createFromParcel(parcel: Parcel): RecyclerViewAdapter {
            val data = ArrayList<String>()
            parcel.readStringList(data)
            return RecyclerViewAdapter(data)
        }

        override fun newArray(size: Int): Array<RecyclerViewAdapter?> {
            return arrayOfNulls(size)
        }
    }
}