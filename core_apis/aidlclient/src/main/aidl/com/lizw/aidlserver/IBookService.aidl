// IBookService.aidl
package com.lizw.aidlserver;

import com.lizw.aidlserver.BookInfo;

interface IBookService {
    // 获取图书信息
    BookInfo getBookInfo();

    // 查询某本图书是否存在
    boolean isExist(in BookInfo bookInfo);
}