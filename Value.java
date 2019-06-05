
/**
 * Write a description of class Value here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Value<T>
{
    T _value;
        
    public T value()
    {
        return _value;
    }
        
    public void setValue(T value)
    {
        _value = value;
    }
        
    public Value(T initial)
    {
        _value = initial;
    }
}
