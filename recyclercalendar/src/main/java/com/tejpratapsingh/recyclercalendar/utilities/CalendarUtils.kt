package com.tejpratapsingh.recyclercalendar.utilities

import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CalendarUtils {

    companion object {
        @JvmStatic
        val DB_DATE_FORMAT = "yyyyMMdd"
        @JvmStatic
        val DB_YEAR_MONTH_FORMAT = "yyyyMM"
        @JvmStatic
        val DB_DATE_FORMAT_WITH_TIME = "yyyyMMddHHmm"
        @JvmStatic
        val SHORT_DATE_FORMAT = "dd-MMM-yyyy"
        @JvmStatic
        val LONG_DATE_FORMAT = "EEE, dd MMM yyyy"
        @JvmStatic
        val LONG_DATE_DAY = "E"
        @JvmStatic
        val DISPLAY_TIME_FORMAT = "hh:mm aaa"
        @JvmStatic
        val DISPLAY_DATE_TIME_FORMAT = "EEE, dd MMM yyyy, hh:mm aaa"

        /**
         * Returns Date Object from date string and date format
         *
         * @param date string date
         * @param format string date format
         * @return IF OK date object ELSE null
         */
        @JvmStatic
        fun dateFromAnyFormat(date: String, format: String): Date? {
            return try {
                val formatter = SimpleDateFormat(format, Locale.getDefault())
                formatter.parse(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }

        /**
         * Returns Date Object from date string and date format
         *
         * @param date string date
         * @param format string date format
         * @return IF OK date object ELSE null
         */
        @JvmStatic
        fun dateStringFromFormat(date: Date, format: String): String? {
            return try {
                val formatter =
                    SimpleDateFormat(format, Locale.getDefault())
                formatter.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * Get month Name from month index
         *
         * @param month month index
         * @return IF OK month string ELSE null
         */
        fun getMonth(month: Int): String? {
            val mDateFormatSymbols: DateFormatSymbols =
                DateFormatSymbols.getInstance(Locale.UK)
            return mDateFormatSymbols.months.get(month)
        }

        /**
         * Get day Name from day index of week
         *
         * @param day day index
         * @return IF OK day string ELSE null
         */
        fun getDay(day: Int): String? {
            val mDateFormatSymbols: DateFormatSymbols =
                DateFormatSymbols.getInstance(Locale.UK)
            val dayStr: String = mDateFormatSymbols.weekdays.get(day)
            return dayStr.substring(0, if (dayStr.length >= 3) 3 else dayStr.length)
        }
    }
}