package common.interceptor;

public interface IInterceptor<T, E extends Throwable> {

    T intercept(T req) throws E;

}