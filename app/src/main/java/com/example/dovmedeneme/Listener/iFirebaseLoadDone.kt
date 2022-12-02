package com.example.dovmedeneme.Listener

import com.example.dovmedeneme.Model.Tattoo
import java.util.SimpleTimeZone

interface IFirebaseLoadDone {

    fun onTattooLoadSuccess(tattooList: List<Tattoo>)
    fun onTattooLoadFailed(message:String)
}