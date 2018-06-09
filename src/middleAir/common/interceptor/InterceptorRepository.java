package middleAir.common.interceptor;

import java.util.ArrayList;
import java.util.Iterator;

public class InterceptorRepository<T> extends ArrayList<IInterceptor<T, Exception>> {

    public InterceptorRepository(IInterceptor... interceptors){
        addMany(interceptors);
    }

    public T intercept(T obj) throws Exception{

        Iterator<IInterceptor<T, Exception>> it = iterator();

        while (it.hasNext()) {
            IInterceptor<T, Exception> inter = it.next();
            obj = inter.intercept(obj);
        }

        return obj;
    }
    public InterceptorRepository addMany(IInterceptor... interceptors){
        for(IInterceptor interceptor : interceptors){
            add(interceptor);
        }

        return this;
    }
}
