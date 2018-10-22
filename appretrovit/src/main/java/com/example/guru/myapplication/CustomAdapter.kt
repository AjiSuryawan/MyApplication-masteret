package net.simplifiedcoding.recyclerviewexample
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.example.guru.myapplication.R
import com.example.guru.myapplication.RetroPhoto
import kotlinx.android.synthetic.main.adapter_details.view.*


/**
 * Created by Belal on 6/19/2017.
 */
class CustomAdapter(val userList: ArrayList<RetroPhoto>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){



    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_details, parent, false)
        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bindItems(userList[position])
    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return userList.size
    }
    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: RetroPhoto) {
            //println("gambarku : "+"http://image.tmdb.org/t/p/w185" + user.poster_path)
            itemView.tvjudul.text="title : "+user.title
            //itemView.tvdesc.text="visi dan misi : "+user.nim
            Glide.with(itemView)
                    .load(user.thumbnailUrl)
                    .into(itemView.ivcover)
        }
    }
}