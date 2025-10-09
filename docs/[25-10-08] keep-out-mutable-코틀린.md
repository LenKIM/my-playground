# 가변을 제한하라


이펙티브 코틀린에서 첫번째 장으로, 가변성을 제한하라 내용을 가져온 것.

**가변성이 많아진다면 어떤 문제가 생길까?**

1. 변경 지점이 많은 프로그램은 이해하고 디버깅하기 어렵다.
2. 가변성은 코드를 추론하기 더 어렵게 만든다.
3. 가변 상태는 멀티스레드 프로그램에서 적절히 동기화되어야 한다.
4. 가변 요소는 테스트하기 더 어렵다.
5. 상태가 변경되면 다른 클래스에 이 변경 사항을 알려야 하는 경우가 많다.

그렇다면 코틀린에서 가변성을 제어하는 것으로 3가지

1. 읽기 전용 프로퍼티 val
```kotlin
val name: String? = "Marton"
val surname: String? = "Braun"

val fullName: String?
    get() = name?.let { "$it $surname" }

//val fullNameFinal = fullName
val fullName2: String? = name?.let { "$it $surname" }

fun main() {
    if (fullName != null) {
        println(fullName.length)
    }

    if (fullName2 != null) {
        println(fullName2.length)
    }
}
```
`println(fullName.length)` 여기가 에러가 발생한다. 왜일까?

val 로 불변 객체를 만들더라도, 읽기 전용 프로퍼티가 반드시 변경 불가능하거나 final 이어야 하는 것은 아니다. 그렇기 때문에, 사용자 정의 게터(custom getter) 에 따라 다르게 호출될 수 있다.

즉, val 이 변경 불가능함을 의미하지는 않는다는 점을 기억해야 한다.

그럼 어떻게 해? `val fullNameFinal = fullName` 와 같이 하면 해결될 수 있다.



또 다른 읽기 프로퍼티문제로 아래와 같이 있다.

```kotlin
fun calculate(): Int {
    print("Calculating...")
    return 42
}

val fizz = calculate() // Calculating..
val buzz
    get() = calculate()

fun main() {
    println(fizz) //Calculating...42
    println(fizz) //42
    println(buzz) //Calculating.. 42
    println(buzz) //Calculating.. 42
}
```

main 에 fizz 에 처음에는 Calculating...42 다음이 42 로 나온다. get 에서 캐시가 되어 값이 다르게 동작된다.



2. 가변 컬렉션과 읽기 전용 컬렉션의 구분

```kotlin
fun main() {
//    val listOf = listOf("xx", "aa")
//    val strings = listOf + "cccc"
//    val map = listOf.map { it + "A" }
//    println(strings)
//
    val a = listOf("xx", "aa")

// 1) 강제 캐스팅 뒤 수정 시도 → 보통 UnsupportedOperationException
    @Suppress("UNCHECKED_CAST")
    val m = a as MutableList<String>
    try {
        m.add("cccc")          // 대개 여기서 UnsupportedOperationException
        println("변경 가능")
    } catch (e: UnsupportedOperationException) {
        println("변경 불가능(런타임 차단)")
    }
}
```

가변 컬렉션과 읽기 전용 컬렉션의 구분에 대해서 강제 다운 캐스팅은 하면 안된다.

```
 val list1: MutableList<Int> = mutableListOf()
    var list2: List<Int> = listOf()

    val list3 = mutableListOf<Int>()

    list1.add(1)
    list2 = list2 + 1

    val elapsed2 = measureTimeMillis {

        for (i in 1..1000000) {
            list1.add(1)
        }
    }

    println("list1:$elapsed2")

    val elapsed = measureTimeMillis {

        for (i in 1..1000000) {
            list2 = list2 + 1
        }
    }

    println("list2:$elapsed")
```

불변과 가변에 따라 시간이 다르다는 사실을 알 수 있다. 



3. 데이터 클래스의 copy

불변 객체의 이점은?

- 한 번 생성되면 상태가 동일하게 유지되므로 상태를 추론하기가 더 쉽습니다.
- 공유되는 객체들 간의 충돌이 발생하지 않으므로 프로그램을 병렬화하기 더 쉽다
- 불변 객체에 대한 참조는 변경되지 않을 것이므로 캐싱될 수 있다.
- 불변 객체에 대해 방어적 본삭본을 만들 필요가 없다.
- 불변 객체는 다른 객체를 구성하는데, 완벽한 재료가 된다.

```
fun main() {
    val names: SortedSet<FullName> = TreeSet()
    val person = FullName("AAA", "BBB")
    names.add(person)
    names.add(FullName("Jordan", "Hansen"))
    names.add(FullName("David", "Blanc"))

    println(names)
    println(person in names)

    person.firstName = "ZZZ"

    println(names)
    println(person in names)

}
print--

[FullName(firstName=AAA, lastName=BBB), FullName(firstName=David, lastName=Blanc), FullName(firstName=Jordan, lastName=Hansen)]
true
[FullName(firstName=ZZZ, lastName=BBB), FullName(firstName=David, lastName=Blanc), FullName(firstName=Jordan, lastName=Hansen)]
false <- 여기가 왜 false 일까? 
```

왜 "ZZZ" 이 부분에서 포함되지 않았을까?

찾는 객체의 해시 값이 달라졌기 때문에 찾을 수 없다.

가변객체가 위험한 이유는 이런 것 때문이다. 

그렇다면 어떻게 해야될까? 아래와 같은 코드를 살펴보자

```kotlin
class User (val name: String, val surname: String) { fun withSurname(surname: String) = User(name, surname)}

var user = User("Maja", "Mark")
user = user.withSurname("Mosk")
print(user)
```

불변으로 만들어서 해결한다.



결론은 **불필요한 가변 상태를 만들지 말라**



마지막 진짜 정리

- var 보다 val 사용을 선호하라.
- 가변 프로퍼티보다 불편 프로퍼티 사용을 선호
- 가변 객체와 클래스보다 불변 객체와 클래스 사용 선호
- 불변 객체를 변경해야 한다면 data 클래스로 만들고 copy 메서드 사용
- 상태를 저장해야 한다면 가변 컬렉션보다 읽기 전용 컬렉션 선호
- 변경 지점을 현명하게 설계하고 불필요한 변경 지점을 생성하지 말자.





