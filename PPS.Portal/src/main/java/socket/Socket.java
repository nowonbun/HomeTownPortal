package socket;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.ServerEndpoint;

import common.HttpSessionConfigurator;
import common.ISocket;
import common.IWorkflow;
import common.JsonConverter;
import common.LoggerManager;
import common.Util;
import common.Workflow;
import entity.NavigateNode;
import entity.WebSocketNode;
import entity.WebSocketResult;

@ServerEndpoint(value = "/socket", configurator = HttpSessionConfigurator.class)
public class Socket extends ISocket {
	private static Map<String, IWorkflow> flyweight = new HashMap<>();
	private static Map<String, Map<String, Method>> flyweight2 = new HashMap<>();

	public void main(WebSocketNode node) {
		try {
			WebSocketResult ret = getExecute("login", IWorkflow.Init, node);
			sendMessage("login", IWorkflow.Init, ret.getData(), node.getSession());
			if (Util.StringEquals(ret.getData(), Login.NG)) {
				return;
			}
			ret = getExecute(node.getControl(), node.getAction(), node);
			List<NavigateNode> navi = ret.getNavigate();
			sendMessage("navigate", IWorkflow.Init, JsonConverter.create(navi), node.getSession());
			sendMessage(ret);
		} catch (Throwable e) {
			LoggerManager.getLogger(Socket.class).error(e.toString());
		}
	}

	private WebSocketResult getExecute(String control, String action, WebSocketNode node) throws Throwable {
		Method mthd = getMethod(control, action);
		IWorkflow clz = getClass(control);

		return (WebSocketResult) mthd.invoke(clz, node);
	}

	private Method getMethod(String control, String action) throws Throwable {
		IWorkflow clz = getClass(control);
		if (!flyweight2.containsKey(control)) {
			flyweight2.put(control, new HashMap<>());
		}
		Map<String, Method> sub = flyweight2.get(control);

		if (!sub.containsKey(action)) {
			sub.put(action, clz.getClass().getMethod(action, WebSocketNode.class));
		}
		return sub.get(action);
	}

	private IWorkflow getClass(String control) throws Throwable {
		if (control == null || control.length() < 1) {
			return null;
		}
		if (!flyweight.containsKey(control)) {
			Class<?> clz = getClassBundle().stream().filter(x -> {
				Workflow anno = x.getAnnotation(Workflow.class);
				if (anno != null && control.equals(anno.name())) {
					return true;
				}
				return false;
			}).findFirst().get();
			if (clz == null) {
				return null;
			}
			flyweight.put(control, (IWorkflow) clz.newInstance());
		}
		return flyweight.get(control);
	}

	private List<Class<?>> getClassBundle() {
		List<Class<?>> ret = new ArrayList<>();
		ret.add(Admin.class);
		ret.add(Card.class);
		ret.add(Login.class);
		ret.add(Main.class);
		ret.add(Profile.class);
		return ret;
	}

}
