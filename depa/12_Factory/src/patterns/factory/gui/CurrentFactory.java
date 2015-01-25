package patterns.factory.gui;

public abstract class CurrentFactory {

	private CurrentFactory() { }

	private static ComponentFactory factory;

	public static void setFactory(ComponentFactory factory) {
		if (factory == null)
			throw new NullPointerException();
		CurrentFactory.factory = factory;
	}

	public static ComponentFactory getFactory() {
		return factory;
	}

}
