package container;

public interface IContainer {
    public boolean isEmpty();
    public <T>void add(T trans);
    public <T>T remove();
}
