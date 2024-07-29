package tech.intellispaces.dynamicproxy.factory;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;
import tech.intellispaces.dynamicproxy.proxy.contract.CommonMethodHandler;

import java.lang.reflect.Method;

public class ProxyMethodCommonInterceptor {
  private final CommonMethodHandler handler;

  ProxyMethodCommonInterceptor(CommonMethodHandler handler) {
    this.handler = handler;
  }

  @RuntimeType
  public Object intercept(@This Object object, @Origin Method method, @AllArguments Object[] arguments) throws Exception {
    return handler.handle(object, method, arguments);
  }
}
