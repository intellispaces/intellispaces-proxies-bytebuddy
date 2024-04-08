package ntellispaces.dynamicproxy.bytebuddy;

import intellispaces.dynamicproxy.bytebuddy.factory.ByteBuddyProxyFactory;
import intellispaces.dynamicproxy.test.DynamicProxyTestFunctions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link ByteBuddyProxyFactory} class.
 */
public class ByteBuddyProxyFactoryTest {
  private final ByteBuddyProxyFactory factory = new ByteBuddyProxyFactory();

  @Test
  public void testWatcher_whenInterface() {
    DynamicProxyTestFunctions.testWatcherForInterface(factory);
  }
}
