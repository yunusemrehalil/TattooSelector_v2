package com.example.dovmedeneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.dovmedeneme.Adapter.MyAdapter
import com.example.dovmedeneme.Listener.IFirebaseLoadDone
import com.example.dovmedeneme.Model.Tattoo
import com.example.dovmedeneme.databinding.ActivityMainBinding
import com.google.firebase.database.*



class MainActivity : AppCompatActivity(), IFirebaseLoadDone {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter:MyAdapter
    lateinit var dr:DatabaseReference
    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    override fun onTattooLoadSuccess(tattooList: List<Tattoo>) {
        adapter = MyAdapter(this,tattooList)
        binding.viewPager.adapter = adapter
        Toast.makeText(this, "basarili", Toast.LENGTH_SHORT).show()
    }

    override fun onTattooLoadFailed(message: String) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dr = FirebaseDatabase.getInstance().getReference("Tattoos")
        iFirebaseLoadDone = this
        loadTattoo()

    }

    fun loadTattoo()
    {
        var tattoos:MutableList<Tattoo> = ArrayList()
        dr.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(tattooSnapShot in snapshot.children)
                {
                    val tattoo = tattooSnapShot.getValue(Tattoo::class.java)
                    tattoos.add(tattoo!!)
                }
                iFirebaseLoadDone.onTattooLoadSuccess(tattoos)
            }

            override fun onCancelled(error: DatabaseError) {
                iFirebaseLoadDone.onTattooLoadFailed(error.message)
            }

        })
    }
}