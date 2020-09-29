package com.binlee.processor;

import com.binlee.annotation.Gender;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.Set;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class Apt extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Gender.class);
        if (elements == null || elements.size() == 0) {
            return false;
        }
        for (final Element element : elements) {
            processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.NOTE, "process()", element);
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Gender.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    public static void run() {
        // java  com.binlee.Main
        final String[] args = {
                "java",
                "-javaagent:/home/binlee/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/202.7319.50/lib/idea_rt.jar=37227:/home/binlee/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/202.7319.50/bin",
                "-Dfile.encoding=UTF-8",
                "-classpath",
                "/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/charsets.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/cldrdata.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/dnsns.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/icedtea-sound.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/jaccess.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/localedata.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/nashorn.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunec.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunjce_provider.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/sunpkcs11.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/ext/zipfs.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jce.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/jsse.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/management-agent.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/resources.jar:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:/home/binlee/code/JavaCore/out/production/JavaCore",
                // "-Xbootclasspath:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar",
                // "-XDprocess.packages",
                // "-proc:only",
                // "-processor",
                "com.binlee.annotation.com.binlee.annotation.processor.Apt",
        };
        // main(args);
    }

    // public static void main(String[] args) {
    //     System.out.println(Arrays.toString(args));
    //     try {
    //         final Process process = Runtime.getRuntime().exec(args);
    //         final InputStream input = process.getInputStream();
    //         System.out.println("input: " + toString(input));
    //         final InputStream error = process.getErrorStream();
    //         System.out.println("error: " + toString(error));
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }
    //
    // private static String toString(InputStream input) throws IOException {
    //     final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //     final byte[] buffer = new byte[1024];
    //     int len;
    //     while ((len = input.read(buffer)) != -1) {
    //         baos.write(buffer, 0, len);
    //     }
    //     baos.flush();
    //     return baos.toString();
    // }
}
