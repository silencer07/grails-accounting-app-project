package com.admu.accountinggroup.utils

import java.text.SimpleDateFormat

/**
 * Created by aldrin on 11/25/15.
 */
class DateUtils {

    private static def format = "yyyy-MM-dd"

    static def convertToDate(String date){
        date = date.substring(0, 10)

        def formatter = new SimpleDateFormat(format)
        return formatter.parse(date)
    }
}
