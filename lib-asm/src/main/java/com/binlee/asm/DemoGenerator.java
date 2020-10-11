package com.binlee.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.io.FileOutputStream;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/10
 */
public class DemoGenerator {

    public static void generate(String file) throws Exception {
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
        writer.visit(V1_8, ACC_PUBLIC | ACC_FINAL, "AutoGenDemo", null, null, null);
        writer.visitSource("AutoGenDemo.java", null);

        writer.visitField(ACC_PRIVATE, "mInt", "I", null, -1);
        writer.visitField(ACC_PRIVATE, "mLong", "J", null, -1L);
        writer.visitField(ACC_PRIVATE, "mShort", "S", null, -1);
        writer.visitField(ACC_PRIVATE, "mByte", "B", null, -1);
        writer.visitField(ACC_PRIVATE, "mChar", "S", null, -1);
        writer.visitField(ACC_PRIVATE, "mDouble", "D", null, -1);
        writer.visitField(ACC_PRIVATE, "mBool", "Z", null, false);
        writer.visitField(ACC_PRIVATE, "mFloat", "F", null, -1);
        // 基本数据类型可以不调用 FieldVisitor#visitEnd() 但是引用数据类型必须调用
        final FieldVisitor fv = writer.visitField(ACC_PRIVATE | ACC_FINAL, "mObj", "Ljava/lang/Object;", null, null);
        fv.visitEnd();

        MethodVisitor mv;
        // public void setInt(int value) {
        //     mInt = value;
        // }
        {
            mv = writer.visitMethod(ACC_PUBLIC, "setInt", "(I)V", null, null);
            mv.visitCode();
            final Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitFieldInsn(PUTFIELD, "AutoGenDemo", "mInt", "I");
            final Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitInsn(RETURN);
            final Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLocalVariable("this", "AutoGenDemo", null, l0, l2, 0);
            mv.visitLocalVariable("value", "I", null, l0, l2, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }

        // public void setIntIfNotEq(int value) {
        //     if (value != mInt) {
        //         mInt = value;
        //     }
        // }
        {
            mv = writer.visitMethod(ACC_PUBLIC, "setIntIfNotEq", "(I)V", null, null);
            mv.visitCode();
            //   L0
            //     LINENUMBER 21 L0
            //     ILOAD 1
            //     ALOAD 0
            //     GETFIELD AutoGenDemo.mInt : I
            //     IF_ICMPEQ L1
            final Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "AutoGenDemo", "mInt", "I");
            final Label l1 = new Label();
            mv.visitJumpInsn(IF_ICMPEQ, l1);
            //   L2
            //     ALOAD 0
            //     ILOAD 1
            //     PUTFIELD AutoGenDemo.mInt : I
            //    L1
            //    FRAME SAME
            //     RETURN
            final Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitVarInsn(ILOAD, 1);
            mv.visitFieldInsn(PUTFIELD, "AutoGenDemo", "mInt", "I");
            mv.visitLabel(l1);
            mv.visitFrame(F_SAME, 0, null, 0, null);
            mv.visitInsn(RETURN);
            //   L3
            //     LOCALVARIABLE this AutoGenDemo L0 L3 0
            //     LOCALVARIABLE value I L0 L3 1
            //     MAXSTACK = 2
            //     MAXLOCALS = 2
            final Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "AutoGenDemo", null, l0, l3, 0);
            mv.visitLocalVariable("value", "I", null, l0, l3, 1);
            mv.visitMaxs(2, 2);
            mv.visitEnd();
        }

        // public int getInt() {
        //     return mInt;
        // }
        {
            mv = writer.visitMethod(ACC_PUBLIC, "getInt", "()I", null, null);
            mv.visitCode();
            final Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "AutoGenDemo", "mInt", "I");
            mv.visitInsn(IRETURN);
            final Label l1 = new Label();
            mv.visitLocalVariable("this", "AutoGenDemo", null, l0, l1, 0);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        writer.visitEnd();
        System.out.println("generate() file: " + file);
        final FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(writer.toByteArray());
        fos.close();
    }
}
