package guru.springframework.converters;

import static org.junit.Assert.*;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

public abstract class AbstractConverterTest<T extends Converter, U>{

    protected T converter;
    protected U command;

    @Before
    public void setUp() throws Exception{
        converter = getInstanceOfT();
        command = getInstanceOfU();
        init();
    }

    public abstract void init();

    @Test
    public void testNullObject() throws Exception{
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception{
        assertNotNull(converter.convert(command));
    }

    private T getInstanceOfT(){
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
        try{
            return type.newInstance();
        }catch(Exception e){
            return null;
        }
    }

    private U getInstanceOfU(){
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<U> type = (Class<U>) superClass.getActualTypeArguments()[1];
        try{
            return type.newInstance();
        }catch(Exception e){
            return null;
        }
    }
}
