package com.binlee.processor;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourceSubjectFactory;
import org.junit.Test;

import javax.tools.JavaFileObject;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/19
 */
public final class FactoryTest {

    private static final String JAVA_SRC =
            "package com.binlee.processor;\n" +
            "import com.binlee.annotation.Factory;\n" +
            "import com.binlee.design.factory.IShape;" +
            "@Factory(type = IShape.class, name = \"Circle\")\n" +
            "public abstract class Circle implements IShape {}";

    @Test
    public void testFactory() {
        final JavaFileObject source = JavaFileObjects.forSourceString("com.binlee.processor.Person", JAVA_SRC);
        Truth.assertAbout(JavaSourceSubjectFactory.javaSource())
                .that(source)
                .processedWith(new Apt())
                .failsToCompile()
                .withErrorContaining("")
                .in(source)
                .onLine(4);
    }
}
