package JavaCore.Module05.Vehicle;

public class CarDoor
{
    private boolean isDoorOpen = false;

    private boolean isWindowOpen = false;

    public CarDoor()
    {
        isDoorOpen = false;
        isWindowOpen = false;
    }

    public CarDoor(boolean isDoorOpen, boolean isWindowOpen)
    {
        this.isDoorOpen = isDoorOpen;
        this.isWindowOpen = isWindowOpen;
    }

    public boolean isDoorOpen()
    {
        return isDoorOpen;
    }

    public void setDoorOpen(boolean doorOpen)
    {
        isDoorOpen = doorOpen;
    }

    public boolean isWindowOpen()
    {
        return isWindowOpen;
    }

    public void setWindowOpen(boolean windowOpen)
    {
        isWindowOpen = windowOpen;
    }

    public void openTheDoor()
    {
        if ( !isDoorOpen )
            toggleTheDoor();
    }

    public void closeTheDoor()
    {
        if ( isDoorOpen )
            toggleTheDoor();
    }

    public void toggleTheDoor()
    {
        setDoorOpen( !isDoorOpen() );
    }

    public void openTheWindow()
    {
        setWindowOpen( true );
    }

    public void closeTheWindow()
    {
        setWindowOpen( false );
    }

    public void toggleTheWindow()
    {
        setWindowOpen( !isWindowOpen() );
    }

    @Override
    public String toString()
    {
        String _isDoorOpen = isDoorOpen() ? "opened" : "closed";
        String _isWindowOpen = isWindowOpen() ? "opened" : "closed";

        return this.getClass().toString() + ":\n door is " + _isDoorOpen + ";\n window is " + _isWindowOpen;
    }
}
