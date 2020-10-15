package com.binlee.processor;

import com.binlee.annotation.Factory;
import com.binlee.processor.util.Utils;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/9/28
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class Apt extends AbstractProcessor {

    private final static List<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS =
            new ArrayList<>(Arrays.asList(
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
        Utils.log(processingEnv, new Exception("init() -------------------->"));
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
        Utils.log(processingEnv, new Exception("process() -------------------->"));
        // fix: File for type 'xxx' created in the last round will not be subject to annotation
        // processing.
        processImpl(annotations, roundEnv);
        FileGenerator.demo(processingEnv, getClass().getName());
        FileGenerator.factories(processingEnv, mItemMap);
        return true;
    }

    private void processImpl(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        Utils.log(processingEnv, "processImpl() start with: " + annotations);
        for (Class<? extends Annotation> a : SUPPORTED_ANNOTATIONS) {
            final Set<? extends Element> elements = env.getElementsAnnotatedWith(a);
            Utils.log(processingEnv, "processImpl() elements for: " + a + " " + elements);
            if (elements == null || elements.size() == 0) {
                continue;
            }
            for (final Element e : elements) {
                Utils.log(processingEnv, "processImpl() -> " + Utils.dumpObj(e) + ", kind: " + e.getKind());
                if (e.getKind() == ElementKind.CLASS) {
                    final Annotation annotation = e.getAnnotation(a);
                    if (annotation instanceof Factory) {
                        AnnotationResolver.factory(mItemMap, (TypeElement) e);
                    }
                }
            }
        }
        Utils.log(processingEnv, "processImpl() end with: " + mItemMap);
    }
}
