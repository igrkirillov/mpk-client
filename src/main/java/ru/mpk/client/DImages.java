package ru.mpk.client;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

public class DImages {
	private ImageRegistry registry;

	private DImages() {
		this.registry = new ImageRegistry();
		registry.put(IMG_BO_Online, ImageDescriptor.createFromURL(getClass().getResource("/icons/bo_online.png")));
		registry.put(IMG_BO128, ImageDescriptor.createFromURL(getClass().getResource("/icons/bo128.png")));
		registry.put(IMG_BO_Offline, ImageDescriptor.createFromURL(getClass().getResource("/icons/bo_offline.png")));
		registry.put(IMG_Quit, ImageDescriptor.createFromURL(getClass().getResource("/icons/quit.png")));
		registry.put(IMG_Wheel, ImageDescriptor.createFromURL(getClass().getResource("/icons/wheel.png")));
		registry.put(IMG_Wheel72, ImageDescriptor.createFromURL(getClass().getResource("/icons/wheel72.png")));
		registry.put(IMG_Find, ImageDescriptor.createFromURL(getClass().getResource("/icons/find.png")));
		registry.put(IMG_Find72, ImageDescriptor.createFromURL(getClass().getResource("/icons/find72.png")));
		registry.put(IMG_Modify, ImageDescriptor.createFromURL(getClass().getResource("/icons/modify.png")));
		registry.put(IMG_Modify72, ImageDescriptor.createFromURL(getClass().getResource("/icons/modify72.png")));
		registry.put(IMG_GreenCircle, ImageDescriptor.createFromURL(getClass().getResource("/icons/greenCircle.png")));
		registry.put(IMG_RedCircle, ImageDescriptor.createFromURL(getClass().getResource("/icons/redCircle.png")));
	}

	private static final String IMG_BO_Online = "img_BO_online";
	public Image img_BO_Online() {
		return registry.get(IMG_BO_Online);
	}
	public ImageDescriptor des_BO_Online() {
		return registry.getDescriptor(IMG_BO_Online);
	}

	private static final String IMG_BO128 = "img_BO128";
	public Image img_BO128() {
		return registry.get(IMG_BO128);
	}
	public ImageDescriptor des_BO128() {
		return registry.getDescriptor(IMG_BO128);
	}

	private static final String IMG_BO_Offline = "img_BO_offline";
	public Image img_BO_OfflineImage() {
		return registry.get(IMG_BO_Offline);
	}
	public ImageDescriptor des_BO_Offline() {
		return registry.getDescriptor(IMG_BO_Offline);
	}

	private static final String IMG_Quit = "img_Quit";
	public Image img_Quit() {
		return registry.get(IMG_Quit);
	}
	public ImageDescriptor des_Quit() {
		return registry.getDescriptor(IMG_Quit);
	}

	private static final String IMG_Wheel = "img_Wheel";
	public Image img_Wheel() {
		return registry.get(IMG_Wheel);
	}
	public ImageDescriptor des_Wheel() {
		return registry.getDescriptor(IMG_Wheel);
	}

	private static final String IMG_Wheel72 = "img_Wheel72";
	public Image img_Wheel72() {
		return registry.get(IMG_Wheel72);
	}
	public ImageDescriptor des_Wheel72() {
		return registry.getDescriptor(IMG_Wheel72);
	}

	private static final String IMG_Find = "img_Find";
	public Image img_Find() {
		return registry.get(IMG_Find);
	}
	public ImageDescriptor des_Find() {
		return registry.getDescriptor(IMG_Find);
	}

	private static final String IMG_Find72 = "img_Find72";
	public Image img_Find72() {
		return registry.get(IMG_Find72);
	}
	public ImageDescriptor des_Find72() {
		return registry.getDescriptor(IMG_Find72);
	}

	private static final String IMG_Modify = "img_Modify";
	public Image img_Modify() {
		return registry.get(IMG_Modify);
	}
	public ImageDescriptor des_Modify() {
		return registry.getDescriptor(IMG_Modify);
	}

	private static final String IMG_Modify72 = "img_Modify72";
	public Image img_Modify72() {
		return registry.get(IMG_Modify72);
	}
	public ImageDescriptor des_Modify72() {
		return registry.getDescriptor(IMG_Modify72);
	}

	private static final String IMG_GreenCircle = "img_GreenCircle";
	public Image img_GreenCircle() {
		return registry.get(IMG_GreenCircle);
	}
	public ImageDescriptor des_GreenCircle() {
		return registry.getDescriptor(IMG_GreenCircle);
	}

	private static final String IMG_RedCircle = "img_RedCircle";
	public Image img_RedCircle() {
		return registry.get(IMG_RedCircle);
	}
	public ImageDescriptor des_RedCircle() {
		return registry.getDescriptor(IMG_RedCircle);
	}

	private static DImages instance;
	public static DImages instance() {
		if (instance == null) {
			instance = new DImages();
		}
		return instance;
	}
}