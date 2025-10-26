package reactor.util.function;

import com.alfabeta.core.flow.mbean.PageModelFunction;
import java.util.Optional;

/**
 * Represents 4 optional variants of a view definition or transformation.
 * Uses Reactor's Tuple4.
 */
public final class ViewVariants extends Tuple4<
        Optional<String>,
        Optional<String>,
        Optional<PageModelFunction>,
        Optional<PageModelFunction>> {

    private ViewVariants(Optional<String> s1,
                         Optional<String> s2,
                         Optional<PageModelFunction> f1,
                         Optional<PageModelFunction> f2) {
        super(s1, s2, f1, f2);
    }

    public static ViewVariants of(
            Optional<String> s1,
            Optional<String> s2,
            Optional<PageModelFunction> f1,
            Optional<PageModelFunction> f2) {
        return new ViewVariants(s1, s2, f1, f2);
    }

    @Override
    public String toString() {
        return "ViewVariants{" +
                "variant1=" + getT1() +
                ", variant2=" + getT2() +
                ", function1=" + getT3() +
                ", function2=" + getT4() +
                '}';
    }
}
