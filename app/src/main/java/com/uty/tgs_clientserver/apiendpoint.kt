package com.uty.tgs_clientserver

class apiendpoint {

    companion object{

        //alamat api di dapatkan melalui cmd dengan mengetik ipconfig
private val SERVER="http://192.168.43.177/adminmember/"
        val CREATE= SERVER+"create.php"
        val READ = SERVER+"read.php"
        val UPDATE = SERVER+"update.php"
        val DELETE = SERVER+"delete.php"
    }
}