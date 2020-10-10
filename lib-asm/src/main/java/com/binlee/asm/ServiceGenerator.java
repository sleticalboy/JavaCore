package com.binlee.asm;

import org.objectweb.asm.*;
import org.objectweb.asm.util.ASMifier;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author binli sleticalboy@gmail.com
 * created by IDEA 2020/10/10
 */
public class ServiceGenerator implements Opcodes {

    public static void generate(String file) throws Exception {
        final File dest = new File(file);
        if (dest.exists()) {
            System.out.println("delete " + file + ": " + dest.delete());
            System.out.println("create new " + file + ": " + dest.createNewFile());
        }
        final FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(dump());
        fos.close();
        final Class<?> clazz = Thread.currentThread().getContextClassLoader()
                .loadClass("com.binlee.design.singleton.ServiceManager");
        System.out.println("redefinedClass -> " + clazz);
        // final Method method = clazz.getDeclaredMethod("getInstance");
        // final Object obj = method.invoke(null);
        // System.out.println("getInstance(): " + obj);
        ASMifier.main(new String[]{"-debug", clazz.getCanonicalName()});
    }

    private static byte[] dump() {

        ClassWriter cw = new ClassWriter(0);
        FieldVisitor fv;
        MethodVisitor mv;

        // 生成 class 版本信息等
        // class version 52.0 (52)
        // access flags 0x31
        cw.visit(V1_8, ACC_PUBLIC | ACC_FINAL | ACC_SUPER, "com/binlee/design/singleton/ServiceManager", null,
                "java/lang/Object", null);

        // compiled from: ServiceManager.java
        cw.visitSource("ServiceManager.java", null);

        // 添加注解 @Singleton
        {
            // @Lcom/binlee/annotation/Singleton;() // invisible
            final AnnotationVisitor av0 = cw.visitAnnotation("Lcom/binlee/annotation/Singleton;", false);
            av0.visitEnd();
        }
        // 添加内部类 SingletonH
        // access flags 0x1008
        // static synthetic INNERCLASS com/binlee/design/singleton/ServiceManager$1 null null
        cw.visitInnerClass("com/binlee/design/singleton/ServiceManager$1", null, null, ACC_STATIC | ACC_SYNTHETIC);
        // access flags 0x1A
        //   private final static INNERCLASS com/binlee/design/singleton/ServiceManager$SingletonH com/binlee/design/singleton/ServiceManager SingletonH
        cw.visitInnerClass("com/binlee/design/singleton/ServiceManager$SingletonH",
                "com/binlee/design/singleton/ServiceManager", "SingletonH", ACC_PRIVATE | ACC_FINAL | ACC_STATIC);

        // 生成字段 private final Map<String, Class<?>> mService;
        // access flags 0x12
        // signature Ljava/util/Map<Ljava/lang/String;Ljava/lang/Class<*>;>;
        // declaration: java.util.Map<java.lang.String, java.lang.Class<?>>
        // private final Ljava/util/Map; mServices
        {
            fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "mServices", "Ljava/util/Map;",
                    "Ljava/util/Map<Ljava/lang/String;Ljava/lang/Class<*>;>;", null);
            fv.visitEnd();
        }
        // 生成构造器 public ServiceManager() {}
        {
            // 生成构造器
            //   access flags 0x2
            //   private <init>()V
            mv = cw.visitMethod(ACC_PRIVATE, "<init>", "()V", null, null);
            mv.visitCode();

            //  L0
            //     LINENUMBER 21 L0
            //     ALOAD 0
            //     INVOKESPECIAL java/lang/Object.<init> ()V
            Label l0 = new Label();
            // 访问标签，标签指示紧随其后将要访问的指令
            mv.visitLabel(l0);
            // 访问行号
            mv.visitLineNumber(21, l0);
            // 访问局部变量指令: ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE, FSTORE, DSTORE, ASTORE or RET
            mv.visitVarInsn(ALOAD, 0);
            // 访问方法指令：INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE
            // super(); Object.<init>()
            mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

            //   L1
            //     LINENUMBER 15 L1
            //     ALOAD 0
            //     NEW java/util/concurrent/ConcurrentHashMap
            //     DUP
            //     INVOKESPECIAL java/util/concurrent/ConcurrentHashMap.<init> ()V
            //     PUTFIELD com/binlee/design/singleton/ServiceManager.mServices : Ljava/util/Map;
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLineNumber(15, l1);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitTypeInsn(NEW, "java/util/concurrent/ConcurrentHashMap");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "java/util/concurrent/ConcurrentHashMap", "<init>", "()V", false);
            mv.visitFieldInsn(PUTFIELD, "com/binlee/design/singleton/ServiceManager", "mServices", "Ljava/util/Map;");

            //   L2
            //     LINENUMBER 22 L2
            //     RETURN
            Label l2 = new Label();
            mv.visitLabel(l2);
            mv.visitLineNumber(22, l2);
            mv.visitInsn(RETURN);

            //   L3
            //     LOCALVARIABLE this Lcom/binlee/design/singleton/ServiceManager; L0 L3 0
            //     MAXSTACK = 3
            //     MAXLOCALS = 1
            Label l3 = new Label();
            mv.visitLabel(l3);
            mv.visitLocalVariable("this", "Lcom/binlee/design/singleton/ServiceManager;", null, l0, l3, 0);
            mv.visitMaxs(3, 1);
            mv.visitEnd();
        }
        // 生成单例方法 getInstance()
        {
            // access flags 0x9
            // public static getInstance()Lcom/binlee/design/singleton/ServiceManager;
            mv = cw.visitMethod(ACC_PUBLIC | ACC_STATIC, "getInstance",
                    "()Lcom/binlee/design/singleton/ServiceManager;", null, null);
            mv.visitCode();
            //  L0
            //     LINENUMBER 25 L0
            //     INVOKESTATIC com/binlee/design/singleton/ServiceManager$SingletonH.access$100 ()Lcom/binlee/design/singleton/ServiceManager;
            //     ARETURN
            //     MAXSTACK = 1
            //     MAXLOCALS = 0
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(25, l0);
            mv.visitMethodInsn(INVOKESTATIC, "com/binlee/design/singleton/ServiceManager$SingletonH", "access$100",
                    "()Lcom/binlee/design/singleton/ServiceManager;", false);
            mv.visitInsn(ARETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        // 生成公共方法 addService()
        {
            mv = cw.visitMethod(ACC_PUBLIC, "addService", "(Ljava/lang/String;Ljava/lang/Class;)V",
                    "(Ljava/lang/String;Ljava/lang/Class<*>;)V", null);
            mv.visitCode();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, null);
            Label l3 = new Label();
            mv.visitTryCatchBlock(l2, l3, l2, null);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(29, l4);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "com/binlee/design/singleton/ServiceManager", "mServices", "Ljava/util/Map;");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ASTORE, 3);
            mv.visitInsn(MONITORENTER);
            mv.visitLabel(l0);
            mv.visitLineNumber(30, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "com/binlee/design/singleton/ServiceManager", "mServices", "Ljava/util/Map;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "putIfAbsent",
                    "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitInsn(POP);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLineNumber(31, l5);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInsn(MONITOREXIT);
            mv.visitLabel(l1);
            Label l6 = new Label();
            mv.visitJumpInsn(GOTO, l6);
            mv.visitLabel(l2);
            mv.visitFrame(Opcodes.F_FULL, 4, new Object[]{"com/binlee/design/singleton/ServiceManager",
                    "java/lang/String", "java/lang/Class", "java/lang/Object"}, 1, new Object[]{"java/lang/Throwable"});
            mv.visitVarInsn(ASTORE, 4);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInsn(MONITOREXIT);
            mv.visitLabel(l3);
            mv.visitVarInsn(ALOAD, 4);
            mv.visitInsn(ATHROW);
            mv.visitLabel(l6);
            mv.visitLineNumber(32, l6);
            mv.visitFrame(Opcodes.F_CHOP, 1, null, 0, null);
            mv.visitInsn(RETURN);
            Label l7 = new Label();
            mv.visitLabel(l7);
            mv.visitLocalVariable("this", "Lcom/binlee/design/singleton/ServiceManager;", null, l4, l7, 0);
            mv.visitLocalVariable("key", "Ljava/lang/String;", null, l4, l7, 1);
            mv.visitLocalVariable("service", "Ljava/lang/Class;", "Ljava/lang/Class<*>;", l4, l7, 2);
            mv.visitMaxs(3, 5);
            mv.visitEnd();
        }
        // 生成公共方法 getService()
        {
            mv = cw.visitMethod(ACC_PUBLIC, "getService", "(Ljava/lang/String;)Ljava/lang/Class;",
                    "(Ljava/lang/String;)Ljava/lang/Class<*>;", null);
            mv.visitCode();
            Label l0 = new Label();
            Label l1 = new Label();
            Label l2 = new Label();
            mv.visitTryCatchBlock(l0, l1, l2, null);
            Label l3 = new Label();
            mv.visitTryCatchBlock(l2, l3, l2, null);
            Label l4 = new Label();
            mv.visitLabel(l4);
            mv.visitLineNumber(35, l4);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "com/binlee/design/singleton/ServiceManager", "mServices", "Ljava/util/Map;");
            mv.visitInsn(DUP);
            mv.visitVarInsn(ASTORE, 2);
            mv.visitInsn(MONITORENTER);
            mv.visitLabel(l0);
            mv.visitLineNumber(36, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "com/binlee/design/singleton/ServiceManager", "mServices", "Ljava/util/Map;");
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
            mv.visitTypeInsn(CHECKCAST, "java/lang/Class");
            mv.visitVarInsn(ALOAD, 2);
            mv.visitInsn(MONITOREXIT);
            mv.visitLabel(l1);
            mv.visitInsn(ARETURN);
            mv.visitLabel(l2);
            mv.visitLineNumber(37, l2);
            mv.visitFrame(Opcodes.F_FULL, 3, new Object[]{"com/binlee/design/singleton/ServiceManager",
                    "java/lang/String", "java/lang/Object"}, 1, new Object[]{"java/lang/Throwable"});
            mv.visitVarInsn(ASTORE, 3);
            mv.visitVarInsn(ALOAD, 2);
            mv.visitInsn(MONITOREXIT);
            mv.visitLabel(l3);
            mv.visitVarInsn(ALOAD, 3);
            mv.visitInsn(ATHROW);
            Label l5 = new Label();
            mv.visitLabel(l5);
            mv.visitLocalVariable("this", "Lcom/binlee/design/singleton/ServiceManager;", null, l4, l5, 0);
            mv.visitLocalVariable("key", "Ljava/lang/String;", null, l4, l5, 1);
            mv.visitMaxs(2, 4);
            mv.visitEnd();
        }
        // 生成静态内部类构造方法
        {
            // access flags 0x1000
            // synthetic <init>(Lcom/binlee/design/singleton/ServiceManager$1;)V
            mv = cw.visitMethod(ACC_SYNTHETIC, "<init>", "(Lcom/binlee/design/singleton/ServiceManager$1;)V",
                    null, null);
            mv.visitCode();
            //  L0
            //     LINENUMBER 13 L0
            //     ALOAD 0
            //     INVOKESPECIAL com/binlee/design/singleton/ServiceManager.<init> ()V
            //     RETURN
            Label l0 = new Label();
            mv.visitLabel(l0);
            mv.visitLineNumber(13, l0);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESPECIAL, "com/binlee/design/singleton/ServiceManager", "<init>", "()V", false);
            mv.visitInsn(RETURN);
            //  L1
            //     LOCALVARIABLE this Lcom/binlee/design/singleton/ServiceManager; L0 L1 0
            //     LOCALVARIABLE x0 Lcom/binlee/design/singleton/ServiceManager$1; L0 L1 1
            //     MAXSTACK = 1
            //     MAXLOCALS = 2
            Label l1 = new Label();
            mv.visitLabel(l1);
            mv.visitLocalVariable("this", "Lcom/binlee/design/singleton/ServiceManager;", null, l0, l1, 0);
            mv.visitLocalVariable("x0", "Lcom/binlee/design/singleton/ServiceManager$1;", null, l0, l1, 1);
            mv.visitMaxs(1, 2);
            mv.visitEnd();
        }
        cw.visitEnd();

        return cw.toByteArray();
    }
}

