package com.example.audiovideocall

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.layout.Layout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class UserAdapter(val context: Context,val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, positon: Int) {
        val currentUser = userList[positon]

        holder.txtName.text = currentUser.name

        //where we can chat from recyclerview
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid", currentUser.uid)
            context.startActivity(intent)
        }
    }

    class UserViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView){

        val txtName = itemView.findViewById<TextView>(R.id.txt_name)
    }

}