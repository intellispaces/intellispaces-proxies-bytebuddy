package tech.intellispaces.dynamicproxy.factory;

import tech.intellispaces.dynamicproxy.test.DynamicProxyTest;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ByteBuddyDynamicProxyFactory} class.
 */
public class ByteBuddyProxyFactoryTest {
  private final ByteBuddyDynamicProxyFactory factory = new ByteBuddyDynamicProxyFactory();

  @Test
  public void testCreateTrackedClass_whenInterface() {
    DynamicProxyTest.testCreateTrackedClass_whenInterface(factory);
  }

  @Test
  public void testCreateTrackedClass_whenAbstractClass() {
    DynamicProxyTest.testCreateTrackedClass_whenAbstractClass(factory);
  }

  @Test
  public void testCreateProxyClass_whenAbstractClass_andAbstractMethodHandler() {
    DynamicProxyTest.testCreateProxyClass_whenAbstractClass_andAbstractMethodHandler(factory);
  }

  @Test
  public void testCreateProxyClass_whenAbstractClass_andSpecificMethodHandler() {
    DynamicProxyTest.testCreateProxyClass_whenAbstractClass_andSpecificMethodHandler(factory);
  }
}
