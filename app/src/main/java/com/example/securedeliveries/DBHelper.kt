package com.example.securedeliveries

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context):SQLiteOpenHelper(context,"Userdata",null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table Userdata (username TEXT primary key,name TEXT, password TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists Userdata")
    }

    fun inserdata (username: String,name: String, password: String): Boolean {
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("username",username)
        cv.put("name",name)
        cv.put("password",password)
        val result = p0.insert("Userdata",null,cv)
        if (result==-1 .toLong()){
            return false
        }
        return true
    }

    fun checkuserpass(username: String,password: String): Boolean {
        val p0 = this.writableDatabase
        val query = "select * from Userdata where username= '$username' and password= '$password'"
        val cursor = p0.rawQuery(query,null)
        if (cursor.count<=0){
            cursor.close()
            return false
        }
        else {
            cursor.close()
            return true
        }
    }

    fun deleteUser(username: String): Boolean {
        val p0 = this.writableDatabase
        val cursor: Cursor = p0.rawQuery("select * from Userdata where username = ?", arrayOf(username))
        if (cursor.count>0){
            val result = p0.delete("Userdata","username=?", arrayOf(username))
            return result != -1
        }
        else {
            return false
        }
    }


}