package com.kupsh.spacedefenders.main;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class Util {

	// for drawing text in rectangles
	public static int clamp(int var, int min, int max) {
		if (var > max)
			return var = max;
		else if (var <= min)
			return var = min;
		else
			return var;
	}

	public static void CenterIntoRect(Graphics g, String text, Font fnt,
			Rectangle rc) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setFont(fnt);
		FontRenderContext frc = g2.getFontRenderContext();
		// get the visual center of the component.
		int centerX = rc.width / 2;
		int centerY = rc.height / 2;

		// get the bounds of the string to draw.
		FontMetrics fontMetrics = g.getFontMetrics(fnt);
		Rectangle stringBounds = fontMetrics.getStringBounds(text, g)
				.getBounds();

		// get the visual bounds of the text using a GlyphVector.
		GlyphVector glyphVector = fnt.createGlyphVector(frc, text);
		Rectangle visualBounds = glyphVector.getVisualBounds().getBounds();

		// calculate the lower left point at which to draw the string. note that
		// this we
		// give the graphics context the y corridinate at which we want the
		// baseline to
		// be placed. use the visual bounds height to center on in conjuction
		// with the
		// position returned in the visual bounds. the vertical position given
		// back in the
		// visualBounds is a negative offset from the basline of the text.
		int textX = centerX - stringBounds.width / 2;
		int textY = centerY - visualBounds.height / 2 - visualBounds.y;

		g.drawString(text, textX + rc.x, textY + rc.y);
	}

	public static void DrawIntoRect(String str, Rectangle rc, Graphics g, Font F) {
		FontMetrics FM = g.getFontMetrics(F);
		g.setFont(F);
		int lineSep = 3;
		int strWidth = FM.stringWidth(str);
		int strHeight = FM.getHeight();
		int strLength = str.length();
		int charPerLine = (int) (strLength * rc.width / (double) strWidth);
		if (charPerLine >= strLength)
			g.drawString(str, rc.x, rc.y + strHeight);
		else {
			int lines = strLength / charPerLine;
			int skip = 0;
			for (int i = 1; i <= lines; i++) {
				String sTemp = str.substring(skip, skip + charPerLine - 1);
				if (!str.substring(skip + charPerLine - 1, skip + charPerLine)
						.equals(" ")
						&& !str.substring(skip + charPerLine - 2,
								skip + charPerLine - 1).equals(" ")) {
					sTemp += "-";
				}
				g.drawString(sTemp.trim(), rc.x, rc.y + i * strHeight + (i - 1)
						* lineSep);
				skip += charPerLine - 1;
			}
			g.drawString(str.substring(skip, strLength).trim(), rc.x, rc.y
					+ (lines + 1) * strHeight + (lines) * lineSep);
		}
	}
}
