package ru.mpk.client.service.nebula;

import org.eclipse.nebula.animation.AnimationRunner;
import org.eclipse.nebula.animation.effects.AlphaEffect;
import org.eclipse.nebula.animation.effects.IEffect;
import org.eclipse.nebula.animation.effects.SetColorEffect;
import org.eclipse.nebula.animation.effects.SetColorEffect.IColoredObject;
import org.eclipse.nebula.animation.effects.ShakeEffect;
import org.eclipse.nebula.animation.movement.ElasticOut;
import org.eclipse.nebula.animation.movement.ExpoOut;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class NebulaUtils {
	public static void messageInfo(Shell shell, String text) {
    	final TrayMessage msg = new TrayMessage(shell, text);
        msg.open();
        new Thread() {
        	public void run() {
        		try {
        			Thread.sleep(1000);
        			Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							AnimationRunner runner = new AnimationRunner();
		        	        final AlphaEffect effect = new AlphaEffect(msg.getShell(), 255, 0, 8000, new ExpoOut(), new Runnable() {
		        				public void run() {
		        					msg.close();
		        				}
		        			}, null);
		        	        runner.runEffect(effect);
						}
					});
        		} catch (InterruptedException ex) {
        			throw new RuntimeException(ex);
        		}
        	}
        }.start();
    }
	
	public static void shake(Control c, Runnable onStop) {    	
		AnimationRunner runner = new AnimationRunner();
        Runnable empty = new Runnable() {
			public void run() {}
		};
		final Point loc = c.getLocation();
		IEffect effect = new ShakeEffect(c,
				new Point(loc.x, loc.y), 
				new Point(loc.x + 2, loc.y), 500,
				new ElasticOut(), 
				new Runnable() {
					public void run() {
						c.setLocation(loc);
						onStop.run();
					}
				}, empty);
		runner.runEffect(effect);
    }
	
	public static IBlinkElement startColorBlink(final Control control, final Color dest) {
		final Color source0 = control.getBackground();
		final Color dest0 = dest;
		final CyclicRunner runner = new CyclicRunner() {
			int flag = 0;
			public IEffect create(Runnable closed) {
				IColoredObject coloredObject = new IColoredObject() {
					public void setColor(final Color c) {
						control.setBackground(c);
						control.update();
					}
					public Color getColor() {
						return control.getBackground();
					}
				};
				if (flag == 0) {
					flag = 1;
					return new SetColorEffect(coloredObject, source0, dest0, 1000, new ExpoOut(), closed, null);
				} else {
					flag = 0;
					return new SetColorEffect(coloredObject, dest0, source0, 1000, new ExpoOut(), closed, null);
				}
			}
		};
		control.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				runner.stop();
			}
		});
		runner.start();
        return new IBlinkElement() {
			public void stop() {
				runner.stop();
			}
		};
	}
	
	private static abstract class CyclicRunner {
		private boolean stopped;
		AnimationRunner runner = new AnimationRunner();
		private IEffect current;
		private Runnable done = new Runnable() {
			public void run() {
				if (!stopped) {
					start();					
				}
			}
		};
		public synchronized void start() {
			if (current != null) current.cancel();
			current = create(done);
			runner.runEffect(current);
		}
		public abstract IEffect create(Runnable closed);
		public synchronized void stop() {
			stopped = true;
			current.cancel();
		}
	}
}
