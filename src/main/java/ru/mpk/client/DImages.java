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
		registry.put(IMG_Address, ImageDescriptor.createFromURL(getClass().getResource("/icons/address24.png")));
		registry.put(IMG_Address72, ImageDescriptor.createFromURL(getClass().getResource("/icons/address72.png")));
		registry.put(IMG_Help, ImageDescriptor.createFromURL(getClass().getResource("/icons/help.png")));
		registry.put(IMG_Fias, ImageDescriptor.createFromURL(getClass().getResource("/icons/fias.png")));
		registry.put(IMG_Ds, ImageDescriptor.createFromURL(getClass().getResource("/icons/ds.png")));
		registry.put(IMG_Back, ImageDescriptor.createFromURL(getClass().getResource("/icons/bk.png")));
		registry.put(IMG_Abonents, ImageDescriptor.createFromURL(getClass().getResource("/icons/clients.png")));
		registry.put(IMG_Abonent, ImageDescriptor.createFromURL(getClass().getResource("/icons/abonent.png")));
		registry.put(IMG_Add, ImageDescriptor.createFromURL(getClass().getResource("/icons/add.png")));
		registry.put(IMG_Edit, ImageDescriptor.createFromURL(getClass().getResource("/icons/edit.png")));
		registry.put(IMG_Remove, ImageDescriptor.createFromURL(getClass().getResource("/icons/remove.png")));
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

	private static final String IMG_Address = "img_Address";
	public Image img_Address() {
		return registry.get(IMG_Address);
	}
	public ImageDescriptor des_Address() {
		return registry.getDescriptor(IMG_Address);
	}

	private static final String IMG_Address72 = "img_Address72";
	public Image img_Address72() {
		return registry.get(IMG_Address72);
	}
	public ImageDescriptor des_Address72() {
		return registry.getDescriptor(IMG_Address72);
	}

	private static final String IMG_Help = "img_Help";
	public Image img_Help() {
		return registry.get(IMG_Help);
	}
	public ImageDescriptor des_Help() {
		return registry.getDescriptor(IMG_Help);
	}

	private static final String IMG_Fias = "img_Fias";
	public Image img_Fias() {
		return registry.get(IMG_Fias);
	}
	public ImageDescriptor des_Fias() {
		return registry.getDescriptor(IMG_Fias);
	}

	private static final String IMG_Ds = "img_Db";
	public Image img_Db() {
		return registry.get(IMG_Ds);
	}
	public ImageDescriptor des_Db() {
		return registry.getDescriptor(IMG_Ds);
	}

	private static final String IMG_Back = "img_Back";
	public Image img_Back() {
		return registry.get(IMG_Back);
	}
	public ImageDescriptor des_Back() {
		return registry.getDescriptor(IMG_Back);
	}

	private static final String IMG_Abonents = "img_Abonents";
	public Image img_Abonents() {
		return registry.get(IMG_Abonents);
	}
	public ImageDescriptor des_Clients() {
		return registry.getDescriptor(IMG_Abonents);
	}

	private static final String IMG_Abonent = "img_Abonent";
	public Image img_Abonent() {
		return registry.get(IMG_Abonent);
	}
	public ImageDescriptor des_Abonent() {
		return registry.getDescriptor(IMG_Abonent);
	}

	private static final String IMG_Add = "img_Add";
	public Image img_Add() {
		return registry.get(IMG_Add);
	}
	public ImageDescriptor des_Add() {
		return registry.getDescriptor(IMG_Add);
	}

	private static final String IMG_Edit = "img_Edit";
	public Image img_Edit() {
		return registry.get(IMG_Edit);
	}
	public ImageDescriptor des_Edit() {
		return registry.getDescriptor(IMG_Edit);
	}

	private static final String IMG_Remove = "img_Remove";
	public Image img_Remove() {
		return registry.get(IMG_Remove);
	}
	public ImageDescriptor des_Remove() {
		return registry.getDescriptor(IMG_Remove);
	}

	private static DImages instance;
	public static DImages instance() {
		if (instance == null) {
			instance = new DImages();
		}
		return instance;
	}
}