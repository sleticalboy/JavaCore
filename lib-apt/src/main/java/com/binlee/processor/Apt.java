package com.binlee.processor;

import com.binlee.annotation.Factory;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.Nonnull;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class Apt extends AbstractProcessor {

    private final static List<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS = new ArrayList<>(Arrays.asList(
            Factory.class
    ));
    private final static Set<String> SUPPORTED_ANNOTATION_TYPES;

    static {
        SUPPORTED_ANNOTATION_TYPES = new HashSet<>();
        for (Class<?> a : SUPPORTED_ANNOTATIONS) {
            SUPPORTED_ANNOTATION_TYPES.add(a.getCanonicalName());
        }
    }

    private final Map<String, FactoryItem> mItemMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return SUPPORTED_ANNOTATION_TYPES;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            generateDemo();
            generateFactories();
            return false;
        } else {
            processImpl(annotations, roundEnv);
        }
        return true;
    }

    private void processImpl(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        log("processImpl() start with: " + annotations);
        for (Class<? extends Annotation> a : SUPPORTED_ANNOTATIONS) {
            final Set<? extends Element> elements = env.getElementsAnnotatedWith(a);
            log("processImpl() elements for: " + a + " " + elements);
            if (elements == null || elements.size() == 0) {
                continue;
            }
            for (final Element e : elements) {
                log("processImpl() -> " + e + ", kind: " + e.getKind());
                if (e.getKind() == ElementKind.CLASS) {
                    final Annotation annotation = e.getAnnotation(a);
                    if (annotation instanceof Factory) {
                        resolveFactory((TypeElement) e, ((Factory) annotation));
                    }
                }
            }
        }
        //
        log("processImpl() end with: " + mItemMap);
    }

    private void resolveFactory(TypeElement element, Factory factory) {
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new IllegalStateException(element + " is abstract.");
        }
        if (!element.getModifiers().contains(Modifier.PUBLIC)) {
            throw new IllegalStateException(element + " is not public.");
        }
        String fullName;
        try {
            // 如果该类已编译，此处会正常执行，否则将抛出以下异常
            fullName = factory.type().getCanonicalName();
            // simpleName = type.getSimpleName();
        } catch (MirroredTypeException e) {
            log("resolveFactory() catch " + e.getClass().getName() + ", fallback now.");
            final TypeElement typeElement = (TypeElement) ((DeclaredType) e.getTypeMirror()).asElement();
            fullName = typeElement.getQualifiedName().toString();
            // simpleName = typeElement.getSimpleName().toString();
        }
        FactoryItem items = mItemMap.get(fullName);
        if (items == null) {
            items = new FactoryItem(fullName);
            mItemMap.put(fullName, items);
        }
        items.children.add(FactoryItem.impl(factory.name(), element.getQualifiedName().toString()));
    }

    private void generateFactories() {
        if (mItemMap.size() != 0) {
            for (String fullName : mItemMap.keySet()) {
                final FactoryItem items = mItemMap.get(fullName);
                if (items == null) {
                    continue;
                }
                generateFactory(items);
            }
        }
    }

    private void generateFactory(@Nonnull FactoryItem items) {
        final Elements utils = processingEnv.getElementUtils();
        final TypeElement element = utils.getTypeElement(items.parent);
        final PackageElement pkg = utils.getPackageOf(element);
        final String pkgName = pkg.isUnnamed() ? "com.binlee.apt" : pkg.getQualifiedName().toString();
        log("generateFactory() for: " + items + ", element: " + element + ", pkg: " + pkgName);
        // start build files
        final MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE);
        final MethodSpec.Builder create = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(ParameterSpec.builder(String.class, "name").build())
                .returns(TypeName.get(element.asType()))
                // start control flow
                .beginControlFlow("if (name == null)")
                // throw new IllegalArgumentException("Unknown name: " + name)
                .addStatement("throw new IllegalArgumentException(\"Unexpected name: \" + name)")
                // end control flow
                .endControlFlow();
        // main code block start
        for (FactoryItem.Impl impl : items.children) {
            create.beginControlFlow("if (name.equals(\"$N\"))", impl.name)
                    .addStatement("return new $L()", impl.clazz)
                    .endControlFlow();
        }
        // main code block end
        // throw new IllegalArgumentException("Unknown name: " + name)
        create.addStatement("throw new IllegalArgumentException(\"Unknown name: \" + name)");

        TypeSpec.Builder clazz = TypeSpec.classBuilder("PersonFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(constructor.build())
                .addMethod(create.build());
        final JavaFile javaFile = JavaFile.builder(pkgName, clazz.build())
                .addFileComment("Auto create by APT, do NOT modify.")
                .skipJavaLangImports(true)
                .indent("  ")
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            logE("generateFactory() error: " + e);
        }
    }

    private void generateDemo() {
        // fields
        final FieldSpec.Builder version = FieldSpec.builder(String.class, "mVersion")
                .addModifiers(Modifier.PRIVATE, Modifier.FINAL);
        // constructor
        final MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("mVersion = \"$N\"", getClass().getName());
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
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            logE(e);
        }
    }

    private void logE(Object obj) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, extract(obj));
    }

    private void log(Object obj) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, extract(obj));
    }

    private String extract(Object obj) {
        return obj instanceof String ? ((String) obj) : obj == null ? "{null}" : obj.toString();
    }
}
