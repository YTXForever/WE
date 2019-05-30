package classloader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yuh
 * @date 2019-05-29 11:06
 **/
public class MyClassLoader extends ClassLoader {

    private String path;


    /**
     * Creates a new class loader using the <tt>ClassLoader</tt> returned by
     * the method {@link #getSystemClassLoader()
     * <tt>getSystemClassLoader()</tt>} as the parent class loader.
     *
     * <p> If there is a security manager, its {@link
     * SecurityManager#checkCreateClassLoader()
     * <tt>checkCreateClassLoader</tt>} method is invoked.  This may result in
     * a security exception.  </p>
     *
     * @throws SecurityException If a security manager exists and its
     *                           <tt>checkCreateClassLoader</tt> method doesn't allow creation
     *                           of a new class loader.
     */
    public MyClassLoader(String path) {
        this.path = path;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return this.loadClass(name, false);
    }

    @Override
    public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        if (c != null) {
            System.out.println(name + "has already been loaded !!!");
            return c;
        }
        synchronized (name.intern()) {

            try {
                c = getParent().loadClass(name);
                if(c != null){
                    return c;
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }
            name = name.replaceAll("\\.", "/");
            try (FileInputStream fileInputStream = new FileInputStream(path + "/" + name + ".class")) {
                byte[] buf = new byte[1024 * 10];
                int read = fileInputStream.read(buf);
                c = defineClass(name, buf, 0, read);
                if (resolve) {
                    super.resolveClass(c);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new ClassNotFoundException(name);
            }
        }
        return c;
    }
}
