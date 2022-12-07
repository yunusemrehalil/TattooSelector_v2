package com.example.dovmedeneme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import com.example.dovmedeneme.Adapter.MyAdapter
import com.example.dovmedeneme.Listener.IFirebaseLoadDone
import com.example.dovmedeneme.Model.Tattoo
import com.example.dovmedeneme.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.*


class MainActivity : AppCompatActivity(), IFirebaseLoadDone {
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter:MyAdapter
    lateinit var dr:DatabaseReference
    lateinit var wr:DatabaseReference
    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    override fun onTattooLoadSuccess(tattooList: List<Tattoo>) {
        adapter = MyAdapter(this,tattooList)
        binding.viewPager.adapter = adapter
    }

    override fun onTattooLoadFailed(message: String) {
        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wr = FirebaseDatabase.getInstance().getReference("Users/Favourites")
        dr = FirebaseDatabase.getInstance().getReference("Tattoos")
        iFirebaseLoadDone = this
        loadTattoo()
        binding.apply {
            buttonLike.setOnClickListener()
            {
                /*val intent = Intent(this@MainActivity,FavouriteActivity::class.java)
                startActivity(intent)*/
                //var imageBitmap: Drawable? = getDrawable(binding.viewPager.currentItem+1)
                var currentPosition:Int = viewPager.currentItem
                var fotograf: Tattoo = adapter.data.get(currentPosition)
                var imageURL = fotograf
                val ref = wr.setValue(imageURL)
            }
            buttonBack.setOnClickListener()
            {
                val page:Int = binding.viewPager.currentItem
                if(page==0)
                {
                    Toast.makeText(this@MainActivity, "geride başka dövme yok...", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    binding.viewPager.currentItem = page-1
                }
            }
            buttonDislike.setOnClickListener()
            {
                val page:Int = binding.viewPager.currentItem
                binding.viewPager.currentItem = page+1
            }
        }
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