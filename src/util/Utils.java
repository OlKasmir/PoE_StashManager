package util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;

public final class Utils {
  public static Dimension getInnerSize(Frame frame) {
    Dimension size = frame.getSize();
    Insets insets = frame.getInsets();
    if (insets != null) {
        size.height -= insets.top + insets.bottom;
        size.width -= insets.left + insets.right;
    }
    return size;
  }
  
  private static Insets defaultInsets;
  public static Insets getInsetsWithDefault(Frame frame) {
      // insets only correct after pack() and setVisible(true) has been
      // called, so we use some fallback strategies
      Insets insets = frame.getInsets();
      if (insets.top == 0) {
          insets = defaultInsets;
          if (insets == null) {
              insets = new Insets(26, 3, 3, 3);
              // usual values for windows as our last resort
              // but only as long as we never saw any real insets
          }
      } else if (defaultInsets == null) {
          defaultInsets = (Insets) insets.clone();
      }
      return insets;
  }
}
