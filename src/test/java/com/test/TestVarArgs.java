package com.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.function.Function;

/**
 *
 */
public class TestVarArgs {

	/**
	 *
	 */
	public static class MyObject {
		public String execute(String name, Function<Integer, String> func, Object ...args) {
			Assertions.assertNotNull(func);
			Assertions.assertEquals(3, args.length);

			return func.apply(1);
		}
	}

	/**
	 *
	 */
	public static class MyObject2 {

		public String execute(String name, Object ...args) {
			return execute(name, null, args);
		}

		public String execute(String name, Function<Integer, String> func, Object ...args) {
			Assertions.assertNotNull(func);
			Assertions.assertEquals(3, args.length);
			return func.apply(1);
		}
	}

	/**
	 *
	 * @throws ScriptException
	 */
	@Test
	public void success()
		throws ScriptException
	{
		ScriptEngine engine = getEngine();
		engine.put("myobject", new MyObject());
		engine.eval("myobject.execute('a', (idx) => idx  == 0? 'a': 'b', 1,2,3)");
	}


	/**
	 *
	 * @throws ScriptException
	 */
	@Test
	public void error()
		throws ScriptException
	{
		ScriptEngine engine = getEngine();
		engine.put("myobject", new MyObject2());
		engine.eval("myobject.execute('a', (idx) => idx  == 0? 'a': 'b', 1,2,3)");
	}


	/**
	 *
	 * @return
	 */
	private ScriptEngine getEngine() {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("Graal.js");
		engine.put("polyglot.js.allowHostAccess", true);
		return engine;
	}

}
