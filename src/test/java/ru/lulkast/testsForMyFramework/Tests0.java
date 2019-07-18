package ru.lulkast.testsForMyFramework;

import ru.lulkast.annotations.MyFrameworkTest;

@MyFrameworkTest (1)
public class Tests0 {
    @MyFrameworkTest (1)
    public void test1 (){
        System.out.println("hello, im test1 from tests1");
    }
    @MyFrameworkTest(2)
    public void test2 (){
        System.out.println("hello, im test2 from tests1");
    }
}
