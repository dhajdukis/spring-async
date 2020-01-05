package hu.dhajdukis.springasync.entity

import javax.persistence.*

private const val MAX_COUNTER = 10

@Entity
data class Data(
        var counter: Int,
        @Enumerated(EnumType.STRING)
        var state: State,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long
) {
    fun checkCounter() {
        check(counter < MAX_COUNTER) { "Counter limit exceeded. Id: $id" }
    }

    fun increaseCounter() {
        if (counter < MAX_COUNTER) counter++
    }

    fun changeState(state: State) {
        counter = 0
        this.state = state
    }
}

enum class State {
    TODO,
    DONE
}
