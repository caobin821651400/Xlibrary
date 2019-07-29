package com.example.cb.test.mvvm

import android.text.TextUtils
import com.example.cb.test.databinding.ActivityMvvmBinding

/**
 *
 * @author bin
 * @time 2019年7月19日10:27:56
 * @desc 主要处理业务逻辑
 */
class MvvmModule : AbstractMvvmViewModule<ActivityMvvmBinding> {
    var user = UserInfo()

    constructor(activityMvvmBinding: ActivityMvvmBinding) : super(activityMvvmBinding)

    fun setUserInfo(name: String?): UserInfo {
        user.name.set(if (TextUtils.isEmpty(name)) "汤姆" else name)
        user.age.set(28)
        return user
    }

    /****99999999999999999999999999999999999999999999999**/
}