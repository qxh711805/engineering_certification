package com.binbinhan.modules.tra.service;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 培养方案Excel接口
 *
 * @作者 binbinhan
 * @时间 2020-05-14 14:32
 */
public interface TraExcelService<T> {
   void saveImport(List<T> dataList);
}
