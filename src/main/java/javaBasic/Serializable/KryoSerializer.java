package javaBasic.Serializable;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;

public class KryoSerializer {
    private static final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        //应该自动扫描并注册entity
        kryo.register(WangerS.class);
        kryo.setReferences(true);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    public byte[] serialize(Object object){
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output,object);
            kryoThreadLocal.remove();
            return output.toBytes();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Object deserialize(byte[] bytes,Class<?> clazz){
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input,clazz);
            kryoThreadLocal.remove();
            return o;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
