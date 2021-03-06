package com.binlee.processor;

import com.binlee.processor.util.Utils;
import com.squareup.javapoet.*;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Map;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
public final class FileGenerator {

    private static boolean sHasDemo = false;
    private static boolean sHasFactories = false;

    public static void demo(ProcessingEnvironment env, String tag) {
        if (sHasDemo) {
            return;
        }
        Utils.log(env, "generate demo...");
        sHasDemo = true;
        // fields
        final FieldSpec.Builder version = FieldSpec.builder(String.class, "mVersion")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL);
        // constructor
        final MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("mVersion = \"$N\"", tag);
        // methods
        final MethodSpec.Builder helloApt = MethodSpec.methodBuilder("helloApt")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addStatement("System.out.println(\"Hello \" + $N)", "mVersion");
        // build class
        TypeSpec.Builder clazz = TypeSpec.classBuilder("Hello$$Apt")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(constructor.build())
                .addField(version.build())
                .addMethod(helloApt.build());
        // build java file
        final JavaFile javaFile = JavaFile.builder("com.binlee.apt", clazz.build())
                .addFileComment("auto generated by Apt, do not modify.")
                .skipJavaLangImports(true)
                // 缩进，默认两个空格
                .indent("    ")
                .build();
        try {
            // write to file
            javaFile.writeTo(env.getFiler());
        } catch (IOException e) {
            Utils.logE(env, e);
        }
    }

    public static void factories(ProcessingEnvironment env, Map<String, FactoryItem> map) {
        if (sHasFactories) {
            return;
        }
        Utils.log(env, "generate factories...");
        sHasFactories = true;
        if (map.size() != 0) {
            for (String fullName : map.keySet()) {
                final FactoryItem items = map.get(fullName);
                if (items == null) {
                    continue;
                }
                generateFactory(env, items);
            }
        }
    }

    private static void generateFactory(ProcessingEnvironment env, @Nonnull FactoryItem items) {
        final TypeElement element = env.getElementUtils().getTypeElement(items.parent);
        final PackageElement pkg = env.getElementUtils().getPackageOf(element);
        String pkgName = pkg.isUnnamed() ? "com.binlee.apt" : pkg.getQualifiedName().toString();
        pkgName = pkgName.substring(0, pkgName.lastIndexOf('.'));
        Utils.log(env, "generateFactory() for: " + items + ", element: " + element + ", pkg: " + pkgName);
        // start build files
        final MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE);
        final MethodSpec.Builder create = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC/*, Modifier.STATIC*/)
                .addParameter(ParameterSpec.builder(String.class, "id").build())
                .returns(TypeName.get(element.asType()))
                // start control flow
                .beginControlFlow("if (id == null)")
                // throw new IllegalArgumentException("Unknown name: " + name)
                .addStatement("throw new IllegalArgumentException(\"Unexpected id: null\")")
                // end control flow
                .endControlFlow();
        // main code block start
        for (FactoryItem.Impl impl : items.children) {
            create.beginControlFlow("if (id.equals(\"$N\"))", impl.name)
                    .addStatement("return new $L()", impl.clazz)
                    .endControlFlow();
        }
        // main code block end
        // throw new IllegalArgumentException("Unknown id: " + id)
        create.addStatement("throw new IllegalArgumentException(\"Unknown id: \" + id)");


        String name = (element.getSimpleName() + "FactoryImpl_").substring(1);
        Utils.log(env, "factory name: " + name);
        TypeSpec.Builder clazz = TypeSpec.classBuilder(name)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                // .addOriginatingElement()
                .addMethod(constructor.build())
                .addMethod(create.build());
        final JavaFile javaFile = JavaFile.builder(pkgName/* + ".factory"*/, clazz.build())
                .addFileComment("Auto create by APT, do NOT modify.")
                .skipJavaLangImports(true)
                .indent("  ")
                .build();
        try {
            javaFile.writeTo(env.getFiler());
        } catch (IOException e) {
            Utils.logE(env, "generateFactory() error: " + e);
        }
    }
}
