package com.auto.common.classloader;

/**
 * @author auto.yin [auto.yin@gmail.com]
 * 2018-12-22
 * @Description: <p></p>
 */

import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义类加载器
 */
public class ClassLoaderTest extends ClassLoader {
    private String currentRoot = null;

    public ClassLoaderTest(String userdir) {
        this.currentRoot = userdir;
    }

    public ClassLoaderTest() {
    }

    //根据类名从文件中读取类字节码
    public byte[] findClassBytes(String className) {
        if (currentRoot == null) {
            currentRoot = "";
        }
        try {
            String pathName = currentRoot + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
            FileInputStream inFile = new FileInputStream(pathName);
            byte[] classBytes = new byte[inFile.available()];
            inFile.read(classBytes);
            return classBytes;
        } catch (java.io.IOException ioEx) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public Class findClass(String name) throws ClassNotFoundException {
        byte[] classBytes = findClassBytes(name);
        if (classBytes == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }

    //根据传入的字节码 返回class信息
    @SuppressWarnings("unchecked")
    public Class findClass(String name, byte[] classBytes) throws ClassNotFoundException {
        if (classBytes == null) {
            throw new ClassNotFoundException("(classBytes==null)");
        } else {
            return defineClass(name, classBytes, 0, classBytes.length);
        }
    }

    //执行 实现了TaskIntf 接口的 任务类
    @SuppressWarnings("unchecked")
    public void execute(String codeName, byte[] code) {
        Class klass = null;
        try {
            klass = findClass(codeName, code);
            TaskIntf task = (TaskIntf) klass.newInstance();
            task.execute();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}