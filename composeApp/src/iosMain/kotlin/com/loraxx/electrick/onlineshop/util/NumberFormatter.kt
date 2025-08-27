package com.loraxx.electrick.onlineshop.util

import platform.Foundation.NSDecimalNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle

actual fun Double.formatToUsd(): String {
    val formatter = NSNumberFormatter().apply {
        setNumberStyle(NSNumberFormatterCurrencyStyle)
        setCurrencyCode("USD")
        setMinimumFractionDigits(2U)
        setMaximumFractionDigits(2U)
    }
    return formatter.stringFromNumber(NSDecimalNumber(this)) ?: "$0.00"
}