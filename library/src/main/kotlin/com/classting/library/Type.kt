package com.classting.library

/**
 * Created by BN on 2017. 5. 29..
 */
interface Type {
    fun value(): String
}

enum class DefaultType: com.classting.library.Type {
    HEADER,
    FOOTER,
    DEFAULT;

    override fun value() = name
}

enum class SectionedType: com.classting.library.Type {
    HEADER,
    FOOTER,
    DEFAULT,
    SECTION;

    override fun value() = name
}

enum class ExtendedType: com.classting.library.Type {
    HEADER,
    FOOTER,
    DEFAULT,
    TWO_LINES;

    override fun value() = name
}