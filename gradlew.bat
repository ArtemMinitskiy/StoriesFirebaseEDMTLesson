package com.example.artem.kotlinrealtimelocationedmtlesson.Service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.artem.kotlinrealtimelocationedmtlesson.Utils.Common
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.paperdb.Paper

class MyLocationReceiver: BroadcastReceiver() {

    lateinit var publicLocation: DatabaseReference
    lateinit var uid: String

    init {
        publicLocation = FirebaseDatabase.getInstance().getReference(Common.PUBLIC_LOCATION)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Paper.init(context!!)

        uid = Paper.book().read(Common.USER_UID_SAVE_KEY)
    }

}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   