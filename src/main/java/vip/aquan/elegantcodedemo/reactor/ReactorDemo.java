package vip.aquan.elegantcodedemo.reactor;

import groovy.lang.Tuple2;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;
import vip.aquan.elegantcodedemo.entity.Student;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

public class ReactorDemo {
    public static void main(String[] args) throws InterruptedException {
        delaySubscription();
//        errorRetry();
//        fluxNext();
//        tupleOperation();
    }


    //延迟订阅，3秒后才执行
    private static void delaySubscription() throws InterruptedException {
        Mono.create((sink -> {
            System.out.println("Mono Operation");
            sink.success("ok");
        })).publishOn(Schedulers.parallel()).subscribeOn(Schedulers.parallel()).delaySubscription(Duration.ofSeconds(3))
                .subscribe(x -> System.out.println("subscribe complete:" + x));
        System.out.println("=============");
        Thread.currentThread().join();
    }

    //异常重试
    private static void errorRetry() throws InterruptedException {
        Mono.create(sink -> {
            System.out.println("Mono Operation");
            throw new NullPointerException("空指针");
        })
                .doOnError(error -> System.out.println("错误: " + error))
//                .onErrorReturn("ErrorReturn")
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))) //每秒重试一次，共三次
                .doOnError(error2 -> System.out.println("错误2: " + error2))
                .subscribe(x -> {
                    System.out.println("mono:" + x);
                });
        Thread.currentThread().join();
    }

    //Flux.subscribe在做监听, FluxSink每一次next执行一次
    private static void fluxNext() throws InterruptedException {
        AtomicReference<FluxSink<Object>> sink = new AtomicReference<>();

        Flux.create(x -> sink.set(x)).subscribe(System.out::println);
        for (int i = 0; i < 5; i++) {
            sink.get().next("Next" + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sink.get().complete();
    }

    //Tuple元组
    private static void tupleOperation() {
        Tuple2<Integer, Student> tuple2 = new Tuple2<>(1, Student.builder().name("Mike").age(10).build());
        System.out.println("tuple2 V1:" + tuple2.getV1());
        System.out.println("tuple2 V2:" + tuple2.getV2());
    }


}
