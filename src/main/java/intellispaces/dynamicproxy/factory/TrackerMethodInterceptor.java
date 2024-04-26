package intellispaces.dynamicproxy.factory;

import intellispaces.dynamicproxy.tracker.BasicTracker;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.This;

import java.lang.reflect.Method;

public class TrackerMethodInterceptor {

  @RuntimeType
  public Object intercept(@This BasicTracker tracker, @Origin Method method, @AllArguments Object[] arguments) {
    tracker.addInvokedMethod(method);
    return null;
  }
}
