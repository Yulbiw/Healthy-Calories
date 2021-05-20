package com.example.haelthc

import android.app.AlertDialog
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_recycler.view.*

class ViewAdapter(private val exampleList: List<ViewItem>): RecyclerView.Adapter<ViewAdapter.ReViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_recycler, parent,false)
        return ReViewHolder(itemView)
    }

    override fun getItemCount(): Int = exampleList.size

    override fun onBindViewHolder(holder: ReViewHolder, position: Int) {
        val cerrentItem = exampleList[position]
        if(cerrentItem.imageResource.isNotEmpty()){
            var picture = Base64.decode(cerrentItem.imageResource,Base64.DEFAULT)
            val pic = BitmapFactory.decodeByteArray(picture,0,picture.size)
            holder.image.setImageBitmap(pic)
        }else{
            holder.image.setImageResource(R.drawable.logo)
        }
        holder.textV1.text = cerrentItem.text1
        holder.textV2.text = cerrentItem.text2
        holder.textV3.text = cerrentItem.text3

//        var picture = Base64.decode(photo, Base64.DEFAULT)
//        val pic = BitmapFactory.decodeByteArray(picture,0,picture.size)
//        holder.delete.setOnClickListener {
////            val builder = AlertDialog.Builder(context)
//        }
//        if (position==0){
//            holder.textV1.setBackgroundColor(Color.MAGENTA)
//        }
    }

    class ReViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val image: ImageView=itemView.findViewById(R.id.image_recycler)
        val textV1: TextView=itemView.findViewById(R.id.title_view)
        val textV2: TextView=itemView.findViewById(R.id.sup_view)
        val textV3: TextView=itemView.findViewById(R.id.date_view)
//        val delete: ImageView=itemView.findViewById(R.id.delete_view)
    }
}