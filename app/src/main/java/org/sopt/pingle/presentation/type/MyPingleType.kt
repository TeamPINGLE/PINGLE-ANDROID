package org.sopt.pingle.presentation.type

enum class MyPingleType(
    val boolean: Boolean,
    val tabPosition: Int
) {
    SOON(
        boolean = false,
        tabPosition = 0
    ),
    DONE(
        boolean = true,
        tabPosition = 1
    )
}
