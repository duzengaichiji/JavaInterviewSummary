package javaBasic.CloneCase;

public class CloneCaseTest {
    public static void main(String[] args) throws Exception {
        /*
        浅拷贝
         */
        Person person = new Person("杜曾",18);
        person.setAddress("福建省","南平市");
        Person person1 = (Person) person.clone();
        //可以看到，两个对象的地址是不同的
        System.out.println("origin:"+person);
        System.out.println("clone:"+person1);
        //两个address的地址是一样的
        System.out.println("origin address:"+person.getAddress());
        System.out.println("clone address:"+person1.getAddress());

        System.out.println("origin:"+person.display());
        System.out.println("clone:"+person1.display());

        person.setAge(10086);
        person.getAddress().setProvince("杜曾");
        //改变了原始的address，克隆对象跟着变，而改变年龄则没有
        System.out.println("origin:"+person.display());
        System.out.println("clone:"+person1.display());
        System.out.println();

        /*
        深拷贝实现
         */
        DeepClonablePerson deepClonablePerson = new DeepClonablePerson("杜曾",18);
        deepClonablePerson.setAddress("福建省","南平市");
        DeepClonablePerson deepClonablePerson1 = (DeepClonablePerson) deepClonablePerson.clone();
        deepClonablePerson.getAddress().setCity("杜曾");
        //两个address的地址不同
        System.out.println(deepClonablePerson.getAddress());
        System.out.println(deepClonablePerson1.getAddress());
        //改变两个address互不影响
        System.out.println(deepClonablePerson.display());
        System.out.println(deepClonablePerson1.display());
        //与上面两个address都不同
        DeepClonablePerson deepClonablePerson2 = (DeepClonablePerson) deepClonablePerson.deepClone();
        System.out.println(deepClonablePerson2.getAddress());
    }
}
