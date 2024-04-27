package tech.intellispacesframework.dynamicproxy.factory;

import com.google.auto.service.AutoService;
import tech.intellispacesframework.dynamicproxy.tracker.BasicTracker;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * The bytebuddy implementation of the {@link DynamicProxyFactory}.
 */
@AutoService(DynamicProxyFactory.class)
public class ByteBuddyDynamicProxyFactory implements DynamicProxyFactory {

  @Override
  @SuppressWarnings("unchecked")
  public <T> Class<T> createTrackerClass(Class<T> aClass) {
    return (Class<T>) new ByteBuddy()
        .subclass(BasicTracker.class)
        .implement(aClass)
        .name(aClass.getCanonicalName() + "Tracker")
        .method(not(named("getInvokedMethods")).and(not(named("addInvokedMethod"))).and(not(named("reset"))))
        .intercept(MethodDelegation.to(new TrackerMethodInterceptor()))
        .make()
        .load(ByteBuddyDynamicProxyFactory.class.getClassLoader())
        .getLoaded();
  }
}
