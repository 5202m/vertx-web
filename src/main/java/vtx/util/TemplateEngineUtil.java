package vtx.util;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.HandlebarsTemplateEngine;
import io.vertx.ext.web.templ.JadeTemplateEngine;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;

/**
 * 模板引擎工具类
 * 
 * @author Jade.zhu
 *
 */
public class TemplateEngineUtil {

	/**
	 * Jade模板
	 * 
	 * @param context
	 * @param templateUrl 模板路径
	 */
	public static void JadeEngine(RoutingContext context, String templateUrl) {
		final JadeTemplateEngine engine = JadeTemplateEngine.create();
		engine.render(context, templateUrl, res -> {
			if (res.succeeded()) {
				context.response().end(res.result());
			} else {
				context.fail(res.cause());
			}
		});
	}

	/**
	 * hbs模板引擎
	 * 
	 * @param context
	 * @param templateUrl 模板路径
	 */
	public static void HandlebarsEngine(RoutingContext context, String templateUrl) {
		final HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create();
		engine.render(context, templateUrl, res -> {
			if (res.succeeded()) {
				context.response().end(res.result());
			} else {
				context.fail(res.cause());
			}
		});
	}

	/**
	 * Thymeleaf模板引擎
	 * @param context
	 * @param templateUrl 模板路径
	 */
	public static void ThymeleafEngine(RoutingContext context, String templateUrl) {
		final ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
		engine.render(context, templateUrl, res -> {
			if (res.succeeded()) {
				context.response().end(res.result());
			} else {
				context.fail(res.cause());
			}
		});
	}
}
