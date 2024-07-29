package tech.intellispaces.dynamicproxy.factory;

import com.google.auto.service.AutoService;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodCall;
import tech.intellispaces.commons.exception.UnexpectedViolationException;
import tech.intellispaces.dynamicproxy.proxy.contract.MethodHandler;
import tech.intellispaces.dynamicproxy.proxy.contract.ProxyContract;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import tech.intellispaces.dynamicproxy.tracker.Tracker;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.bytebuddy.matcher.ElementMatchers.isAbstract;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * The bytebuddy implementation of the {@link DynamicProxyFactory}.
 */
@AutoService(DynamicProxyFactory.class)
public class ByteBuddyDynamicProxyFactory implements DynamicProxyFactory {

  @Override
  @SuppressWarnings("unchecked")
  public <T> Class<T> getTrackedClass(Class<T> aClass) {
    try {
      Class<?> subclass = aClass.isInterface() ? Object.class : aClass;
      List<Class<?>> implInterfaces = aClass.isInterface() ? List.of(TrackedObject.class, aClass) : List.of(TrackedObject.class);
      return (Class<T>) new ByteBuddy()
          .subclass(subclass, ConstructorStrategy.Default.NO_CONSTRUCTORS)
          .implement(implInterfaces)
          .defineField("___tracker", Tracker.class, Visibility.PRIVATE)
          .defineConstructor(Visibility.PUBLIC)
          .withParameters(Tracker.class)
          .intercept(MethodCall.invoke(subclass.getConstructor()).andThen(FieldAccessor.ofField("___tracker").setsArgumentAt(0)))
          .defineMethod( "___tracker", Tracker.class, Modifier.PUBLIC)
          .intercept(FieldAccessor.ofField("___tracker"))
          .method(not(named("___tracker")))
          .intercept(MethodDelegation.to(new TrackerMethodInterceptor()))
          .name(aClass.getCanonicalName() + "Tracked")
          .make()
          .load(ByteBuddyDynamicProxyFactory.class.getClassLoader())
          .getLoaded();
    } catch (NoSuchMethodException | SecurityException e) {
      throw UnexpectedViolationException.withCauseAndMessage(e, "Failed to create tracked class of {}", aClass.getCanonicalName());
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Class<T> getProxyClass(ProxyContract<T> contract) {
    DynamicType.Builder<T> proxyBuilder;
    if (contract.type().isInterface()) {
      List<Class<?>> implInterfaces = new ArrayList<>(1 + contract.additionalInterfaces().size());
      implInterfaces.add(contract.type());
      implInterfaces.addAll(contract.additionalInterfaces());
      proxyBuilder = (DynamicType.Builder<T>) new ByteBuddy()
          .subclass(Object.class)
          .implement(implInterfaces)
          .name(contract.className());
    } else {
      proxyBuilder = new ByteBuddy()
          .subclass(contract.type())
          .implement(contract.additionalInterfaces())
          .name(contract.className());
    }

    if (contract.abstractMethodHandler().isPresent()) {
      proxyBuilder = proxyBuilder
          .method(isAbstract())
          .intercept(MethodDelegation.to(new ProxyMethodCommonInterceptor(contract.abstractMethodHandler().get())));
    } else {
      proxyBuilder = proxyBuilder
          .method(isAbstract())
          .intercept(MethodDelegation.to(new ProxyAbstractMethodDefaultInterceptor()));
    }

    for (Map.Entry<Method, MethodHandler> entry : contract.methodHandlers().entrySet()) {
      Method method = entry.getKey();
      MethodHandler handler = entry.getValue();
      proxyBuilder = proxyBuilder
          .define(method)
          .intercept(MethodDelegation.to(new ProxyMethodInterceptor(handler)));
    }

    return (Class<T>) proxyBuilder.make()
        .load(ByteBuddyDynamicProxyFactory.class.getClassLoader())
        .getLoaded();
  }
}
