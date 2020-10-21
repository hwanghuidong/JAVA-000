import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author huang huidong
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {

        String xlassFilePath = "D:\\JAVA000\\Week01\\src\\main\\java\\Hello.xlass";

        try {
            Class<?> clazz = new HelloClassLoader().convert2Class("Hello", xlassFilePath);
            Method helloMethod = clazz.getDeclaredMethod("hello");
            helloMethod.setAccessible(true);
            helloMethod.invoke(clazz.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected Class<?> convert2Class(String className, String loadClassFilePath)
            throws ClassNotFoundException {

        //file2Bytes将文件转化成byte数组
        byte[] classBytes = file2Bytes(loadClassFilePath);

        //defineClass方法继承自ClassLoader类
        return defineClass(className, classBytes, 0, classBytes.length);

    }



    protected byte[] file2Bytes(String filePath) {

        byte[] buffer = null;
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(new File(filePath));
            bos = new ByteArrayOutputStream(2048);
            byte[] originBytes = new byte[1];
            byte[] resultBytes = new byte[1];
            byte b255 = (byte) 255;
            int pos;
            while ((pos = fis.read(originBytes)) != -1) {

                //对读出的Xlass文件，逐个字节进行处理
                resultBytes[0] = (byte) (b255 - originBytes[0]);

                bos.write(resultBytes, 0, pos);
            }
            buffer = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer;
    }
}
