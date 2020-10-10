package com.binlee.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.ASM7;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/10
 */
public class MethodEnhancer {

    public static void enhance(String baseDir) throws Exception {
        final String cls = "com/binlee/design/singleton/ServiceManager.class";
        // 将字节数组或者 class 文件读取到内存中，并以 tree 的数据结构表示，tree 的每个节点代表 class 文件中的某个区域
        // ClassReader 可以视为 visitor 模式中的访问者的实现类
        final ClassReader reader = new ClassReader(new FileInputStream(baseDir + "/app/build/classes/java/main/" + cls));
        // 继承于 ClassVisitor，用于生成字节码，并以责任链的模式提供给 ClassReader 使用
        final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        final ClassVisitor singletonVisitor = new SingletonVisitor(writer);
        reader.accept(singletonVisitor, 0);

        // /lib-asm/build/classes/java/main/com/binlee/asm/ServiceManager.class
        final File dest = new File(baseDir + "/lib-asm/build/classes/java/main/com/binlee/asm/ServiceManager.class");
        if (dest.exists()) {
            dest.delete();
        }
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(dest, false);
        fos.write(writer.toByteArray());
        fos.close();
    }

    private static final class SingletonVisitor extends ClassVisitor {

        public SingletonVisitor(ClassVisitor classVisitor) {
            super(ASM7, classVisitor);
        }

        @Override
        public void visitSource(String source, String debug) {
            System.out.println("visitSource() source: " + source + ", debug: " + debug + "");
            super.visitSource(source, debug);
        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {
            System.out.println("visitOuterClass() called with: owner = [" + owner + "], name = [" + name + "], descriptor = [" + descriptor + "]");
            super.visitOuterClass(owner, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            System.out.println("visitAnnotation() descriptor: " + descriptor + ", visible: " + visible + "");
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
            System.out.println("visitTypeAnnotation() typeRef: " + typeRef + ", typePath: " + typePath + ", descriptor: " + descriptor + ", visible: " + visible + "");
            return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
        }

        @Override
        public void visitAttribute(Attribute attribute) {
            System.out.println("visitAttribute() called with: attribute = [" + attribute + "]");
            super.visitAttribute(attribute);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            System.out.println("visitInnerClass() called with: name = [" + name + "], outerName = [" + outerName + "], innerName = [" + innerName + "], access = [" + access + "]");
            super.visitInnerClass(name, outerName, innerName, access);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            System.out.println("visitField() called with: access = [" + access + "], name = [" + name + "], descriptor = [" + descriptor + "], signature = [" + signature + "], value = [" + value + "]");
            return super.visitField(access, name, descriptor, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("visitMethod() access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions: " + exceptions + "");
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        @Override
        public void visitEnd() {
            System.out.println("visitEnd() called");
            super.visitEnd();
        }
    }
}
