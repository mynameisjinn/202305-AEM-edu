package com.nsc.core.annotation;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.spi.DisposalCallbackRegistry;
import org.apache.sling.models.spi.Injector;
import org.apache.sling.models.spi.injectorspecific.AbstractInjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.InjectAnnotationProcessor2;
import org.apache.sling.models.spi.injectorspecific.StaticInjectAnnotationProcessorFactory;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;

@Component(property = {Constants.SERVICE_RANKING + "=" + Integer.MIN_VALUE})
public class RequestParameterInjector implements Injector, StaticInjectAnnotationProcessorFactory {


    // Override 메소드 >>> Ctrl + O : 자동생성

    @Override
    public String getName() {
        return "request-parameter";
    }

    @Override
    public Object getValue(Object adaptable, String fieldName, Type type,
                           AnnotatedElement annotatedElement, DisposalCallbackRegistry disposalCallbackRegistry) {

        // sling 으로 upcasting 가능한지 확인
        // RequestParameter 값 받는지? -> return에 포인트걸고 debug 해보기 >>> filed 에 값이찍히는지 확인
        if(adaptable instanceof SlingHttpServletRequest && annotatedElement.isAnnotationPresent(RequestParameter.class)) {
            SlingHttpServletRequest request = (SlingHttpServletRequest) adaptable;
            return request.getParameter(fieldName);
        }

        return null;
    }
    @Override
    public InjectAnnotationProcessor2 createAnnotationProcessor(AnnotatedElement annotatedElement) {
        RequestParameter annotation = annotatedElement.getAnnotation(RequestParameter.class);
            if (annotation != null) {
                return new AnnotationProcessor(annotation);
            }
        return null;
    }

    private static class AnnotationProcessor extends AbstractInjectAnnotationProcessor2 {
        private final RequestParameter annotation;

        AnnotationProcessor(RequestParameter annotation) {this.annotation = annotation;}

        @Override
        public Boolean isOptional(){return annotation.optional();}

        @Override
        public InjectionStrategy getInjectionStrategy() {
            return this.annotation.optional() ? InjectionStrategy.OPTIONAL : InjectionStrategy.DEFAULT;
        }

    }
}
