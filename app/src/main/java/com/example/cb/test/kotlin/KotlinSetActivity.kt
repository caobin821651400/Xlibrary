package com.example.cb.test.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.bean.PersonBean

/**
 * 记录一些好用的语句and方法
 */
class KotlinSetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_fun)
        initView()
    }

    private fun initView() {
        /***_____________________________________数组_______________________________________________***/
        //1.数组最简单创建方式，kotlin可以自动识别数据的类型
//        val mArrays= arrayOf(1,2,3)

        //2.也可以指定类型创建数组,kotlin提供了8种byte,short,int,long,char,double,boolean如下简单写两个
//        val intArray = intArrayOf(1, 2, 3)
//        val longArray = longArrayOf(1L, 2L, 3L)

        //3.指定长度创建null的数组必须指定类型;类似java的 Integer[] ints = new Integer[3];
//        val mArrays = arrayOfNulls<Int>(3)
//        mArrays[0] = 1

        //4.数组转List,as作用是强转
//        var list = mArrays.toList() as ArrayList<Int>

        //5.数组的遍历
//        for (a in mArrays) {
//            XLogUtils.d("数组的遍历->$a")
//        }
        /***_____________________________________List_______________________________________________***/
        //1.创建不可变的list，可以包含空值.
        val people = listOf(PersonBean("汤姆", 23), PersonBean("杰瑞", 34), PersonBean("酸奶", 34))

        //2.创建不可变list,不包含空值.
//        val people = listOfNotNull(PersonBean("汤姆", 23), PersonBean("杰瑞", 34))

        //3.创建可变list,可以含空值，跟listOf类似，在kotlin1.1中实际返回的是ArrayList.
//        val people = mutableListOf(PersonBean("汤姆", 23), PersonBean("杰瑞", 34))

        //4.创建可变list,可以含空值，跟mutableListOf类似.
//        val people = arrayListOf(PersonBean("汤姆", 23), PersonBean("杰瑞", 34))

        //5.相当于java中 List<T> list=new ArrayList<>();.
//        var people = ArrayList<PersonBean>()
//        people.add(PersonBean("汤姆", 23))
//        people.add(PersonBean("杰瑞", 34))

        //6.查询最大值&最小值,maxBy返回值类型是List<T>.
//        XLogUtils.d("最大值1->" + people.maxBy { it.age }!!.name)
//        XLogUtils.i("最小值1->" + people.minBy { it.age }!!.name)

        //7.遍历List
//        people.forEach { p: PersonBean ->
//            XLogUtils.e("遍历people->${p.name}")
//        }

        //8.遍历componentN的方式
//        for ((name, age) in people) {
//            XLogUtils.i(name + age)
//        }

        //9.查找list中所有元素;all返回的是boolean类型
//        XLogUtils.d("${people.all { it.age >= 22 }}")

        //10.查找list中所有元素，只要其中一个元素满足返回true
//        XLogUtils.d("${people.any { it.age >= 33 }}")

        //11.判断元素在不在list中;in在  !in不在
//        val person: PersonBean = people[0]
//        XLogUtils.d("在不在集合中-> ${if (person in people) "在" else "不在"}")

        //12.删除元素后的list
//        val chars = ('a'..'z').toList()
//        XLogUtils.d(chars.drop(23)) // 删除前23个[x, y, z]
//        XLogUtils.d(chars.dropLast(23)) // 删除后23个[a, b, c]
//        XLogUtils.d(chars.dropWhile { it < 'x' }) // 删除小于'x'的[x, y, z]
//        XLogUtils.d(chars.dropLastWhile { it > 'c' }) // 删除大于'c'的[a, b, c]

        //13.filter过滤;返回结果是list
//        XLogUtils.d(people.filter { it.age > 33 }[0].name)

        //14.列表倒序
//        people.reverse()

        //15.取相同的元素划分新的分组(map),age作为map的key，value则是List<PersonBean>
        //   相当于Map<Int,List<PersonBean>>
//        XLogUtils.d(people.groupBy { it.age }.toString())

        //16.遍历List中元素，取出集合元素对象平铺成新的集合(toSet()去掉重复的;toList()不会去重)
//        val books = listOf(Book("第一行代码", listOf("郭霖", "弘扬")),
//                Book("Android开发艺术探索", listOf("任玉刚", "郭霖")),
//                Book("Kotlin实战", listOf("Dmitry Jemerov", "Svetlana Lsakova")))
//        XLogUtils.d(books.flatMap { it.authors }.toSet().toList().toString())

        //17.惰性集合(asSequence),数据量小的时候用下面链式没有性能影响
//        val list = people.map(PersonBean::name).filter { it.startsWith("汤") }
        //但是当数据量非常大的时候，kotlin推荐使用惰性集合来处理，上下两种方式就是多了一个asSequence()
        //序列，转换过程中没有创建任何中间集合。所以比第一种方式高效。结果是序列对象。
//        val list = people.asSequence().map(PersonBean::name).filter { it.startsWith("汤") }.toList()
//        XLogUtils.d(list.toString())

        //18.惰性集合,找集合中元素平方大于4的元素。
        var num: Int = listOf(1, 2, 3, 4).asSequence().map { it * it }.find { it > 3 } as Int
        //如果不使用asSequence，首先会将map { it*it }结果计算出来（1,4,9,16），然后再去找大于3的。
        //使用asSequence执行顺序1*1>3?->NO跳过，2*2>3-?YES 返回4，（也就是每个元素全部执行完所有的链式）
        XLogUtils.d(num.toString())

        /***_____________________________________Map_______________________________________________***/
        //1.mapOf返回不可变的map，有序
//        val courseMap = mapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")

        //2.可变的map集合，
//        val courseMap= mutableMapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")//有序
//        val courseMap= linkedMapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")//有序
//        val courseMap = hashMapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")//无序的
//        val courseMap = sortedMapOf(1 to "数学", 2 to "语文", 3 to "物理", 4 to "化学")//无序的

        //3.map集合取最大值&最小值
//        val maxMap: Map.Entry<Int, String> = courseMap.maxBy { it.key }!!
//        XLogUtils.d("最大值2->${courseMap[maxMap.key]} ")
//        XLogUtils.i("最小值2->" + courseMap.minBy { it.key })

        //4.map支持增删查改，方法与java差不多，就不一一说了

        //遍历map第一种方式
//        courseMap.forEach {
//            XLogUtils.d("遍历数组第一种方式->${it.key}   值：${it.value}")
//        }
        //遍历map第二种方式
//        for (map in courseMap) {
//            XLogUtils.i("遍历数组第二种方式->${map.key}   值：${map.value}")
//        }
        //遍历map第三种方式
//        for ((key, value) in courseMap) {
//            XLogUtils.e("遍历数组第三种方式->$key   值：$value")
//        }
    }
}
