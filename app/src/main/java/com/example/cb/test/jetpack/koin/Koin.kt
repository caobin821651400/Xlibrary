package com.example.cb.test.jetpack.koin

import org.koin.dsl.module


val module = module {
    factory { CPU() }

    factory { Computer(get() ) }
}