package com.nha.usingprovider

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getContact()
    }

    fun getContact(){
        val cr = contentResolver
        val cur: Cursor? = cr.query(
            Uri.parse("content://com.nha.myprovider.MyProvider/Student"),
            null, null, null, null
        )
        cur?.apply {
            while (this.moveToNext()){
                Log.d("NHAPRODIVER", cur.getString(0)+"_"+cur.getString(1))
            }
        }
        if (cur != null) {
            cur.close()
        }
    }
}