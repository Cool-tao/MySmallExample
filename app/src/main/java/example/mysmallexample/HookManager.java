package example.mysmallexample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipFile;

import org.apache.http.Header;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flongjce.util.SerializeUtil;
import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;
import com.taobao.android.dexposed.XposedHelpers;

public class HookManager {

	public static AssetManager assetManager;

	public static Resources resources;

	private static final String TAG = HookManager.class.getSimpleName();

	private static String targetPkgName;

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
		DexposedBridge.findAndHookMethod(Throwable.class, "getStackTrace", x2);
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
								ApplicationInfo applicationInfo = new ApplicationInfo(
										app);
								applicationInfo.sourceDir = srcDir;
								Log.e(TAG, "getApplicationInfo:"
										+ applicationInfo.sourceDir);

								param.setResult(applicationInfo);
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



	private static void hookAllMethod(final Class class1) {
		Method[] methods = class1.getDeclaredMethods();
		for (Method method : methods) {
			try {
				Log.e(TAG, class1.getName() + "." + method.getName());

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
						Log.e(TAG, TAG + ":" + class1.getName() + "."
								+ param.method.getName());

						for (Object object : objects) {
							if (object instanceof Class) {
								Log.e(TAG, TAG + ":" + "param:"
										+ ((Class) object).getName());
							}
						}

						Log.e(TAG,
								"beforeHookedMethod:" + param.method.getName());
						for (Object object : param.args) {
							if (object instanceof byte[]) {
								Log.e(TAG, "param.args" + ":"
										+ new String((byte[]) object));
							} else {
								Log.e(TAG, "param.args" + ":" + object);
							}
						}

						// Object iC = XposedHelpers.getObjectField(
						// param.thisObject, "iC");
						//

						//
						// Log.e(TAG, "iC" + ":" + iC);
						

					}

					@Override
					protected void afterHookedMethod(MethodHookParam param)
							throws Throwable {
						super.afterHookedMethod(param);
						Log.e(TAG,
								"afterHookedMethod:" + param.method.getName());

						if (param.getResult() instanceof byte[]) {
							String s = new String((byte[]) param.getResult());

							Log.e(TAG, "param.getResult()" + ":" + s);
							

						} else {
							Log.e(TAG,
									"param.getResult()" + ":"
											+ param.getResult());
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
