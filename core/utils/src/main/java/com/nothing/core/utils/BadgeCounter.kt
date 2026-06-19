package com.nothing.core.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object BadgeCounter {
    private val _badgeCounts = MutableStateFlow<Map<String, Int>>(emptyMap())
    val badgeCounts: StateFlow<Map<String, Int>> = _badgeCounts.asStateFlow()

    fun update(counts: Map<String, Int>) {
        _badgeCounts.value = counts
    }
}
