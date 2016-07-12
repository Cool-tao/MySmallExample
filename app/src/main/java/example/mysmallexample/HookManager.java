package example.mysmallexample;

import java.io.IOException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;

public class HookManager {

	public static AssetManager assetManager;

	public static Resources resources;

	private static final String TAG = HookManager.class.getSimpleName();

	private static String targetPkgName;

	@SuppressWarnings("unused")
	private static boolean isLDevice() {
		return android.os.Build.VERSION.SDK_INT >= 20;
	}

	public static void load() {
		Log.e("HookManager", "load");
	}

	static {
		try {
			if (android.os.Build.VERSION.SDK_INT == 22) {
				Log.e("System_load", "load dexposed_l51");
				System.loadLibrary("dexposed_l51");
			} else if (android.os.Build.VERSION.SDK_INT > 19
					&& android.os.Build.VERSION.SDK_INT <= 21) {
				Log.e("System_load", "load dexposed_l");
				System.loadLibrary("dexposed_l");
			} else if (android.os.Build.VERSION.SDK_INT > 14) {
				Log.e("System_load", "load dexposed");
				System.loadLibrary("dexposed");
			}
		} catch (Throwable e) {
			Log.e("System_load", "load dexposed exception");
			e.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public static boolean hookAssest(Context context) throws IOException {
		// hookAllMethod(AssetManager.class);
		// DexposedBridge.findAndHookMethod(Throwable.class, "getStackTrace",
		// x2);
		// hookAllMethod(ZipFile.class);
		// hookAllMethod(ZipInputStream.class);
		// hookAllMethod(ZipOutputStream.class);

		// hookAllMethod(ContextWrapper.class);

		// applicationInfo.sourceDir = srcDir;
		// new File(context.getFilesDir(),
		// "new/com.joym.ultrfight-2.apk").getAbsolutePath();
		try {

			DexposedBridge.findAndHookMethod(
					Class.forName("android.app.ApplicationPackageManager"),
					"getApplicationInfo", String.class, int.class,
					new XC_MethodHook() {
						@Override
						protected void afterHookedMethod(MethodHookParam param)
								throws Throwable {
							super.afterHookedMethod(param);

							new Exception().printStackTrace();

							if (param.args[0].equals(targetPkgName)) {
								// ApplicationInfo applicationInfo = new
								// ApplicationInfo(app);
								// applicationInfo.sourceDir = srcDir;
								// Log.e(TAG, "getApplicationInfo:"+
								// applicationInfo.sourceDir);
								// param.setResult(applicationInfo);
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// DexposedBridge.hookAllConstructors(AssetManager.class,
		// new XC_MethodHook() {
		// @Override
		// protected void beforeHookedMethod(MethodHookParam param)
		// throws Throwable {
		// super.beforeHookedMethod(param);
		// new Exception().printStackTrace(System.err);
		// Log.e(TAG, TAG + ":" + AssetManager.class.getName()
		// + "." + param.method.getName());
		// Log.e(TAG,
		// "beforeHookedMethod:" + param.method.getName());
		// for (Object object : param.args) {
		// Log.e(TAG, "param.args" + ":" + object);
		// }
		// }
		//
		// @Override
		// protected void afterHookedMethod(MethodHookParam param)
		// throws Throwable {
		// super.afterHookedMethod(param);
		// Log.e(TAG,
		// "afterHookedMethod:" + param.method.getName());
		// Log.e(TAG,
		// "param.getResult()" + ":" + param.getResult());
		// }
		// });

		// new Date();
		// AssetManager assetManager;
		// try {
		// assetManager = AssetManager.class.newInstance();
		// Log.e(TAG, assetManager.toString());
		//
		// } catch (InstantiationException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IllegalAccessException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		Log.e(TAG, " hook success!");
		return true;
	}

	// private static void showStack(File dir) throws Exception {
	// StackTraceElement[] elements = getStack(dir);
	// for (StackTraceElement element : elements) {
	// Log.e(TAG,
	// TAG + ":" + element.getClassName() + "->"
	// + element.getMethodName() + "("
	// + element.getLineNumber() + ")");
	// }
	// }

	@SuppressWarnings("rawtypes")
	public static void hookAllMethod(final Class class1) {
		Method[] methods = class1.getDeclaredMethods();

		// Main shared = new Main(tid);
		for (Method method : methods) {
			try {
				Log.i(TAG,
						"message hook 0:" + class1.getName() + "."
								+ method.getName() + ",  ThreadID="
								+ Thread.currentThread().getId());

				final Object[] objects = new Object[method.getParameterTypes().length + 1];

				for (int i = 0; i < method.getParameterTypes().length; i++) {
					objects[i] = method.getParameterTypes()[i];
				}

				objects[objects.length - 1] = new XC_MethodHook() {
					@Override
					protected void beforeHookedMethod(MethodHookParam param)
							throws Throwable {
						super.beforeHookedMethod(param);
						// new Exception().printStackTrace(System.err);
						Log.e(TAG, "message hook  1:" + class1.getName() + "."
								+ param.method.getName() + ",  ThreadID="
								+ Thread.currentThread().getId());

						Log.e(TAG, "message hook  1111:" + ",  ThreadID="
								+ Thread.currentThread().getId());

						for (Object object : objects) {
							if (object instanceof Class) {
								Log.i(TAG, "message hook 2=" + class1.getName()
										+ "." + param.method.getName() + " , "
										+ "param:" + ((Class) object).getName()
										+ ",    ThreadID="
										+ Thread.currentThread().getId());
								Log.i(TAG,
										"message hook  3 ="
												+ class1.getName()
												+ "."
												+ param.method.getName()
												+ " , "
												+ "param object="
												+ object.toString()
												+ ",  ThreadID="
												+ Thread.currentThread()
														.getId());
							}
						}

						Log.e(TAG,
								"message hook 4:" + "beforeHookedMethod:"
										+ class1.getName() + "."
										+ param.method.getName()
										+ ",    ThreadID="
										+ Thread.currentThread().getId());

						// if (param.args.length > 1) {
						// Log.e(TAG, "message hook 4444:"
						// + "beforeHookedMethod:" + "param.args[0]="
						// + param.args[0] + ",param.args[1]="
						// + param.args[1]);
						// }

						for (Object object : param.args) {
							if (object instanceof byte[]) {
								Log.e(TAG, "message hook 5:  " + "param.args"
										+ ":" + class1.getName() + "."
										+ param.method.getName() + ","
										+ new String((byte[]) object)
										+ ",  ThreadID="
										+ Thread.currentThread().getId());
							} else {
								Log.e(TAG,
										"message hook 6 :"
												+ "param.args"
												+ ":"
												+ class1.getName()
												+ "."
												+ param.method.getName()
												+ ","
												+ object.toString()
												+ ", hashCode="
												+ param.method.getName()
														.hashCode()
												+ ",  ThreadID="
												+ Thread.currentThread()
														.getId());
							}
						}

						RuntimeException re = new RuntimeException();
						re.fillInStackTrace();
						Log.e("info", "message info", re);

					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						super.afterHookedMethod(param);
						Log.e(TAG, "message hook 7777:" + ",  ThreadID="
								+ Thread.currentThread().getId());
						Log.e(TAG,
								"afterHookedMethod:" + param.method.getName());

						if (param.getResult() instanceof byte[]) {
							String s = new String((byte[]) param.getResult());

							Log.e(TAG, "param.getResult()  77" + ":" + s
									+ ",  ThreadID="
									+ Thread.currentThread().getId());

						} else {
							Log.e(TAG,
									"param.getResult()   88" + ":"
											+ param.getResult()
											+ ",  ThreadID="
											+ Thread.currentThread().getId());
						}

					}

				};
				DexposedBridge.findAndHookMethod(class1, method.getName(),
						objects);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
