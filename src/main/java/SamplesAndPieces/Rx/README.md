
 [примеры на Rx1]
 // https://github.com/ReactiveX/RxJava/wiki/How-To-Use-RxJava

	String[] arr = {"asd", "foo"};
    rx.Observable.from(arr).forEach( System.out::println );


   [примеры на Rx2]
   https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
   https://dzone.com/articles/creating-an-observable-in-rxjava
   https://github.com/politrons/reactive/blob/master/src/test/java/rx/observables/scheduler/ObservableAsynchronous.java

		// А
		FlowableRangeLong flow = new FlowableRangeLong(1,10);

        flow
            .reduce( (a,b) -> a + b )
            .subscribe(
                    (u) -> {
                        System.out.println("V: " + u);
                    },
                    (e) -> {
                        System.out.println( e.getMessage() );
                    },
                    () -> {
                        System.out.println("STOP");
                    }
            );

		// Б - AsyncSubject хранит последнее значение
		AsyncSubject<Integer> s = AsyncSubject.create();
        s.onNext(1);
        s.onNext(2);
        s.subscribe(
                System.out::println,
                c -> {},
                () -> System.out.println("STOP"));
        s.onComplete();
		s.onNext(3);
		// 2

		// В
		Observable<String> o = Observable.just("a", "b", "c");
        o = Observable.empty();
        o = Observable.error(new Exception("Error"));
        o.subscribe(
                item -> {
                    System.out.println(item);
                },
                e -> {
                    System.out.println("Err: " + e.getMessage());
                },
                () -> {
                    System.out.println("STOP");
                }
        );
		// Err: Error

		// отложенный вызов. defer принимает функцию, которая будет выполнена для каждого нового подписчика
		Observable<Long> now = Observable.defer( () -> Observable.just( System.currentTimeMillis() ) );

        now.subscribe(System.out::println);
        Thread.sleep(1000);
        now.subscribe(System.out::println);
        // 1515330059688
        // 1515330060697

		// Аналогично предыдущему. Но создаем Observable вручную
        Observable<Long> obs = Observable.create(o -> {
            o.onNext(System.currentTimeMillis());
            o.onComplete();
        });

        obs.subscribe(System.out::println);
        sleep(1000);
        obs.subscribe(System.out::println);
		// 1515330688139
		// 1515330689140

		// Печатает бесконечную последовательность целых чисел, начиная с 0
		// До тех пор, пока не отписаться
		Observable<Long> values = Observable.interval(1000, TimeUnit.MILLISECONDS);

        disposable = values.subscribe(
                v -> System.out.println("Received: " + v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
        );
		System.in.read();

		// отписаться. Например, по нажатию кнопки
		disposable.dispose();

	[универсальные примеры с теорией на хабре]
	https://habrahabr.ru/post/281633/
	https://habrahabr.ru/post/270023/

    [tutorial]
    http://www.vogella.com/tutorials/RxJava/article.html

    [примеры]
    https://github.com/amitshekhariitbhu/RxJava2-Android-Samples

    [официальная документация с картинками]
    http://reactivex.io/documentation/operators/replay.html


    [101 rx пример]
    http://rxwiki.wikidot.com/101samples#toc5

    [Observable Utility Operators]
    https://github.com/ReactiveX/RxJava/wiki/Observable-Utility-Operators





























