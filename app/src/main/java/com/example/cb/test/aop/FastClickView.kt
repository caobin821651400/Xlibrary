package com.example.cb.test.aop

/**
 * @author: bincao2
 * @date: 2022/3/14 10:29
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/3/14 10:29
 * @updateRemark: 更新说明
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FastClickView(val time: Long = 1500)
