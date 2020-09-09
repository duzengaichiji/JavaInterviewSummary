package javaBasic.Serializable;

import java.io.Serializable;

public class WangerS implements Serializable {
    public static String className = "屑";
    private String name;
    private int age;

    public WangerS(){}

    public WangerS(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "WangerS{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' + className+
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
