package com.zt.homework.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExcelUtill {
    @Value("${homeDir}")
    private String homeDir;

    public static void exportSummaryExcel() {

    }

}
