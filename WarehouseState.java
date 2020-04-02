public abstract class WarehouseState {
	protected static Context context;
	protected WarehouseState() {
		context = Context.instance();
	}
	public abstract void run();
}