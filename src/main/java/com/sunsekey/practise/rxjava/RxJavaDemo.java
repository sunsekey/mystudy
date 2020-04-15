package com.sunsekey.practise.rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RxJavaDemo {

    static class DemoStudent {
        private String name;
        private List<DemoCourse> courseList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<DemoCourse> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<DemoCourse> courseList) {
            this.courseList = courseList;
        }
    }

    static class DemoCourse {
        private String courseName;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        @Override
        public String toString() {
            return "DemoCourse{" +
                    "courseName='" + courseName + '\'' +
                    '}';
        }
    }


    public static void main(String[] args) throws Exception {

//        testBasicSubscribe();
//        testActionSubscribe();
//        testJustOpr();
//        testMapOpr();
//        testFlatMap();
//        testScheduler();
//        testDeferOpr();
//        testToFuture();
//        testReplaySubject();
        liftTest();
//        testWindow();
    }

    public static void testWindow() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("hello, no:" + i);
        }
        AtomicInteger i = new AtomicInteger(0);
        // Observable.window(ts,tu)一定时间间隔发射一个Observable，每个Observable可发射的数据都是原始Observable所能发射的数据的子集
        // Observable.window(count) 每当当前窗口发射了count项数据，它就关闭当前窗口并打开一个新窗口
        // Observable.window(count, skip) 原始Observable每发射skip项数据它就打开一个新窗口，每当当前窗口发射了count项数据，它就关闭当前窗口（等待下次窗口在满足了skip后打开）
        // skip > count 则有丢弃；skip < count 则有重复
        // 文档 https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Window.html
        Observable.from(list).window(2, 3).subscribe(new Subscriber<Observable<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Observable<String> stringObservable) {
                stringObservable.subscribe(getDemoSubscriberForString());
            }
        });
    }

    /**
     * 变换操作
     * 如数据源是1，2，中间通过new一个Subscriber去接收数据，然后做转换操作，然后在触发发射给目标Subscriber
     * 可以理解为发射数据前，对原Subscriber的变换或增强
     */
    public static void liftTest() {
        Subscriber<String> targetSubscriber = getDemoSubscriberForString();
        Observable.just(1, 2)
                .lift(new Observable.Operator<String, Integer>() {
                    @Override
                    public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
                        return new Subscriber<Integer>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Integer integer) {
                                String result = "test " + integer;
//                                targetSubscriber.onNext(result);
                            }
                        };
                    }
                })
                .subscribe(targetSubscriber);

    }

    public static void testReplaySubject() {
        ReplaySubject<String> replaySubject = ReplaySubject.create();
//        Observable.just("1", "2", "3").subscribe(getDemoSubscriberForString());
        /* replaySubject会把从原始Observable接收到的数据发射给所有订阅它的观察者（因为它既是Observable，也是Observer）**/
        Observable.just("0").subscribe(replaySubject);
        replaySubject.subscribe(getDemoSubscriberForString());
        replaySubject.subscribe(getDemoSubscriberForString());
    }

    public static void testToFuture() throws Exception {
        /*  the Future to retrieve a single elements from an Observable **/
        /*  将Observable 转成一个future，这个future从Observable中取一个值，所以Observable只能发射一个数据（接受多个的话用toList()来接受一个数组）**/
        /*  这里之所以Observable不用显式subscribe观察者，是因为在toFuture里，实现了这个步骤，其中是在onNext时把值放到value里，让future.get()时可以取**/
        Future<Integer> future = Observable.just(1).toBlocking().toFuture();
        Integer i = future.get();
        System.out.println(i);
    }

    public static void testDeferOpr() {
        DemoStudent demoStudent = new DemoStudent();
        /**
         * 不使用defer-方法1
         * 这种方式在Observable.just(demoStudent.getName())的时候已经设定了value的值，后面demoStudent.setName也不会改变已设定的值了
         */
        System.out.println("不使用defer-方法1");
        Observable<String> observableWithoutDefer1 = Observable.just(demoStudent.getName());
        demoStudent.setName("hello world");
        observableWithoutDefer1.subscribe(getDemoSubscriberForString());
        // 输出 null

        /**
         * 不使用defer-方法2
         * 订阅时才回调call方法，这时候demoStudent.setName已经生效，所以这种做法也是可以，但是没那么简洁
         */
        System.out.println("不使用defer-方法2");
        Observable<String> observableWithoutDefer2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(demoStudent.getName());
                subscriber.onCompleted();
            }
        });
        demoStudent.setName("hello world");
        observableWithoutDefer2.subscribe(getDemoSubscriberForString());

        /**
         * 使用defer，相比不使用defer的方法2，更简介，封装性更好
         * 延迟订阅，当订阅关系建立时，才生成一个新的Observable，并和Subscriber建立订阅关系
         */
//        https://blog.csdn.net/axuanqq/article/details/50687252?utm_source=distribute.pc_relevant.none-task
        System.out.println("使用defer");
        Observable<String> deferObservable = Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just(demoStudent.getName());
            }
        });
        /**
         * 注意理解OnSubscribeDefer，它是OnSubscribe的实现类，并持有这里传入的new Func0，即observableFactory。
         * subscribe执行时，会调用OnSubscribeDefer的call方法
         * OnSubscribeDefer.call会调用observableFactory.call(), 即Func0里的call，获取到Observable，
         * 然后调用subscribe，真正的OnSubscribe的call方法被调用
         * {@link OnSubscribeDefer#call(Subscriber s)
         * 里面会获得上面call方法返回的Observable对象，并执行订阅关系 }
        **/
        demoStudent.setName("hello world");
        deferObservable.subscribe(getDemoSubscriberForString());
    }

    public static Subscriber<String> getDemoSubscriberForString(Object arg) {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s + "  from " + arg);
            }
        };
    }

    public static Subscriber<String> getDemoSubscriberForString() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
//                System.out.println("completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println(this + " get " + s);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        };
    }

    private static void testScheduler() {
        /*
            如果你想给 Observable 操作符链添加多线程功能，你可以指定操作符( 或者特定的Observable )在特定的调度器( Scheduler )上执行。
            subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
            observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程
            让事件的产生和消费发生在不同的线程。
        **/
        /* ObserveOn 指示一个 Observable 在一个特定的调度器上调用观察者的 onNext , onError 和 onCompleted 方法。
            SubscribeOn 更进一步，它指示 Observable 将全部的处理过程( 包括发射数据和通知 )放在特定的调度器上执行。
            **/
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("OnSubscribe.call Thread -> " + Thread.currentThread().getName());
                subscriber.onNext("message");
            }
        })
                // 设置 observable 对象在 io 线程运行
                .subscribeOn(Schedulers.newThread())
                // 设置 observe 对象在 Android 主线程运行
//                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("Subscriber.onNext Thread -> " + Thread.currentThread().getName());
                    }
                });

    }

    public static void testFlatMap() {
        List<DemoStudent> students = initDemoData();
        // 类比 Java8 stream 中的flatMap
        List<DemoCourse> allCourses = students.stream().flatMap(new Function<DemoStudent, Stream<DemoCourse>>() {
            @Override
            public Stream<DemoCourse> apply(DemoStudent demoStudent) {
                return demoStudent.courseList.stream();
            }
        }).collect(Collectors.toList());
        System.out.println(allCourses);

        Observable.from(students).flatMap(new Func1<DemoStudent, Observable<DemoCourse>>() {
            @Override
            public Observable<DemoCourse> call(DemoStudent demoStudent) {
                // 将一个demoStudent对象的转换成一个发射DemoCourse的Observable
                return Observable.from(demoStudent.getCourseList());
            }
        }).subscribe(new Subscriber<DemoCourse>() {

            @Override
            public void onCompleted() {
                System.out.println("complete");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DemoCourse demoCourse) {
                System.out.println(demoCourse.getCourseName());
            }
        });
//        Observable.from(students).flatMap((Func1<DemoStudent, Observable<DemoCourse>>) demoStudent -> Observable.from(demoStudent.getCourseList()))
//                .subscribe(c -> System.out.println(c.getCourseName()), Throwable::printStackTrace, () -> System.out.println("complete"));

    }

    public static List<DemoStudent> initDemoData() {
        DemoCourse course1 = new DemoCourse();
        DemoCourse course2 = new DemoCourse();
        DemoCourse course3 = new DemoCourse();
        course1.setCourseName("语文");
        course2.setCourseName("数学");
        course3.setCourseName("英语");
        DemoStudent demoStudent1 = new DemoStudent();
        DemoStudent demoStudent2 = new DemoStudent();
        List<DemoCourse> courseList1 = new ArrayList<>();
        courseList1.add(course1);
        courseList1.add(course2);
        demoStudent1.setCourseList(courseList1);
        List<DemoCourse> courseList2 = new ArrayList<>();
        courseList2.add(course2);
        courseList2.add(course3);
        demoStudent2.setCourseList(courseList2);
        List<DemoStudent> demoStudentList = new ArrayList<>();
        demoStudentList.add(demoStudent1);
        demoStudentList.add(demoStudent2);
        return demoStudentList;
    }

    /* Observable有多种操作符，每次操作后都会返回一个新的Observable对象**/
    public static void testJustOpr() {
        /* 类似的还有from操作**/
        Observable.just(1, 2, 3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer i) {
                System.out.println(i);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("complete");
            }
        });
    }

    public static void testMapOpr() {
        Observable.just(1, 2, 3)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        integer *= 10;
                        return integer + "";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("complete");
                    }
                });
    }

    public static void testBasicSubscribe() {
        /* Observer 观察者 可理解为其定义了 onNext\onError\onCompleted这三种可观察的事件，而onNext中接收由Observable发射的数据项 **/
        Observer<String> observer = new Observer<String>() {
            public void onCompleted() {
                System.out.println("event completed");
            }

            public void onError(Throwable e) {
                e.printStackTrace();
                System.out.println("error occur");
            }

            public void onNext(String s) {
                System.out.println(s);
            }
        };
        Observable<String> observable = getDemoObservableForString();
        /* 建立订阅关系 成功建立关系后，则调用Observable中的OnSubscribe的call方法 **/
        observable.subscribe(observer);
        /* 以上是Observable.subscribe(Observer observer)方式订阅**/
    }

    public static Observable<String> getDemoObservableForString() {
        /* 被观察对象 创建时接收一个OnSubscribe的实现，该实现中有个call方法，而call方法中则决定事件怎么发生 **/
        return Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                System.out.println("call begin");
                subscriber.onNext("hello");
                subscriber.onNext("world");
                subscriber.onCompleted();
            }
        });
    }

    public static void testActionSubscribe() {
        /* 以下使用Action的方式进行订阅**/
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s + " from action");
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable e) {
                e.printStackTrace();
                System.out.println("error occur from action");
            }
        };
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                System.out.println("event completed from action");
            }
        };
        Observable<String> observable = getDemoObservableForString();
        /* 本质还是Observable.subscribe(Observer observer)，因为Action对象会被转换为ActionSubscriber(继承了Subscriber)**/
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
    }

}
