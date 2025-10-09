package io.agistep.challenge

import kotlin.concurrent.thread


//val name: String? = "Marton"
//val surname: String? = "Braun"
//
//val fullName: String?
//    get() = name?.let { "$it $surname" }
//
//val fullNameFinal = fullName
//val fullName2: String? = name?.let { "$it $surname" }
//
/////
//
//
//fun main() {
//    if (fullName != null) {
//        println(fullName.length)
//    }
//
//    if (fullName2 != null) {
//        println(fullName2.length)
//    }
//}

//fun calculate(): Int {
//    print("Calculating...")
//    return 42
//}
//
//val fizz = calculate() // Calculating..
//val buzz
//    get() = calculate()
//
//fun main() {
//    println(fizz) //Calculating...42
//    println(fizz) //42
//    println(buzz) //Calculating.. 42
//    println(buzz) //Calculating.. 42
//}


//fun main() {
////    val listOf = listOf("xx", "aa")
////    val strings = listOf + "cccc"
////    val map = listOf.map { it + "A" }
////    println(strings)
////
//    val a = listOf("xx", "aa")
//
//// 1) 강제 캐스팅 뒤 수정 시도 → 보통 UnsupportedOperationException
//    @Suppress("UNCHECKED_CAST")
//    val m = a as MutableList<String>
//    try {
//        m.add("cccc")          // 대개 여기서 UnsupportedOperationException
//        println("변경 가능")
//    } catch (e: UnsupportedOperationException) {
//        println("변경 불가능(런타임 차단)")
//    }
//
//}

//data class User(val name: String, val surname: String)
//
//fun main() {
////    val user = User("Maja", "Markiewicz")
////    val user2 = user.copy(surname= "MMM")
////    print(user2)
//
//    val list1: MutableList<Int> = mutableListOf()
//    var list2: List<Int> = listOf()
//
//    val list3 = mutableListOf<Int>()
//
//    list1.add(1)
//    list2 = list2 + 1
//
//    val elapsed2 = measureTimeMillis {
//
//        for (i in 1..1000000) {
//            list1.add(1)
//        }
//    }
//
//    println("list1:$elapsed2")
//
//    val elapsed = measureTimeMillis {
//
//        for (i in 1..1000000) {
//            list2 = list2 + 1
//        }
//    }
//
//    println("list2:$elapsed")
//
//
//}
//
//fun main() {
//    val names: SortedSet<FullName> = TreeSet()
//    val person = FullName("AAA", "BBB")
//    names.add(person)
//    names.add(FullName("Jordan", "Hansen"))
//    names.add(FullName("David", "Blanc"))
//
//    println(names)
//    println(person in names)
//
//    person.firstName = "ZZZ"
//
//    println(names)
//    println(person in names)
//
//}
//
//data class FullName(var firstName: String, var lastName: String) : Comparable<FullName> {
//    override fun compareTo(other: FullName): Int {
//        return compareValuesBy(this, other, FullName::firstName, FullName::lastName)
//    }
//}

fun main() {

    val list1: MutableList<Int> = mutableListOf()
    var list2: List<Int> = listOf()

    list1.add(1)
    list2 = list2 + 1

    for (i in 1..1000) {
        thread {
            list2 = list2 + i
        }
    }
    Thread.sleep(1000)
    print(list2.size)

}

