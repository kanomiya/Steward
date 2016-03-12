package com.kanomiya.steward.common.model.texture;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * @author Kanomiya
 *
 */
public class TextureConverter implements JsonSerializer<Texture>, JsonDeserializer<Texture> {

	/**
	* @inheritDoc
	*/
	@Override
	public JsonElement serialize(Texture texture, Type type, JsonSerializationContext context) {
		JsonObject jsObj = new JsonObject();

		jsObj.add("src", context.serialize(texture.src));
		if (texture.mode != TextureMode.STATIC) jsObj.add("mode", context.serialize(texture.mode));
		if (texture.type != TextureType.FRONT) jsObj.add("type", context.serialize(texture.type));

		if (texture.mode.requireInterval()) jsObj.addProperty("interval", texture.interval);

		return jsObj;
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Texture deserialize(JsonElement jsElm, Type type, JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsObj = jsElm.getAsJsonObject();

		JsonElement srcElm = jsObj.get("src");
		String[][] src;
		context.deserialize(jsObj.get("src"), String[].class);

		if (srcElm.isJsonNull()) src = null;
		else if (srcElm.isJsonPrimitive()) src = new String[][] { new String[] { srcElm.getAsString() } };
		else
		{
			JsonArray srcElmArray = srcElm.getAsJsonArray();

			if (srcElmArray.size() == 0)
			{
				src = null;
			}
			else if (srcElmArray.get(0).isJsonPrimitive())
			{
				src = new String[][] { context.deserialize(srcElmArray, String[].class) };
			} else
			{
				src = context.deserialize(srcElmArray, String[][].class);
			}
		}

		TextureMode mode = TextureMode.STATIC;
		if (jsObj.has("mode")) mode = context.deserialize(jsObj.get("mode"), TextureMode.class);

		TextureType itype = TextureType.FRONT;
		if (jsObj.has("type")) itype = context.deserialize(jsObj.get("type"), TextureType.class);

		int interval = 1000;
		if (mode.requireInterval() && jsObj.has("interval")) interval = jsObj.get("interval").getAsInt();

		return new Texture(src, mode, itype, null, interval);
	}




}
