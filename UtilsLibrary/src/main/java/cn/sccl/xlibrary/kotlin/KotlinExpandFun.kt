package cn.sccl.xlibrary.kotlin


/**
 * by lazy 默认模式是 [LazyThreadSafetyMode.SYNCHRONIZED] 双重加锁的单例;有些情况下不涉及多线程会有性能影响
 * @param initializer
 * @return Lazy<T>
 */
fun <T> lazyNone(initializer: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE, initializer)
