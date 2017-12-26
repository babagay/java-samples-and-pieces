package JavaCore.Module05OOP.Interfaces;

public interface Factory
{
    default Factory getFactory(){ return this; }
}
