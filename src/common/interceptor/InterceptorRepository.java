package common.interceptor;

import java.util.ArrayList;

public class InterceptorRepository<T, E extends Throwable> extends ArrayList<IInterceptor<T, E>> {
//
//    public InterceptorRepository(){
//
//    }
//    public InterceptorRepository(IInterceptor... interceptors){
//        addMany(interceptors);
//
//    }
//    public T intercept(T obj) throws Exception{
//
//        Iterator<IInterceptor<T, E>> it = iterator();
//        try {
//            while (it.hasNext()) {
//                IInterceptor<T, E> inter = it.next();
//                obj = inter.intercept(obj);
//            }
//
//        }catch(Exception e){
//
//        }
//        return obj;
//    }
//    public InterceptorRepository addMany(IInterceptor... interceptors){
//        for(IInterceptor interceptor : interceptors){
//            add(interceptor);
//        }
//
//        return this;
//    }
}
