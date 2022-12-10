import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class DeclarativeProgrammingTest {

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
    // 직접적인 알고리즘과 동작 방식을 제시하지 않고 선언만 하는 선언형 프로그래밍
    final int actual =
        elements.stream().filter(element -> element != 0).mapToInt(element -> element).sum();

    // then
    Assertions.assertEquals(expected, actual);
  }
}
