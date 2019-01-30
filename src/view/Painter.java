package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import model.RenderConstants;

public abstract class Painter {
	protected int screenWidth;
	protected int screenHeight;

	protected Graphics2D g2d;

	protected Color clsColor;
	
	protected AlphaComposite alphaFull=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
	protected AlphaComposite alphaLow=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f);
	protected AlphaComposite alphaHigh=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .75f);

	public Painter() {
		screenWidth = RenderConstants.SCREEN_WIDTH;
		screenHeight = RenderConstants.SCREEN_HEIGHT;
		
		clsColor=Color.BLACK;
	}

	public abstract void drawScreen(Graphics2D g2d);

	protected void setColor(Color c) {
		g2d.setColor(c);
	}

	protected void setClsColor(Color c) {
		clsColor = c;
	}

	protected void cls() {
		setColor(clsColor);
		fillRect(0, 0, screenWidth, screenHeight);
	}

	protected void fillRect(int x, int y, int width, int height) {
		g2d.fillRect(x, y, width, height);
	}

	protected void fillRect(float x, float y, float width, float height) {
		g2d.fillRect((int)x, (int)y, (int)width, (int)height);
	}
	
	protected void fillRect(Rectangle r){
		fillRect(r.x, r.y, r.width, r.height);
	}
	
	protected void drawRect(int x, int y, int width, int height) {
		g2d.drawRect(x, y, width, height);
	}

	protected void drawRect(float x, float y, float width, float height) {
		g2d.drawRect((int)x, (int)y, (int)width, (int)height);
	}
	
	protected void drawRect(Rectangle r){
		drawRect(r.x, r.y, r.width, r.height);
	}
	
	protected void drawLine(int x1, int y1, int x2, int y2){
		g2d.drawLine(x1, y1, x2, y2);
	}
	
	protected void drawLine(float x1, float y1, float x2, float y2){
		g2d.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	protected void drawString(String str, float x, float y){
		g2d.drawString(str, x, y);
	}
	
	protected void drawShadowedString(String str, float x, float y){
		drawShadowedString(str, x, y, g2d.getColor());
	}

	protected void drawShadowedString(String str, float x, float y, Color c){
		drawShadowedString(str, x, y, c, Color.BLACK);
	}
	
	protected void drawShadowedString(String str, float x, float y, Color c, Color shadowColor){
		setColor(shadowColor);
		drawString(str, x+1, y+1);
		setColor(c);
		drawString(str, x, y);
	}
	
	protected void fillOval(float x, float y, float width, float height){
		g2d.fillOval((int)x, (int)y, (int)width, (int)height);
	}
	
	protected void fillCircle(float centerX, float centerY, float radius){
		fillOval(centerX-radius, centerY-radius, 2*radius, 2*radius);
	}
	
	protected void setAlpha(AlphaComposite alpha){
		g2d.setComposite(alpha);
	}
	
	protected void setAlpha(float alpha){
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
	}
	
	protected void drawImage(BufferedImage image, float x, float y){
		g2d.drawImage(image, (int)x, (int)y, null);
	}
	
	protected void drawScaledImageCenteredAt(BufferedImage image, int centerX, int centerY, float scalingCoefficient){
		drawScaledImage(image, centerX-image.getWidth()*scalingCoefficient/2, centerY-image.getHeight()*scalingCoefficient/2, image.getWidth()*scalingCoefficient, image.getHeight()*scalingCoefficient);
	}
	
	protected void drawScaledImage(BufferedImage image, float x, float y, float width, float height){
		g2d.drawImage(image, (int)x, (int)y, (int)width, (int)height, null);
	}
}
