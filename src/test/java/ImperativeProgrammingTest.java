import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class ImperativeProgrammingTest {

  @Test
  void shouldPassWhenSumOfElementsOfListEquals20() {

    final int expected = 20;

    // given
    final List<Integer> elements = new LinkedList<>();
    elements.add(5);
    elements.add(5);
    elements.add(3);
    elements.add(2);
    elements.add(0);
    elements.add(5);

    // when
    int actual = 0;

    // 직접적인 알고리즘과 동작 방식을 명시하는 명령형 프로그래밍
    for (final Integer element : elements) {
      if (element != 0) {
        actual += element;
      }
    }

    // then
    Assertions.assertEquals(expected, actual);
  }
}
