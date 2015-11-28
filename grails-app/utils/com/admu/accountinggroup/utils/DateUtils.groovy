package com.admu.accountinggroup.utils

import java.text.SimpleDateFormat

/**
 * Created by aldrin on 11/25/15.
 */
class DateUtils {

    //2015-11-23T07:50:07Z
    private static def format = "yyyy-MM-dd'T'HH:mm:ssX"

    static def convertToDate(String date){
        def formatter = new SimpleDateFormat(format);
        return formatter.parse(date)
    }

    static def convertToW3CXMLSchemaDateTimeString(Date date){
        def formatter = new SimpleDateFormat(format)
        return formatter.format(date)
    }
}
