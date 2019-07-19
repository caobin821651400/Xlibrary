package com.example.cb.test.mvvm

import com.example.cb.test.databinding.ActivityMvvmBinding

class MvvmModule : AbstractMvvmViewModule<ActivityMvvmBinding> {

    constructor(activityMvvmBinding: ActivityMvvmBinding) : super(activityMvvmBinding)

    fun getUserInfo(): UserInfo {
        return UserInfo("汤姆", 28)
    }
}