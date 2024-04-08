package intellispaces.dynamicproxy.bytebuddy.factory;

import com.google.auto.service.AutoService;
import intellispaces.dynamicproxy.factory.ProxyFactory;
import intellispaces.dynamicproxy.object.BaseWatcher;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * The bytebuddy implementation of the {@link ProxyFactory}.
 */
@AutoService(ProxyFactory.class)
public class ByteBuddyProxyFactory implements ProxyFactory {

  @Override
  @SuppressWarnings("unchecked")
  public <T> Class<T> createWatcherClass(Class<T> aClass) {
    return (Class<T>) new ByteBuddy()
        .subclass(BaseWatcher.class)
        .implement(aClass)
        .name(aClass.getCanonicalName() + "Watcher")
        .method(not(named("invokedMethods")).and(not(named("addInvokedMethod"))).and(not(named("reset"))))
        .intercept(MethodDelegation.to(new WatcherMethodInterceptor()))
        .make()
        .load(ByteBuddyProxyFactory.class.getClassLoader())
        .getLoaded();
  }
}
