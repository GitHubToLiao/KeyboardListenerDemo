package com.izxjf.liao.keyboardlistener;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

/**
 * 创建日期:2018/5/5 on 21:46
 * 作   者:张辽
 * 邮   箱:Zl13484407109@sina.com
 * 描   述:
 */

public class KeyboardListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private Context mContext;
    private boolean isKeyboardOpened = false;//键盘是否打开
    private OnKeyboardListener mKeyboardListener;//键盘状态监听
    private Window mWindow;
    private int mNavigationBarHeight = 0;//状态栏高度
    private View contentView;

    public KeyboardListener(Context mContext) {
        this.mContext = mContext;
        mWindow = ((Activity) mContext).getWindow();
        if (mWindow == null) {
            return;
        }
        View decorView = mWindow.getDecorView();
        if (decorView == null) {
            return;
        }
        //获取contentView
        contentView = decorView.findViewById(android.R.id.content);
        if (contentView == null) {
            return;
        }
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(this);//监听布局树是否发生改变

        //获取状态栏高度
        Resources resources = mContext.getResources();
        int navigationBarID = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        mNavigationBarHeight = resources.getDimensionPixelOffset(navigationBarID);

    }


    @Override
    public void onGlobalLayout() {
        if (mKeyboardListener != null) {
            //获取布局可视区域
            if (contentView == null) {
                return;
            }
            Rect rect = new Rect();
            contentView.getWindowVisibleDisplayFrame(rect);//获取布局的可视区域

            int keyboardHeight = contentView.getHeight() - (rect.bottom - rect.top);
            if (!isKeyboardOpened && keyboardHeight > 0) {
                isKeyboardOpened = true;
                mKeyboardListener.onKeyboardOpened(keyboardHeight - mNavigationBarHeight);
            } else if (isKeyboardOpened && keyboardHeight <= 0) {
                isKeyboardOpened = false;
                mKeyboardListener.onKeyboardClose();
            }

        }
    }


    public void removeGlobalLayoutListener() {
        if(contentView ==null){
            return;
        }
        contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    public void setOnKeyboardListener(OnKeyboardListener onKeyboardListener) {
        this.mKeyboardListener = onKeyboardListener;
    }


    public interface OnKeyboardListener {
        void onKeyboardOpened(int keyboardHeight);

        void onKeyboardClose();
    }

}
