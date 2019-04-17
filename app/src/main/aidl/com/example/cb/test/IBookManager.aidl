// IBookManager.aidl
package com.example.cb.test;

// Declare any non-default types here with import statements
import  com.example.cb.test.ui.aidl.Book;
interface IBookManager {
     /**
        * 获取图书列表
        */
       List<Book> getBookList();

       /**
        * 添加图书
        */
       void addBook(in Book book);


}
