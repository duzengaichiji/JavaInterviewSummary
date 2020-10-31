# 关于序列化和Serializable接口

相关代码：javaBasic.Serializable

> 一个简单的认知就是---序列化就是将将对象转化为二进制序列，便于持久化或者在网络中传输；
>
> 要进行序列化只要让对象实现serializable接口接口，JDK有自带的序列化器，也可以使用性能更好的序列化器（比如kryo等）；
>
> Serializable接口是一个没有任何方法或变量声明的接口，单纯作为一个标识，表示某个对象可以被序列化，实际上序列化的工作由其他类执行；


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

> 由上述代码可知，在进行文件流操作的时候，进行了序列化，所以对于ObjectOutputStream的writeObject0()方法；

    if (obj instanceof String) {
        writeString((String) obj, unshared);
    } else if (cl.isArray()) {
        writeArray(obj, desc, unshared);
    } else if (obj instanceof Enum) {
        writeEnum((Enum<?>) obj, desc, unshared);
    //是否支持序列化
    } else if (obj instanceof Serializable) {
        writeOrdinaryObject(obj, desc, unshared);
    } else {
        if (extendedDebugInfo) {
            throw new NotSerializableException(
                cl.getName() + "\n" + debugInfoStack.toString());
        } else {
            throw new NotSerializableException(cl.getName());
        }
    }

> 可见会对对象是否能写入文件进行判断，其中就包含了对象是否支持序列化即是否实现了Serializable接口；
>
> 可见Serializable接口仅作为标识，而实际的序列化操作在这里是由OutputStream类后续进行的；


----------

> static和transient修饰的变量是不会被序列化的；
>
> 在ObjectStreamClass中可以看到这一段；

        private static ObjectStreamField[] getDefaultSerialFields(Class<?> cl) {
        //变量的作用域
        Field[] clFields = cl.getDeclaredFields();
        ArrayList<ObjectStreamField> list = new ArrayList<>();
        int mask = Modifier.STATIC | Modifier.TRANSIENT;

        for (int i = 0; i < clFields.length; i++) {
            //只有没有被static或者transient修饰的变量会被纳入列表进行序列化
            if ((clFields[i].getModifiers() & mask) == 0) {
                list.add(new ObjectStreamField(clFields[i], false, true));
            }
        }
        int size = list.size();
        return (size == 0) ? NO_FIELDS :
            list.toArray(new ObjectStreamField[size]);
    }


----------

> 值得一提的是，最好加入无参构造器，因为kryo这样的序列化器使用的时候，如果类没有无参构造器则会序列化失败；

