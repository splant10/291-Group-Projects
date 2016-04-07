import java.util.*;

public class KeyValue {
	public final List<String> keys;
	public final List<String> values;

	public KeyValue() {
		this.keys = new ArrayList<String>();
		this.values = new ArrayList<String>();
	}

	public void AddKey(String key) {
		this.keys.add(key);
	}

	public void AddValue(String value) {
		this.values.add(value);
	}
}