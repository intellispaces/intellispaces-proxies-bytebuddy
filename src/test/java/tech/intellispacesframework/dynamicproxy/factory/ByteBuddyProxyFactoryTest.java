package tech.intellispacesframework.dynamicproxy.factory;

import tech.intellispacesframework.dynamicproxy.factory.ByteBuddyDynamicProxyFactory;
import tech.intellispacesframework.dynamicproxy.test.DynamicProxyTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ByteBuddyDynamicProxyFactory} class.
 */
public class ByteBuddyProxyFactoryTest {
  private final ByteBuddyDynamicProxyFactory factory = new ByteBuddyDynamicProxyFactory();

  @Test
  public void testTracker_whenInterface() {
    DynamicProxyTest.testTracker_whenInterface(factory);
  }
}
