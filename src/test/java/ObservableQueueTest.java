import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

class ObservableQueueTest {

  @Test
  void shouldPassWhenAllDataIsSubscribedIn1Second() throws InterruptedException {
    // given
    final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    queue.put(5);
    queue.put(5);
    queue.put(3);
    queue.put(2);
    queue.put(0);
    queue.put(5);

    final int expected = queue.size();
    final List<Integer> elements = new LinkedList<>();

    // when
    Observable.fromIterable(queue)
        .doOnNext(
            element -> {
              System.out.println(element);
              System.out.println(Thread.currentThread().getName());
            })
        // .observeOn(Schedulers.trampoline()) // 단일 큐를 사용해 순차 처리를 하고 싶다면  trampoline()
        .observeOn(Schedulers.computation()) // 쓰레드 풀을 미리 생성하고 필요할 때마다 쓰레드를 참조하고 싶다면 computation()
        .subscribe(
            element -> {
              System.out.println(element);
              System.out.println(Thread.currentThread().getName());
              elements.add(element);
            });

    sleepThrowIgnore(1, TimeUnit.SECONDS);

    // then
    final int actual = elements.size();
    Assertions.assertEquals(expected, actual);
  }

  @Test
  void shouldPassWhenDynamicCreationElementIsNotSubscribedIn2Second() throws InterruptedException {
    // given
    final BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
    // when
    Observable.fromIterable(queue)
        .doOnNext(
            element -> {
              System.out.println(element);
              System.out.println(Thread.currentThread().getName());
            })
        // .observeOn(Schedulers.trampoline()) // 단일 큐를 사용해 순차 처리를 하고 싶다면  trampoline()
        .observeOn(Schedulers.computation()) // 쓰레드 풀을 미리 생성하고 필요할 때마다 쓰레드를 참조하고 싶다면 computation()
        .subscribe(
            element -> {
              System.out.println(element);
              System.out.println(Thread.currentThread().getName());
            });

    for (int i = 0; i < 10; i++) {
      queue.put(i);
      System.out.println("publish : " + i);
    }

    sleepThrowIgnore(2, TimeUnit.SECONDS);

    // then
    Assertions.assertFalse(queue.isEmpty()); // Observable 생성 후에 데이터가 삽입됐기 때문에 subscribe 할 수 없다.
  }

  void sleepThrowIgnore(final int sleep, final TimeUnit timeUnit) {
    try {
      timeUnit.sleep(sleep);
    } catch (final Exception ignored) {
    }
  }
}
