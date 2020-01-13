package com.cybernetica.valkaryne.spectrumrejoice.core

interface BaseMapper<in A, out B> {

    fun map(type: A): B
}