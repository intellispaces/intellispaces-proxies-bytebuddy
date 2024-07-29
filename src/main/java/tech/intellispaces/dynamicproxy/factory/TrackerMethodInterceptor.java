package tech.intellispaces.dynamicproxy.factory;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import tech.intellispaces.commons.type.TypeFunctions;

import java.lang.reflect.Method;

public class TrackerMethodInterceptor {

  TrackerMethodInterceptor() {}

  @RuntimeType
  public Object intercept(@This TrackedObject trackedObject, @Origin Method method, @AllArguments Object[] arguments) {
    trackedObject.___tracker().addInvokedMethod(method);
    return TypeFunctions.getDefaultValueOf(method.getReturnType());
  }
}
