package com.example.cb.test.aop

import android.util.Log
import android.view.View
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Pointcut
//import org.aspectj.lang.reflect.MethodSignature

/**
 * @author: bincao2
 * @date: 2022/3/14 10:31
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2022/3/14 10:31
 * @updateRemark: 更新说明
 */
//@Aspect
class FastViewAspect {

//    private val TAG = "FastViewAspect-> "
//
//    @Pointcut("execution(@com.example.cb.test.aop.FastClickView * *(..))")
//    fun executionFastClickViewLimit() {
//    }
//
//    @Around("executionFastClickViewLimit()")
//    @Throws(Throwable::class)
//    fun aroundExecuteFastClickViewLimit(joinPoint: ProceedingJoinPoint) {
//        Log.d(TAG, "aroundExecuteFastClickViewLimit")
//
//        val signature = joinPoint.signature as? MethodSignature
//
//        signature?.let {
//            val method = it.method
//            if (method.isAnnotationPresent(FastClickView::class.java)) {
//                method.getAnnotation(FastClickView::class.java)?.let { annotation ->
//                    //拿到时间间隔
//                    val time = annotation.time
//                    val view = joinPoint.args[0] as View
//                    if (isFastClick(view, time)) {
//                        joinPoint.proceed()
//                    }
//                }
//            }
//        }
//
//    }
//
//
//    /**
//     * 判断是否属于快速点击
//     *
//     * @param view     点击的View
//     * @param interval 快速点击的阈值
//     * @return true：快速点击
//     */
//    fun isFastClick(view: View, interval: Long): Boolean {
//        val key: Int = view.id
//        Log.d(TAG, "isFastClick: $view $interval")
//        val currentClickTime: Long = System.currentTimeMillis()
//        // 如果两次点击间隔超过阈值，则不是快速点击
//        return if (view.getTag(key) == null || currentClickTime - (view.getTag(key) as Long) > interval) {
//            // 保存最近点击时间
//            view.setTag(key, currentClickTime)
//            false
//        } else true
//    }
}