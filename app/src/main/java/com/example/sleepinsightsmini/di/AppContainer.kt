package com.example.sleepinsightsmini.di

import com.example.sleepinsightsmini.data.Repository
import com.example.sleepinsightsmini.data.RoomRepository

interface AppContainer {
    val repository: Repository
}