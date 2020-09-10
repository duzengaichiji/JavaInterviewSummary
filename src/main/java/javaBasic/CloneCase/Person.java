package javaBasic.CloneCase;

public class Person implements Cloneable{
    private String name;
    private int age;
    private Address address;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.address = new Address();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String display() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address.display() +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(String province, String city) {
        this.address.setAddress(province,city);
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
