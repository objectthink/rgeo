
/**
 * Write a description of interface Action here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface IAction<T>
{
    void action(T t);
    void error(int responseCode);
}
