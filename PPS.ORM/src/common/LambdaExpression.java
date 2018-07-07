package common;

public interface LambdaExpression<T, R> {
	R run(T node);
}
