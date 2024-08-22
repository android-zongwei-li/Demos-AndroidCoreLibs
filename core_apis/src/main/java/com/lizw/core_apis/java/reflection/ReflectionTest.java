package com.lizw.core_apis.java.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class ReflectionTest {
    public static void main(String[] args) {
        testGetClass();

        testConstruct();

        testField();

        testMethod();
    }

    public static void testGetClass() {
        // 方式1：Object.getClass()
        // Object类中的getClass()返回一个Class类型的实例
        Boolean teat = true;
        Class<?> classType = teat.getClass();
        System.out.println(classType);
        // 输出结果：class java.lang.Boolean

        // 方式2：T.class 语法
        // T = 任意Java类型
        Class<?> classType1 = Boolean.class;
        System.out.println(classType1);
        // 输出结果：class java.lang.Boolean

        try {
            // 方式3：Class.forName()，使用时应需进行异常处理（try/catch）
            Class<?> classType2;
            classType2 = Class.forName("java.lang.Boolean");
            System.out.println(classType2);
            // 输出结果：class java.lang.Boolean
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // 方式4：TYPE语法
        Class<?> classType3 = Boolean.TYPE;
        System.out.println(classType3);
        // 输出结果：boolean
    }

    private static void testConstruct() {
        // 1. 获取 Person 类的Class对象
        Class<Person> personClass = Person.class;

        try {
            // 2.1 通过Class对象获取Constructor类对象，并通过 newInstance() 创建对象，调用无参构造方法
            Object mObj1 = personClass.getConstructor().newInstance();

            // 2.2 通过Class对象获取Constructor类对象（传入参数类型），从而调用有参构造方法
            Object mObj2 = personClass.getConstructor(String.class).newInstance("Jack");
        } catch (IllegalAccessException | InstantiationException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void testField() {
        // 1. 获取Student类的Class对象
        Class<Person> personClass = Person.class;

        try {
            // 2. 通过Class对象创建Student类的对象
            // Class.newInstance()已经被弃用，下面的方法替代了 personClass.newInstance()
            Object person = personClass.getDeclaredConstructor().newInstance();

            // 3. 通过Class对象获取 Person 类的name属性
            Field nameField = personClass.getDeclaredField("name");

            // 4. 设置私有访问权限
            // private 属性，如果不设置会报错：
            // java.lang.IllegalAccessException: Class com.lizw.core_apis.java.reflection.ReflectionTest
            // can not access a member of class com.lizw.core_apis.java.reflection.Person with modifiers "private"
            nameField.setAccessible(true);

            Object defaultName = nameField.get(person);
            System.out.println("Default name = " + defaultName);

            // 5. 对新创建的Student对象设置name值
            nameField.set(person, "Jack");
            // 6. 获取新创建Student对象的的name属性 & 输出
            System.out.println("New name = " + nameField.get(person));
        } catch (InvocationTargetException | IllegalAccessException
                 | InstantiationException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static void testMethod() {
        Class<Person> personClass = Person.class;
        try {
            // 获取无参方法
            Method printMethod = personClass.getDeclaredMethod("print");
            // 获取有参方法
            Method setNameMethod = personClass.getDeclaredMethod("setName", String.class);

            Object person = personClass.getConstructor().newInstance();

            // 方法调用
            printMethod.invoke(person);
            setNameMethod.invoke(person, "Bob");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
