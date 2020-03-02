package com.cybernetica.valkaryne.spectrumrejoice.core.status

data class QueryStatus(
    val statusType: StatusType,
    val error: Throwable? = null
) {

    companion object {
        fun loading(): QueryStatus {
            return QueryStatus(StatusType.LOADING)
        }

        fun success(): QueryStatus {
            return QueryStatus(StatusType.SUCCESS)
        }

        fun error(error: Throwable): QueryStatus {
            return QueryStatus(StatusType.ERROR, error)
        }
    }
}