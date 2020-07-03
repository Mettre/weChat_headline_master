package com.chaychan.news.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaychan.news.R;
import com.chaychan.news.enum_.MomentsEnum;
import com.chaychan.news.event.StartBrotherEvent;
import com.chaychan.news.model.entity.ReleaseMoments;
import com.chaychan.news.model.response.ResultResponse;
import com.chaychan.news.ui.base.BaseActivity;
import com.chaychan.news.ui.presenter.ReleaseMomentsPresenter;
import com.chaychan.news.utils.CompressImageUtils;
import com.chaychan.news.utils.SoftUtils;
import com.chaychan.news.utils.ToastUtils;
import com.chaychan.news.utils.UIUtils;
import com.chaychan.news.view.MomentsReleaseListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;
import flyn.Eyes;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * 发布说说
 */
public class ReleaseMomentsActivity extends BaseActivity<ReleaseMomentsPresenter> implements EasyPermissions.PermissionCallbacks, View.OnClickListener, MomentsReleaseListener<Object>, BGASortableNinePhotoLayout.Delegate {

    private static final int PRC_PHOTO_PICKER = 1;

    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;

    @Bind(R.id.group_view)
    LinearLayout groupView;

    @Bind(R.id.tv_author)
    TextView mTvAuthor;

    @Bind(R.id.right_btn)
    TextView right_btn;

    @Bind(R.id.iv_back)
    ImageView iv_back;

    @Bind(R.id.nickname_edt)
    EditText editText;

    @Bind(R.id.len_num)
    TextView len_num;

    @Bind(R.id.snpl_moment_add_photos)
    BGASortableNinePhotoLayout mPhotosSnpl;

    private int maxNum = 240;
    private String faceBackContent;
    private MomentsEnum momentsType;
    private ReleaseMoments releaseMoments = new ReleaseMoments();

    public static void startActivity(Context mContext, MomentsEnum momentsType) {
        Intent intent = new Intent(mContext, ReleaseMomentsActivity.class);
        intent.putExtra("momentsType", momentsType);
        mContext.startActivity(intent);
    }

    @Override
    protected ReleaseMomentsPresenter createPresenter() {
        return new ReleaseMomentsPresenter(this);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_release_moments;
    }

    @Override
    public void initView() {
        Eyes.setStatusBarColor(this, UIUtils.getColor(R.color.color_3333));//设置状态栏的颜色为灰色
        momentsType = (MomentsEnum) getIntent().getSerializableExtra("momentsType");
        SoftUtils.setupUI(this, groupView);
        mTvAuthor.setVisibility(View.GONE);
        right_btn.setText("发布");

        switch (momentsType) {
            case PURE_TEXT:
                mPhotosSnpl.setVisibility(View.GONE);
                break;
            case PHOTO:
                mPhotosSnpl.setVisibility(View.VISIBLE);
                break;
        }

        mPhotosSnpl.setMaxItemCount(1);
        mPhotosSnpl.setPlusEnable(true);
        mPhotosSnpl.setDelegate(this);
    }


    @Override
    public void initListener() {
        right_btn.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        len_num.setText("0/" + maxNum);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = editText.getText();
                int len = editable.length();

                len_num.setText(len + "/" + maxNum);
                if (len > maxNum) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, maxNum);
                    editText.setText(newStr);
                    editable = editText.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.right_btn:
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    UIUtils.showToast("说说内容不能为空");
                    return;
                }
                faceBackContent = editText.getText().toString();

                releaseMoments.setMomentsTitle(faceBackContent);
                releaseMoments.setMomentsType(momentsType);
                switch (momentsType) {
                    case PURE_TEXT:
                        mPresenter.releaseMoments(releaseMoments);
                        break;
                    case PHOTO:
                        if (mPhotosSnpl.getData() != null && mPhotosSnpl.getData().size() > 0) {
                            List<File> fileList = CompressImageUtils.CompressImage(mPhotosSnpl.getData(), ReleaseMomentsActivity.this);
                            mPresenter.uploadFileRequest(fileList);
                        } else {
                            releaseMoments.setMomentsType(MomentsEnum.PURE_TEXT);
                            mPresenter.releaseMoments(releaseMoments);
                        }
                        break;
                }

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {

            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));

        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }


    @Override
    public void onReleaseSuccess(Object response) {
        EventBus.getDefault().post(new StartBrotherEvent(StartBrotherEvent.RELEASEMOMENTS));
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("")
                .setContentText("发布成功")
                .setConfirmText("关闭")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                })
                .show();
    }


    @Override
    public void onUpFileSuccess(Object response) {
        releaseMoments.setMomentsImage(faceBackContent);
        mPresenter.releaseMoments(releaseMoments);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");

            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            Toast.makeText(ReleaseMomentsActivity.this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }
}
