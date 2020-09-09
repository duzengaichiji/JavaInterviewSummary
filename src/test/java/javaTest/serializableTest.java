package javaTest;

import com.esotericsoftware.kryo.Kryo;
import javaBasic.Serializable.KryoSerializer;
import javaBasic.Serializable.Wanger;
import javaBasic.Serializable.WangerS;
import org.junit.Test;

import java.io.*;

public class serializableTest {
    @Test
    public void serializableTest1(){
        Wanger wanger = new Wanger();
        wanger.setAge(18);
        wanger.setName("杜曾");
        //由于没有实现序列化接口，所以会报错
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("cheshuo"));
            objectOutputStream.writeObject(wanger);
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("cheshuo"));
            Wanger wanger1 = (Wanger) objectInputStream.readObject();
            System.out.println(wanger1);
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializableTest2(){
        WangerS wanger = new WangerS("杜曾",18);
        wanger.setAge(18);
        wanger.setName("杜曾");

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("cheshuo"));
            objectOutputStream.writeObject(wanger);
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("cheshuo"));
            WangerS wanger1 = (WangerS) objectInputStream.readObject();
            System.out.println(wanger1);
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serializableWithKryoTest1(){
        WangerS wangerS = new WangerS("杜曾",18);
        KryoSerializer kryoSerializer = new KryoSerializer();
        //使用kryoSerializer需要写入无参构造器，否则就会报错
        System.out.println(kryoSerializer.deserialize(kryoSerializer.serialize(wangerS),wangerS.getClass()));
    }

    @Test
    public void serializableTest3(){
        WangerS wangerS = new WangerS("杜曾",18);
        System.out.println(wangerS);//18,屑
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] bytes = kryoSerializer.serialize(wangerS);
        wangerS.setAge(10086);
        WangerS.className = "臭";//18,臭
        //说明静态变量没有和对象一起被序列化，而实例变量被序列化了
        wangerS = (WangerS) kryoSerializer.deserialize(bytes,WangerS.class);
        System.out.println(wangerS);
    }
}
