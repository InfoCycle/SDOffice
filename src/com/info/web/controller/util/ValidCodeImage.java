package com.info.web.controller.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random; 

import javax.imageio.ImageIO;

public class ValidCodeImage {
	public String ValidCode;
	public byte[] imageByte;
	public void validCodeImage() throws IOException
	{
		int width=86;
		int height=24;
		int codeCount=5; 
		int x=0;
		int fontHeight;
		int codeY;
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
		'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
		x=width/(codeCount+1);
		fontHeight=height-2;
		codeY=height-4; 
		//定义图像buffer
		BufferedImage buffImg = new BufferedImage(
		width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		Random random = new Random();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		//随机产生10条干扰线，使图象中的认证码不易被其它程序探测到。
		g.setColor(Color.BLACK);
		for(int i = 0; i<4; i++)
		{
			int x1 = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x1, y, x1 + xl, y + yl);
		}
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;
		for (int i = 0; i<codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i + 1)*x, codeY);
			randomCode.append(strRand);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(buffImg, "jpg", out);
		imageByte = out.toByteArray();
		ValidCode = randomCode.toString();
	}
}
