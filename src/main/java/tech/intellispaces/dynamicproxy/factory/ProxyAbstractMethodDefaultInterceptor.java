package tech.intellispaces.dynamicproxy.factory;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import tech.intellispaces.commons.exception.UnexpectedViolationException;

import java.lang.reflect.Method;

public class ProxyAbstractMethodDefaultInterceptor {

  ProxyAbstractMethodDefaultInterceptor() {}

  @RuntimeType
  public Object intercept(@Origin Method method) {
    throw UnexpectedViolationException.withMessage("Interceptor of abstract proxy method '{}' is not defined. Class {}",
        method.getName(), method.getDeclaringClass().getCanonicalName());
  }
}
