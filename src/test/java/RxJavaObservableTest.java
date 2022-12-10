import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class RxJavaObservableTest {

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
    final AtomicInteger actual = new AtomicInteger();

    Observable.fromIterable(elements)
        .doOnNext(
            element ->
                System.out.println(
                    " check element : " + element)) // Overable이 아이템을 발행할 때 호출하는 함수 선언
        .subscribeOn(Schedulers.io()) //  (기본 옵션일 경우) 발행 단계에서 처리하는 thread를 지정
        .observeOn(Schedulers.computation()) // (기본 옵션일 경우) 구독해서 처리하는 thread를 지정
        .filter(element -> element != 0)
        .subscribe(actual::addAndGet);

    sleepThrowIgnore(1000, TimeUnit.MILLISECONDS);

    // then
    Assertions.assertEquals(expected, actual.get());
  }

  void sleepThrowIgnore(final int sleep, final TimeUnit timeUnit) {
    try {
      timeUnit.sleep(sleep);
    } catch (final Exception ignored) {
    }
  }
}
