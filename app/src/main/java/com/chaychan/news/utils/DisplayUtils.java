package com.chaychan.news.utils;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chaychan.news.R;

public class DisplayUtils {

    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //不是第一个的格子都设一个上边和底部的间距  这些间隔大小可以自行修改
            int pos = parent.getChildLayoutPosition(view);  //当前条目的position
            int itemCount = state.getItemCount()-1;           //最后一条的postion

            if(pos==itemCount){   //最后一条
                outRect.bottom = 0;
                outRect.top = 10;
            }else {
                if (pos % 2 == 1) {  //下面一行

                    outRect.bottom = 10;
                    outRect.top = 10;
                } else { //上面一行
                    if (pos == 0) {
                        outRect.bottom = 10;
                        outRect.top = 0;
                    } else {
                        outRect.top = 10;
                        outRect.bottom = 10;
                    }


                }}
}}




    public static class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {

        private int mDividerHeight;  //分割线高度

        public SimpleDividerItemDecoration(int mDividerHeight) {
            this.mDividerHeight = mDividerHeight;
        }

        //获取分割线尺寸
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);

            int childAdapterPosition = parent.getChildAdapterPosition(view);

            int lastCount = parent.getAdapter().getItemCount() - 1;

            if (childAdapterPosition == 0) {
                outRect.set(0, 0, 0, 0);
                return;
            }

            if (childAdapterPosition == lastCount) {
                outRect.set(0, 0, 0, 0);
                return;
            }
            outRect.set(0, 0, 0, mDividerHeight);

        }
    }
}
