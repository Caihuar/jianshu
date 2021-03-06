package com.example.dabutaizha.lines.mvp.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dabutaizha.lines.Constant;
import com.example.dabutaizha.lines.ImageUtil.ImageLoader;
import com.example.dabutaizha.lines.R;
import com.example.dabutaizha.lines.ResUtil;
import com.example.dabutaizha.lines.SentenceUtil;
import com.example.dabutaizha.lines.bean.info.SearchInfo;
import com.example.dabutaizha.lines.bean.model.SentencesModel;
import com.example.dabutaizha.lines.mvp.contract.AddSentenceContract;
import com.example.dabutaizha.lines.mvp.presenter.AddSentencePresenter;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import slideDampongAnimationLayout.SlideDampingAnimationLayout;
import slideDampongAnimationLayout.SlideEventListener;

/**
 * Copyright (C) 2018 Unicorn, Inc.
 * Description :
 * Created by dabutaizha on 2018/6/24 下午1:40.
 */

public class AddSentenceActivity extends BaseActivity implements AddSentenceContract.View {

    @BindView(R.id.add_sentence_sliding_layout)
    public SlideDampingAnimationLayout mSlideDampingAnimationLayout;
    @BindView(R.id.add_sentence_background_layout)
    public RelativeLayout mBackgroundLayout;
    @BindView(R.id.toolbar)
    public android.support.v7.widget.Toolbar mToolbar;
    @BindView(R.id.add_sentence_top_image_bg)
    public ImageView mBackground;
    @BindView(R.id.add_sentence_btn)
    public FloatingActionButton mFloatingActionButton;
    @BindView(R.id.add_sentence_article)
    public MaterialEditText mEdtArticle;
    @BindView(R.id.add_sentence_author)
    public MaterialEditText mEdtAuthor;
    @BindView(R.id.add_sentence_content)
    public MaterialEditText mEdtContent;

    private AddSentenceContract.Presenter mPresenter;

    /**
     *Description: BaseActivity
     */
    @Override
    protected void initData() {
        mPresenter = new AddSentencePresenter(this);
        mPresenter.initData();
    }

    @Override
    protected void initView() {
        // 键盘遮盖布局，暂时使用adjust_pan属性，由于adjust_resize属性失效
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.add_sentence_title);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mToolbar.setNavigationIcon(R.drawable.back);

        ImageLoader.loadImageByRes(this, mBackground, R.drawable.card_default_bg);
    }

    @Override
    protected void initViewListener() {
        mSlideDampingAnimationLayout.setSlideListener(new SlideEventListener() {
            @Override
            public void leftEvent() {
                finish();
            }

            @Override
            public void rightEvent() {

            }
        });

        mToolbar.setNavigationOnClickListener(view -> finish());

        mFloatingActionButton.setOnClickListener(view -> {
            String article = mEdtArticle.getText().toString();
            String author = mEdtAuthor.getText().toString();
            String content = mEdtContent.getText().toString();
            if (!article.trim().isEmpty()
                    && !author.trim().isEmpty()
                    && !content.trim().isEmpty()) {
                mPresenter.createSentence(article, author, content);
            } else {
                showMessage(ResUtil.getString(R.string.input_error));
            }
        });
    }

    @Override
    protected void initTheme(int themeId) {
        switch (themeId) {
            case Constant.DAY_TIME:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.black));
                mToolbar.setNavigationIcon(R.drawable.back);

                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.colorPrimary));

                setEditTextColor(mEdtAuthor, R.color.black);
                setEditTextColor(mEdtArticle, R.color.black);
                setEditTextColor(mEdtContent, R.color.black);

                mFloatingActionButton.setColorFilter(ResUtil.getColor(R.color.black));
                break;
            case Constant.NIGHT:
                mToolbar.setBackgroundColor(ResUtil.getColor(R.color.status_bar_night));
                mToolbar.setTitleTextColor(ResUtil.getColor(R.color.white));
                mToolbar.setNavigationIcon(R.drawable.back_white);

                mBackgroundLayout.setBackgroundColor(ResUtil.getColor(R.color.background_night));

                setEditTextColor(mEdtAuthor, R.color.white);
                setEditTextColor(mEdtArticle, R.color.white);
                setEditTextColor(mEdtContent, R.color.white);

                mFloatingActionButton.setColorFilter(ResUtil.getColor(R.color.red_text_bg));
                break;
            default:
                break;
        }
    }

    @Override
    protected void process() {
        mPresenter.process();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_add_sentence;
    }

    @Override
    public void jumpToSharedCardActivity(SentencesModel model) {
        SearchInfo.SentencesItem sentencesItem = SentenceUtil.model2item(model);

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.SHARE_INFO, sentencesItem);
//        ShareActivity.startActivity(this, bundle);

        showMessage(ResUtil.getString(R.string.created_sentence));
    }

    /**
     *Description: AddSentenceContract.View
     */
    @Override
    public void showMessage(String msg) {
        ResUtil.showToast(this, msg);
    }

    private void setEditTextColor(MaterialEditText editText, int colorResId) {
        editText.setUnderlineColor(ResUtil.getColor(colorResId));
        editText.setBaseColor(ResUtil.getColor(colorResId));
        editText.setPrimaryColor(ResUtil.getColor(colorResId));
        editText.setTextColor(ResUtil.getColor(colorResId));

    }
}
