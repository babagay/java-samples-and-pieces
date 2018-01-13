package JavaCore.Module05Poly.Interface;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collector;

//@FunctionalInterface
public interface FlowerCollector<Flower, A,T> extends BiFunction
{
    BiConsumer<A, T> accumulator();
}
