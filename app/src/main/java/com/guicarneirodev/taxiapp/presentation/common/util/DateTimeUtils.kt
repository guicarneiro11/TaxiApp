package com.guicarneirodev.taxiapp.presentation.common.util

object DateTimeUtils {
    fun formatDate(dateString: String): String {
        return try {
            // Assumindo que a data vem no formato ISO
            dateString.substring(0, 16).replace('T', ' ')
        } catch (e: Exception) {
            dateString
        }
    }

    fun formatPrice(value: Double): String {
        return "%.2f".format(value)
    }

    fun formatDistance(distance: Double): String {
        return "%.1f".format(distance)
    }

    fun formatDuration(duration: String): String {
        return try {
            // Se a duração vier em segundos, converte para minutos
            if (duration.endsWith('s')) {
                val seconds = duration.removeSuffix("s").toInt()
                "${seconds / 60} min"
            } else {
                duration
            }
        } catch (e: Exception) {
            duration
        }
    }
}
