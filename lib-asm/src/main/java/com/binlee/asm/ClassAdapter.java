package com.binlee.asm;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/8
 */
public final class ClassAdapter extends ClassVisitor implements Opcodes {

    private static final String BASE_DIR = "/home/binli/code/github/java/JavaCore";

    public ClassAdapter(int api) {
        super(Opcodes.ASM7);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    public static void main(String... args) {
        // generateClassFile();
        final String cls = "com/binlee/design/singleton/ServiceManager.class";
        try {
            // 将字节数组或者 class 文件读取到内存中，并以 tree 的数据结构表示，tree 的每个节点代表 class 文件中的某个区域
            // ClassReader 可以视为 visitor 模式中的访问者的实现类
            final ClassReader reader = new ClassReader(new FileInputStream(BASE_DIR + "/app/build/classes/java/main/" + cls));
            // 继承于 ClassVisitor，用于生成字节码，并以责任链的模式提供给 ClassReader 使用
            final ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            final ClassVisitor singletonVisitor = new SingletonVisitor(writer);
            reader.accept(singletonVisitor, 0);

            // /lib-asm/build/classes/java/main/com/binlee/asm/ServiceManager.class
            final File dest = new File(BASE_DIR + "/lib-asm/build/classes/java/main/com/binlee/asm/ServiceManager.class");
            if (dest.exists()) {
                dest.delete();
            }
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            final FileOutputStream fos = new FileOutputStream(dest, false);
            fos.write(writer.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("visitMethod() access: " + access + ", name: " + name + ", descriptor: " + descriptor + ", signature: " + signature + ", exceptions: " + exceptions + "");
            final MethodVisitor pre = super.visitMethod(access, name, descriptor, signature, exceptions);
            if ("<init>".equals(name) && "()V".equals(descriptor)) {
                return new ConstructorAdapter(pre, access, name, descriptor, signature, exceptions);
            }
            // if ("addService".equals(name) || "getService".equals(name)) {
            //     return null;
            // }
            Type.getDescriptor(String.class);
            return pre;
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            cv.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
        }
    }

    private static final class ConstructorAdapter extends MethodVisitor {

        public ConstructorAdapter(MethodVisitor pre, int access, String name, String descriptor, String signature, String[] exceptions) {
            super(ASM7, pre);
        }

        @Override
        public void visitCode() {
            System.out.println("visitCode() called");
            super.visitCode();
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            System.out.println("visitMethodInsn() called with: opcode = [" + opcode + "], owner = [" + owner + "], name = [" + name + "], descriptor = [" + descriptor + "], isInterface = [" + isInterface + "]");
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        @Override
        public void visitEnd() {
            System.out.println("visitEnd() called");
            super.visitEnd();
        }
    }

    private static void generateClassFile() {
        // final ClassWriter writer = new ClassWriter(0);
        // writer.visit(); 该方法是当扫描类时第一个调用的方法，主要用于类声明使用
        // 方法参数( 类版本 , 修饰符 , 类名 , 泛型信息 , 继承的父类 , 实现的接口)
        // writer.visitSource();
        // writer.visitOuterClass();
        // writer.visitAnnotation(); 该方法是当扫描器扫描到类注解声明时进行调用
        // 方法参数(注解类型 , 注解是否可以在 JVM 中可见)
        // writer.visitTypeAnnotation();
        // writer.visitAttribute();
        // writer.visitInnerClass();
        // writer.visitField(); 该方法是当扫描器扫描到类中字段时进行调用
        // 方法参数(修饰符 , 字段名 , 字段类型 , 泛型描述 , 默认值)
        // writer.visitMethod(); 该方法是当扫描器扫描到类的方法时进行调用
        // 方法参数(修饰符 , 方法名 , 方法签名 , 泛型信息 , 抛出的异常)
        // writer.visitEnd(); 该方法是当扫描器完成类扫描时才会调用
        final ClassWriter writer = new ClassWriter(0);
        writer.visit(V1_8, ACC_PUBLIC | ACC_FINAL, "com/binlee/asm/gen/ServiceManager", null, null, null);
        writer.visitEnd();
        final byte[] data = writer.toByteArray();
        System.out.println("data: " + Arrays.toString(data));
        try {
            final File dest = new File(BASE_DIR + "/lib-asm/build/com/binlee/asm/gen/ServiceManager.class");
            if (!dest.exists()) {
                dest.getParentFile().mkdirs();
            }
            final FileOutputStream fos = new FileOutputStream(dest, false);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
