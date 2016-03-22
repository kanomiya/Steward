package com.kanomiya.steward.common.controller.action.key;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.kanomiya.steward.common.Game;
import com.kanomiya.steward.common.config.GameKeys;
import com.kanomiya.steward.common.controller.action.IControlAction;
import com.kanomiya.steward.common.controller.unit.event.KeyboardUpdateEvent;
import com.kanomiya.steward.common.model.assets.Assets;
import com.kanomiya.steward.common.model.event.Player;
import com.kanomiya.steward.common.model.overlay.GameColor;
import com.kanomiya.steward.common.model.overlay.text.Text;
import com.kanomiya.steward.common.view.ViewConsts;

/**
 * @author Kanomiya
 *
 */
public class CAKeyFunction implements IControlAction<KeyboardUpdateEvent> {

	/**
	* @inheritDoc
	*/
	@Override
	public void action(KeyboardUpdateEvent event, Game game)
	{
		if (event.isCancelledOrConsumed()) return ;

		Assets assets = game.assets;
		Player player = game.thePlayer;

		if (event.isPressed(GameKeys.F1)); // help

		if (event.isPressed(GameKeys.F2)); //

		// F3 -> CAKeyMode

		if (event.isPressed(GameKeys.F4)) // screenshot
		{
			File ssDir = new File("screenshots");
			if (! ssDir.exists()) ssDir.mkdir();

			try {
				ImageIO.write(ViewConsts.viewGame.screenShot(), "png", new File(ssDir, System.currentTimeMillis() + ".png"));
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			player.logger.println(Text.create(assets.translate("se.photo")).color(GameColor.green));
		}

		if (event.isPressed(GameKeys.F5)); //
		if (event.isPressed(GameKeys.F6)); //
		if (event.isPressed(GameKeys.F7)); //
		if (event.isPressed(GameKeys.F8)); //
		if (event.isPressed(GameKeys.F9)); //
		if (event.isPressed(GameKeys.F10)); //
		if (event.isPressed(GameKeys.F11)); //

		// F12 -> CAKeyMode


	}



}
