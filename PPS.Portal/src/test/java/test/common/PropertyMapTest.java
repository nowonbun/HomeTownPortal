package test.common;

import java.io.IOException;

import common.PropertyMap;

public class PropertyMapTest {
	public static void main(String... args) {
		String val = PropertyMap.getInstance().getProperty("test", "Test");
		System.out.println(val);
	}
}
