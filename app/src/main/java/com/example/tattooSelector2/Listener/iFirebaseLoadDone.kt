package com.example.tattooSelector2.Listener

import com.example.tattooSelector2.Model.Tattoo

interface IFirebaseLoadDone {

    fun onTattooLoadSuccess(tattooList: List<Tattoo>)
    fun onTattooLoadFailed(message:String)
}