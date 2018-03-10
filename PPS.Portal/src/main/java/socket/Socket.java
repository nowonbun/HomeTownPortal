package socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.ServerEndpoint;

import common.HttpSessionConfigurator;
import common.ISocket;
import common.IWorkflow;
import common.Util;
import common.Workflow;
import entity.WebSocketNode;
import entity.WebSocketResult;

@ServerEndpoint(value = "/socket", configurator = HttpSessionConfigurator.class)
public class Socket extends ISocket {
	private static Map<String, IWorkflow> flyweight = new HashMap<>();

	public void main(WebSocketNode node) {
		try {
			WebSocketResult ret = getClass(Login.class, node);
			sendMessage(ret);
			if (Util.StringEquals(ret.getData(), Login.NG)) {
				return;
			}
			sendMessage(getClass(Main.class, node));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WebSocketResult getClass(Class<?> clz, WebSocketNode node) {
		return getClass(getClassName(Main.class), node);
	}

	private WebSocketResult getClass(String key, WebSocketNode node) {
		return getClass(key).run(node);
	}

	private IWorkflow getClass(String key) {
		try {
			if (key == null || key.length() < 1) {
				return null;
			}
			if (!flyweight.containsKey(key)) {
				List<Class<?>> classes = Util.getClasses(this.getClass().getPackage().getName());
				Class<?> clz = classes.stream().filter(x -> {
					try {
						Workflow anno = x.getAnnotation(Workflow.class);
						if (key.equals(anno.name())) {
							return true;
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
					return false;
				}).findFirst().get();
				if (clz == null) {
					return null;
				}
				flyweight.put(key, (IWorkflow) clz.newInstance());
			}
			return flyweight.get(key);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IOException e) {
			return null;
		}
	}
}
