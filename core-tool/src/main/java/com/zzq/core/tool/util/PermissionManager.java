package com.zzq.core.tool.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.Size;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.zzq.core.tool.fragment.MessageDialogFragment;
import com.zzq.core.tool.interfaces.DialogCallback;

/**
 * 请求权限
 */
public class PermissionManager {

    private static PermissionManager mInstance;
    private AppCompatActivity mActivity;
    private Fragment mFragment;
    private int mRequestCode;
    private String[] permissionArr;
    private int mMessageRes;

    private PermissionManager() {
    }

    public static PermissionManager newInstance() {
        /*if (mInstance == null) {
            mInstance = new PermissionManager();
        }*/
        mInstance = new PermissionManager();
        return mInstance;
    }

    public void requireMultiPermissions(@NonNull AppCompatActivity activity,
                                        @NonNull String[] permissions, final @IntRange(from = 0) int requestCode, int messageRes) {
        mActivity = activity;
        mRequestCode = requestCode;
        permissionArr = permissions;
        mMessageRes = messageRes;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //申请多项权限
            if (hasPermissionDeny(mActivity, permissionArr)) {
                //第一次请求若被拒绝，下一次请求应用shouldShowRequestPermissionRationale表示为什么需要该权限
                if (needShowRequestPermission(permissionArr)) {
                    MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(
                            activity.getString(messageRes), "");
                    dialogFragment.setCancelable(false);
                    dialogFragment.setDialogCallback(new DialogCallback() {
                        @Override
                        public void confirm() {
                            ActivityCompat.requestPermissions(mActivity,
                                    permissionArr, mRequestCode);
                        }

                        @Override
                        public void cancel() {
                            mActivity.finish();
                        }
                    });
                    dialogFragment.show(activity.getSupportFragmentManager(), "permission");
                } else {
                    ActivityCompat.requestPermissions(mActivity, permissionArr, mRequestCode);
                }
            } else {
                //已经获取到权限了
                if (mPermissionInfoListener != null) {
                    mPermissionInfoListener.grantPermissions(requestCode, permissions);
                }
            }
        } else {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.grantPermissions(requestCode, permissions);
            }
        }
    }

    public void requireMultiPermissions(@NonNull Fragment fragment,
                                        @NonNull String[] permissions, final @IntRange(from = 0) int requestCode, int messageRes) {
        mFragment = fragment;
        mRequestCode = requestCode;
        permissionArr = permissions;
        mMessageRes = messageRes;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //申请多项权限
            if (hasPermissionDeny(fragment.getActivity(), permissionArr)) {
                //第一次请求若被拒绝，下一次请求应用shouldShowRequestPermissionRationale表示为什么需要该权限
                if (needShowRequestPermission(mFragment, permissionArr)) {
                    MessageDialogFragment dialogFragment = MessageDialogFragment.newInstance(
                            fragment.getString(messageRes), "");
                    dialogFragment.setCancelable(false);
                    dialogFragment.setDialogCallback(new DialogCallback() {
                        @Override
                        public void confirm() {
                            mFragment.requestPermissions(permissionArr, mRequestCode);
                        }

                        @Override
                        public void cancel() {
                            mFragment.getActivity().finish();
                        }
                    });
                    dialogFragment.show(mFragment.getActivity().getSupportFragmentManager(), "permission");
                } else {
                    mFragment.requestPermissions(permissionArr, mRequestCode);
                }
            } else {
                //已经获取到权限了
                if (mPermissionInfoListener != null) {
                    mPermissionInfoListener.grantPermissions(requestCode, permissions);
                }
            }
        } else {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.grantPermissions(requestCode, permissions);
            }
        }
    }

    /**
     * 在被拒绝后再次申请，否则会崩溃
     */
    public void requirePermissionAgain() {
        ActivityCompat.requestPermissions(mActivity,
                permissionArr, mRequestCode);
    }

    /**
     * 在被拒绝后再次申请，否则会崩溃
     */
    public void requirePermissionAgain(Fragment fragment) {
        fragment.requestPermissions(permissionArr, mRequestCode);
    }

    private void requireSinglePermissionResult(int type, int requestCode, @NonNull String[] permissions, @NonNull int grantResult) {
        if (grantResult == PackageManager.PERMISSION_GRANTED) {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.grantPermissions(requestCode, permissions);
            }
        } else if (type == 1 && !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.refusePermissionsAndNotAsk(requestCode, null, null, permissions);
            }
        } else if (type == 2 && !mFragment.shouldShowRequestPermissionRationale(permissions[0])) {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.refusePermissionsAndNotAsk(requestCode, null, null, permissions);
            }
        } else {
            if (mPermissionInfoListener != null) {
                mPermissionInfoListener.refusePermissions(requestCode, null, permissions);
            }
        }
    }

    private void requireMultiPermissionsResult(int type, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length == permissionArr.length) {
            StringBuilder successStr = new StringBuilder();
            StringBuilder refuseStr = new StringBuilder();
            StringBuilder refuseNoAskStr = new StringBuilder();
            int successCount = 0;
            int refuseCount = 0;
            int refuseNoAskCount = 0;
            String msg = "";
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    //同意给出权限
                    successStr.append(permissions[i]).append("/");
                    successCount++;
                    msg += " " + permissions[i] + "权限已获取 ";
                } else if (type == 1 && !ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[i])) {
                    //拒绝，且不再询问
                    refuseNoAskCount++;
                    refuseNoAskStr.append(permissions[i]).append("/");
                    msg += " " + permissions[i] + "权限已经设置不再提醒 ";
                } else if (type == 2 && !mFragment.shouldShowRequestPermissionRationale(permissions[i])) {
                    //拒绝，且不再询问
                    refuseNoAskCount++;
                    refuseNoAskStr.append(permissions[i]).append("/");
                    msg += " " + permissions[i] + "权限已经设置不再提醒 ";
                } else {
                    //拒绝
                    refuseStr.append(permissions[i]).append("/");
                    refuseCount++;
                    msg += " " + permissions[i] + "拒绝 ";
                }
            }

            if (mPermissionInfoListener != null) {
                if (successCount == grantResults.length) {
                    //全部都是授予权限
                    mPermissionInfoListener.grantPermissions(requestCode, permissions);
                } else if (refuseNoAskCount == 0) {
                    //全部都是禁止授予权限
                    mPermissionInfoListener.refusePermissions(requestCode,
                            successStr.toString().split("/"),
                            refuseStr.toString().split("/"));
                } else {
                    mPermissionInfoListener.refusePermissionsAndNotAsk(requestCode,
                            successStr.toString().split("/"),
                            refuseStr.toString().split(" /"),
                            refuseNoAskStr.toString().split("/"));
                }
            }
        }
    }

    /**
     * 在Activity的onRequestPermissionsResult()方法中调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRequestCode == requestCode) {
            if (grantResults.length != 0) {
                //单项权限
                if (grantResults.length == 1) {
                    requireSinglePermissionResult(1, requestCode, permissions, grantResults[0]);
                } else {//申请多项权限
                    requireMultiPermissionsResult(1, requestCode, permissions, grantResults);
                }
            }
        }
    }

    /**
     * 在Fragment的onRequestPermissionsResult()方法中调用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onFragmentRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mRequestCode == requestCode) {
            if (grantResults.length != 0) {
                //单项权限
                if (grantResults.length == 1) {
                    requireSinglePermissionResult(2, requestCode, permissions, grantResults[0]);
                } else {//申请多项权限
                    requireMultiPermissionsResult(2, requestCode, permissions, grantResults);
                }
            }
        }
    }

    /**
     * 如果已经获取到对应权限，则返回true
     *
     * @param context
     * @param permission
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean hasPermission(@NonNull Context context, @Size(min = 1L) @NonNull String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 如果在想要获取权限的多个权限中，有任一权限已经被拒绝，则返回true
     *
     * @param context
     * @param permissionsArr
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean hasPermissionDeny(@NonNull Context context, @Size(min = 1L) @NonNull String[] permissionsArr) {
        for (String aPermissionsArr : permissionsArr) {
            int selfPermission = ContextCompat.checkSelfPermission(context, aPermissionsArr);
            if (selfPermission != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean needShowRequestPermission(@Size(min = 1L) @NonNull String[] permissionsArr) {
        for (int i = 0; i < permissionsArr.length; i++) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissionsArr[i])) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean needShowRequestPermission(Fragment fragment, @Size(min = 1L) @NonNull String[] permissionsArr) {
        for (int i = 0; i < permissionsArr.length; i++) {
            if (fragment.shouldShowRequestPermissionRationale(permissionsArr[i])) {
                return true;
            }
        }
        return false;
    }

    public void goToAppSettingActivity(int requestCode) {
        Intent intent = new Intent()
                .setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                .setData(Uri.fromParts("package",
                        mActivity.getPackageName(), null));
        mActivity.startActivityForResult(intent, requestCode);
    }

    public void goToAppSettingActivityFromFragment(int requestCode) {
        Intent intent = new Intent()
                .setAction("android.settings.APPLICATION_DETAILS_SETTINGS")
                .setData(Uri.fromParts("package",
                        mFragment.getActivity().getPackageName(), null));
        mFragment.getActivity().startActivityForResult(intent, requestCode);
    }

    public boolean isGreaterSdkVerSion23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public interface PermissionInfoListener {

        /**
         * 多项权限都被授予时调用，或单项权限申请通过
         *
         * @param requestCode 请求码
         * @param permissions 哪些权限全部被授予
         */
        void grantPermissions(int requestCode, @NonNull String[] permissions);

        /**
         * 部分权限或者是全部权限时被禁止授予权限：两种状态：成功或者禁止
         * <p>
         * 如果是单项权限申请，那么{@param successPermissions}就可能为空
         *
         * @param requestCode        请求码
         * @param successPermissions 成功申请到的权限
         * @param refusePermissions  被拒绝的权限
         */
        void refusePermissions(int requestCode, String[] successPermissions, String[] refusePermissions);

        /**
         * 部分权限或者是全部权限时被禁止授予权限且不再询问：三种状态：成功或者禁止或者禁止且不再询问
         * <p>
         * 如果是单项权限申请，那么 successPermissions 就可能为空, refusePermissions 可能为空
         *
         * @param requestCode            请求码
         * @param successPermissions     成功申请到的权限
         * @param refusePermissions      被拒绝的权限
         * @param refuseNoAskPermissions 被拒绝且要求不再请求的权限
         */
        void refusePermissionsAndNotAsk(int requestCode, String[] successPermissions, String[] refusePermissions, String[] refuseNoAskPermissions);
    }

    private PermissionInfoListener mPermissionInfoListener;

    public void setPermissionInfoListener(PermissionInfoListener permissionInfoListener) {
        this.mPermissionInfoListener = permissionInfoListener;
    }
}
