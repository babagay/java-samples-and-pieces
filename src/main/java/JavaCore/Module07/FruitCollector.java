package JavaCore.Module07;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;

@FunctionalInterface
public interface FruitCollector<Fruit,A,R>
{
//    <R> R collect(Supplier<R> supplier,
//                  BiConsumer<R, ? super T> accumulator,
//                  BiConsumer<R, R> combiner);

    <A,R> List<Fruit> toList(Supplier supplier, BiConsumer accumulator);

}
