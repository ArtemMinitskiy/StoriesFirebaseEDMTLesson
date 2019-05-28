package com.example.artem.kotlinstoriesfirebase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.artem.kotlinstoriesfirebase.Utils.IFirebaseLoadDone
import com.example.artem.kotlinstoriesfirebase.Utils.Movie
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), IFirebaseLoadDone {

    lateinit var dbRef: DatabaseReference
    lateinit var iFirebaseLoadDone: IFirebaseLoadDone
    var counter: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbRef = FirebaseDatabase.getInstance().getReference("Movies")

        iFirebaseLoadDone = this

        btn_pause.setOnClickListener { stories.pause() }
        btn_resume.setOnClickListener { stories.resume() }
        btn_reverse.setOnClickListener { stories.reverse() }
        btn_load.setOnClickListener { dbRef.addListenerForSingleValueEvent(object: ValueEventListener{

            var movieList: MutableList<Movie> = ArrayList()

            override fun onCancelled(error: DatabaseError) {
                iFirebaseLoadDone.onFirebaseLoadFailed(error.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (movieSnapshot in dataSnapshot.children){
                    val movie = movieSnapshot.getValue<Movie>(Movie::class.java)
                    movieList.add(movie!!)
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(movieList)
            }

        })}

        image_view.setOnClickListener{ stories.skip() }
    }

    override fun onDestroy() {
        stories.destroy()
        super.onDestroy()
    }

    override fun onFirebaseLoadSuccess(movieList: List<Movie>) {
        stories.setStoriesCount(movieList.size)
        stories.setStoryDuration(1000L) //1 sec

        //First image
        Picasso.get().load(movieList[0].image).into(image_view, object : Callback {
            override fun onSuccess() {
                stories.startStories()
            }

            override fun onError(e: Exception?) {

            }

        })

        //Next image
        stories.setStoriesListener(object: StoriesProgressView.StoriesListener{
            override fun onComplete() {
                counter = 0
                Toast.makeText(this@MainActivity, "Load Done", Toast.LENGTH_SHORT).show()
            }

            override fun onPrev() {
                if (counter > 0){
                    counter--;
                    Picasso.get().load(movieList[counter].image).into(image_view)
                }
            }

            override fun onNext() {
                if (counter < movieList.size){
                    counter++
                    Picasso.get().load(movieList[counter].image).into(image_view)
                }
            }

        })
    }

    override fun onFirebaseLoadFailed(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
