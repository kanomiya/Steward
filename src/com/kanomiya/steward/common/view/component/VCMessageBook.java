package com.kanomiya.steward.common.view.component;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.util.Iterator;

import com.kanomiya.steward.common.model.assets.Assets;
import com.kanomiya.steward.common.model.overlay.GameFont;
import com.kanomiya.steward.common.model.overlay.Text;
import com.kanomiya.steward.common.model.overlay.message.IngameLogger;
import com.kanomiya.steward.common.model.overlay.message.MessageBook;
import com.kanomiya.steward.common.view.ViewConsts;
import com.kanomiya.steward.common.view.ViewUtils;

/**
 * @author Kanomiya
 *
 */
public class VCMessageBook implements IViewComponent<MessageBook> {

	public static boolean containsBtnCheck(MessageBook book, int x, int y)
	{
		int dx = 0;
		if (book.isFirstPage()) dx += -27;
		return (book.width -30 +dx <= x && x < book.width -30 +dx +27 && 20 <= y && y < 20 +27);
	}

	public static boolean containsBtnRight(MessageBook book, int x, int y)
	{
		int dx = 0;
		if (! book.isFirstPage() && book.isClosable()) dx += -27;
		return (book.width -30 +dx <= x && x < book.width -30 +dx +27 && 20 <= y && y < 20 +27);
	}

	public static boolean containsBtnLeft(MessageBook book, int x, int y)
	{
		int dx = 0;
		if (! book.isFirstPage()) dx += -27;
		return (book.width -30 +dx <= x && x < book.width -30 +dx +27 && 20 <= y && y < 20 +27);
	}

	public static boolean containsBtnUp(MessageBook book, int x, int y)
	{
		return (book.width -30 -27 <= x && x < book.width -30 -27 +27 && book.height -30 <= y && y < book.height -30 +27);
	}

	public static boolean containsBtnDown(MessageBook book, int x, int y)
	{
		return (book.width -30 <= x && x < book.width -30 +27 && book.height -30 <= y && y < book.height -30 +27);
	}

	/**
	* @inheritDoc
	*/
	@Override
	public void paint(Graphics2D g, MessageBook book, Assets assets, int frame) {

		if (! book.hasPage()) return;
		if (! book.currentPage().hasItem()) return;

		g.translate(book.x, book.y);

		g.setComposite(ViewConsts.alpha80);
		if (book.hasBackground()) ViewConsts.vcTexture.paint(g, book.getBackground(), assets, frame);

		int x1 = book.width -30;
		int y1 = 20;
		g.translate(x1, y1);

		int x2 = 0;
		if (! book.isFirstPage() && book.isClosable())
		{
			x2 = -27;
			ViewConsts.vcTexture.paint(g, ViewConsts.texCheck, assets, frame);
		}

		g.translate(x2, 0);

		int x3 = 0;
		if (! book.isLastPage())
		{
			x3 = -27;
			ViewConsts.vcTexture.paint(g, ViewConsts.texArrowBtnRight, assets, frame);
		}

		g.translate(x3, 0);

		int x4 = 0;

		if (! book.isFirstPage())
		{
			x4 = -27;
			ViewConsts.vcTexture.paint(g, ViewConsts.texArrowBtnLeft, assets, frame);
		}

		g.translate(x4, 0);

		if (book.isFirstPage() && book.isClosable())
		{
			ViewConsts.vcTexture.paint(g, ViewConsts.texCheck, assets, frame);
		}

		g.translate(-x2 -x4, -y1);

		int x5 = 24;
		int y2 = book.height -30;
		g.translate(0, y2);

		if (0 < book.getTopIndexToShow())
			ViewConsts.vcTexture.paint(g, ViewConsts.texArrowBtnUp, assets, frame); // 492, 196

		g.translate(x5, 0);

		if (book.getTopIndexToShow() < book.currentPage().itemCount() -1)
			ViewConsts.vcTexture.paint(g, ViewConsts.texArrowBtnDown, assets, frame); // 516, 196

		g.translate(-x3 -x5, -y2);

		g.translate(-x1, 0);




		g.setComposite(AlphaComposite.SrcOver);



		g.translate(Text.lineHeight, Text.lineHeight);
		g.setFont(GameFont.textFont);


		Iterator<Text> itr = book.currentPage().items().subList(book.getTopIndexToShow(), book.getLastIndexToShow(IngameLogger.oneHeight)).iterator();

		int line = 0;
		int left = 0;

		while (line < IngameLogger.oneHeight && itr.hasNext())
		{
			Text item = itr.next();
			int top = (line +1) *Text.lineHeight;

			g.setColor(item.color);
			g.setFont(GameFont.textFont);
			if (item.bold) g.setFont(GameFont.textFontBold);

			String text = ViewUtils.wordWrap(item.text, g, left, IngameLogger.innerWidth);

			if (text.contains("\n"))
			{
				String[] lines = text.split("\n");
				int yLen = lines.length;

				for (int i=0; i<yLen; i++)
				{
					top += i *Text.lineHeight;

					g.drawString(lines[i], left, top);
					if (item.underline) g.drawLine(left, top, g.getFontMetrics().stringWidth(lines[i]), top);

					left = 0;
				}

				line += yLen -1;

				left += g.getFontMetrics().stringWidth(lines[yLen -1]);

			} else
			{
				g.drawString(text, left, top);
				if (item.underline) g.drawLine(left, top, g.getFontMetrics().stringWidth(text), top);

				left += g.getFontMetrics().stringWidth(text);
			}



			if (item.lineBreak)
			{
				line ++;
				left = 0;
			}

		}



		g.translate(-Text.lineHeight, -Text.lineHeight);

		g.translate(-book.x, -book.y);
	}

}
