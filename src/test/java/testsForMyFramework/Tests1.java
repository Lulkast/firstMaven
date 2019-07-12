package testsForMyFramework;

import annotations.MyFrameworkTest;

@MyFrameworkTest(2)
public class Tests1 {
    @MyFrameworkTest(1)
    public void test1 (){
        System.out.println("hello, im test1 from tests2");
    }
    @MyFrameworkTest(5)
    public void test2 (){
        System.out.println("hello, im test2 from tests2");
    }
}
