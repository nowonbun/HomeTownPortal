package common;

import java.util.Date;

import model.State;

public class DBUtil {
	public static byte FALSE = 0;
	public static byte TRUE = 1;
	
	public static String ADMIN = "ADMIN";
	public static String GUEST = "GUEST";
	
	public static State createState(String user) {
		State state = new State();
		state.setCreater(user);
		state.setCreateDate(new Date());
		state.setIsDelete(false);

		return state;
	}
}
