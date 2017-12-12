package HT.Portal.common;

import java.io.IOException;

public class PropertyMapTest {
	public static void main(String... args) {
		try {
			String val = PropertyMap.getInstance().getProperty("test", "Test");
			System.out.println(val);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
