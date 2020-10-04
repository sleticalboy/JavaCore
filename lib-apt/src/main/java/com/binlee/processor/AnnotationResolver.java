package com.binlee.processor;

import com.binlee.annotation.Factory;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import java.util.Map;

/**
 * @author binlee sleticalboy@gmail.com
 * created by IDEA on 2020/10/4
 */
public final class AnnotationResolver {

    public static void factory(Map<String, FactoryItem> map, TypeElement element) {
        if (element.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new IllegalStateException(element + " is abstract.");
        }
        if (!element.getModifiers().contains(Modifier.PUBLIC)) {
            throw new IllegalStateException(element + " is not public.");
        }
        final Factory factory = element.getAnnotation(Factory.class);
        String fullName;
        try {
            // 如果该类已编译，此处会正常执行，否则将抛出以下异常
            fullName = factory.type().getCanonicalName();
            // simpleName = type.getSimpleName();
        } catch (MirroredTypeException e) {
            // log("resolveFactory() catch " + e.getClass().getName() + ", fallback now.");
            TypeElement typeElement = (TypeElement) ((DeclaredType) e.getTypeMirror()).asElement();
            fullName = typeElement.getQualifiedName().toString();
            // simpleName = typeElement.getSimpleName().toString();
        }
        FactoryItem items = map.get(fullName);
        if (items == null) {
            items = new FactoryItem(fullName);
            map.put(fullName, items);
        }
        items.children.add(FactoryItem.impl(factory.name(), element.getQualifiedName().toString()));
    }
}
