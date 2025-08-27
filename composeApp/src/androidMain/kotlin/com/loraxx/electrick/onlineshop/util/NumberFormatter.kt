package com.loraxx.electrick.onlineshop.util

import java.text.DecimalFormat

actual fun Double.formatToUsd(): String {
    return DecimalFormat("$#,##0.00").format(this)
}
