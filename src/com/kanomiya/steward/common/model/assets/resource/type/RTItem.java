package com.kanomiya.steward.common.model.assets.resource.type;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.FutureTask;

import com.google.gson.Gson;
import com.kanomiya.steward.common.model.item.Item;
import com.kanomiya.steward.common.util.filter.ExtensionFilter;

/**
 * @author Kanomiya
 *
 */
public class RTItem extends ResourceType<Item> {

	protected RTItem() {
		super(false, true, "item", new ExtensionFilter("json", true));
	}

	/**
	* @inheritDoc
	*/
	@Override
	public Item load(String id, File file, Gson gson, List<FutureTask> futureTaskList) throws IOException
	{
		return gson.fromJson(new FileReader(file), Item.class);
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void save(Item resource, File file, Gson gson) throws IOException
	{
		// TODO 自動生成されたメソッド・スタブ

	}




}
