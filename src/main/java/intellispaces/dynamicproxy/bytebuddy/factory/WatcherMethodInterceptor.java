package intellispaces.dynamicproxy.bytebuddy.factory;

import intellispaces.dynamicproxy.object.BaseWatcher;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class WatcherMethodInterceptor {

  @RuntimeType
  public Object intercept(@This BaseWatcher watcher, @Origin Method method, @AllArguments Object[] arguments) {
    watcher.addInvokedMethod(method);
    return null;
  }
}
