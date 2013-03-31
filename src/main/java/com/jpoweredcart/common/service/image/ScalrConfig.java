package com.jpoweredcart.common.service.image;

import java.awt.image.BufferedImageOp;

import org.imgscalr.Scalr;

public class ScalrConfig {
	
	private Scalr.Method method = Scalr.Method.SPEED;
	
	private Scalr.Mode mode = Scalr.Mode.FIT_TO_WIDTH;
	
	private BufferedImageOp imageOp = Scalr.OP_ANTIALIAS;
	
	public Scalr.Method getMethod() {
		return method;
	}

	public ScalrConfig setMethod(Scalr.Method method) {
		this.method = method;
		return this;
	}

	public Scalr.Mode getMode() {
		return mode;
	}

	public ScalrConfig setMode(Scalr.Mode mode) {
		this.mode = mode;
		return this;
	}

	public BufferedImageOp getImageOp() {
		return imageOp;
	}

	public ScalrConfig setImageOp(BufferedImageOp imageOp) {
		this.imageOp = imageOp;
		return this;
	}
	
}
