package javaBasic.CloneCase;

import java.io.*;

public class DeepClonablePerson implements Cloneable, Serializable {
    private String name;
    private int age;
    private AddressClonable address;

    public DeepClonablePerson(String name, int age) {
        this.name = name;
        this.age = age;
        this.address = new AddressClonable();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepClonablePerson deepClonablePerson = (DeepClonablePerson) super.clone();
        deepClonablePerson.address = (AddressClonable) address.clone();
        return deepClonablePerson;
    }

    //深度拷贝
    public Object deepClone() throws Exception{
        // 序列化
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(this);

        // 反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);

        return ois.readObject();
    }

    public String display() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address.display() +
                '}';
    }

    public AddressClonable getAddress() {
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
