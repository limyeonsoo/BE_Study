package com.example.demo;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.StepVerifierOptions;
import reactor.test.publisher.PublisherProbe;
import reactor.test.publisher.TestPublisher;
import reactor.util.context.Context;

import java.time.Duration;
import java.util.function.Supplier;

@SpringBootTest
class ReactorTestTests {
    Flux<Integer> oddFilter(Flux<Integer> data) {
        return data.filter( datum -> datum % 2 == 1);
    }

    public <T> Flux<T> appendBoomError(Flux<T> source) {
        return source.concatWith(Mono.error(new IllegalArgumentException("boom")));
    }

    @Test
    public void testAppendBoomError() {
        Flux<String> source = Flux.just("thing1", "thing2");

        StepVerifier.create(appendBoomError(source))
                .expectNext("thing1")
                .expectNext("thing2")
                .expectErrorMessage("boom")
                .verify();
    }

//    Supplier<Flux<Long>> supplier;
//    @Test
//    public void withVirtualTime() {
//        StepVerifier.withVirtualTime(supplier)
//                .thenAwait(Duration.ofSeconds(3600)) // 3,600 초가 지났다고 간주함
//                .expectNextCount(3600) .verifyComplete();
//    }
    @Test
    public void withVirtualTime() {
        StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofDays(1)))
                .expectSubscription() // (1)
                .expectNoEvent(Duration.ofDays(1)) // (2)
                .expectNext(0L) // (3)
                .verifyComplete(); // (4)
    }

    @Test
    public void context() {
        String key = "message";
        Mono<String> r = Mono.just("Hello")
                .flatMap( s -> Mono.subscriberContext()
                        .map( ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "World"));

        StepVerifier.create(r)
                .expectNext("Hello World")
                .verifyComplete();
    }

    @Test
    public void contextTest() {
        StepVerifier.create(Mono.just(1).map(i -> i + 10),
                StepVerifierOptions.create().withInitialContext(Context.of("foo", "bar")))
                .expectAccessibleContext() // --- Context 셋
                .contains("foo", "bar") // --- Context 셋
                .then() // --- Context 셋
                .expectNext(11) // --- Sequence 셋
                .verifyComplete();
    }

    @Test
    void contextLoads() {
        StepVerifier.create(Mono.just(1).map(i -> i + 10),
                StepVerifierOptions.create().withInitialContext(Context.of("foo", "bar"))) // (1)
                .expectAccessibleContext() // (2)
                .contains("foo", "bar") // (3)
                .then() // (4)
                .expectNext(11)
                .verifyComplete(); // (5)
    }

    @Test
    void testPublisherNoUse() {
        StepVerifier.create(oddFilter(Flux.just(1, 2, 3, 4, 5, 6)))
                .expectNext(1)
                .expectNext(3, 5)
                .verifyComplete();
    }

    @Test
    void testPublisherExample() {
        TestPublisher<Integer> testPublisher = TestPublisher.<Integer> create();

        StepVerifier.create(oddFilter(testPublisher.flux()))
                .then(() -> testPublisher.emit(11, 12, 13, 14, 15))
                .expectNext(11, 13, 15)
                .verifyComplete();
    }

    @Test
    void testPublisherCreateNoncompliant() {
//        TestPublisher<Integer> testPublisher = TestPublisher.<Integer> create();
//        TestPublisher<Integer> testPublisherNon = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW);
//
//        StepVerifier.create(testPublisher.flux())
//                .thenRequest(4)
//                .then(() -> testPublisher.emit(10, 11, 12))
//                .expectNext(10, 11, 12)
//                .verifyComplete();

        TestPublisher<Integer> publisher = TestPublisher.create();
//        TestPublisher<String> publisher = TestPublisher.createNoncompliant(TestPublisher.Violation.REQUEST_OVERFLOW);
        StepVerifier.create(publisher, 2)
                .thenRequest(Long.MAX_VALUE - 2)
                .then(() -> publisher.emit(10, 11, 12))
                .expectNext(10, 11, 12)
                .expectComplete()
                .verify();
    }

    private Mono<String> executeCommand(String command) {
        return Mono.just(command + " DONE");
    }
    public Mono<Void> processOrFallback(Mono<String> commandSource, Mono<Void> doWhenEmpty) {
        return commandSource
                .flatMap(command -> executeCommand(command).then())
                .switchIfEmpty(doWhenEmpty);
    }

    @Test
    void testPublisherProbe() {
        PublisherProbe<Void> probe = PublisherProbe.empty();
        StepVerifier.create(processOrFallback(Mono.empty(), probe.mono()))
                .verifyComplete();

        probe.assertWasSubscribed();
        probe.assertWasRequested();
        probe.assertWasNotCancelled();
    }

    /*

    given:
    TestPublisher<Long> testPublisher = TestPublisher.<Long> create();

    when:
    StepVerifier.create(gameIdFilter(testPublisher.flux())) // testPublisher를 flux로 전환
            .then({ -> testPublisher.emit(1L, 3L, 5L, 7L, 10L) }) // 원하는 값 emit
            .expectNext(1L, 3L, 7L) // filter 메서드를 통해 filter 된 emit 값 정상 확인
            .verifyComplete()

    then:
     */
}
