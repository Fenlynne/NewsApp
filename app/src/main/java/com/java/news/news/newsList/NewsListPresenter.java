package com.java.news.news.newsList;
/*
 * Created by ljf on 2019-8-30.
 */

import android.annotation.SuppressLint;

import androidx.annotation.BinderThread;

import com.java.news.data.NewsEntity;
import com.java.news.data.RealmHelper;
import com.java.news.http.NewsResponse;
import com.java.news.http.RetrofitManager;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NewsListPresenter implements NewsListContract.Presenter {
    private final String PAGE_SIZE = "20";

    private NewsListContract.View mNewsListView;
    private String mCatogory;
    private String mKeyword;
    public NewsListPresenter(NewsListContract.View newsListView, String category, String keyword){
        mNewsListView = newsListView;
        mCatogory = category;
        mKeyword = keyword;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadMore() {
        RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory)
                .subscribe(new Observer<NewsResponse>(){
                    private Disposable disposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(NewsResponse value){
                        List<NewsEntity> newsList = value.getNewsList();

                        //                To handle the data here, for exmple
                        mNewsListView.appendNewsList(newsList);
                    }
                    @Override
                    public void onError(Throwable e) {
                        System.out.println("Error");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("加载完成");
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void refresh() {
        final RealmHelper dbHelper = RealmHelper.getInstance();
        RetrofitManager.getInstance().fetchNewsList(PAGE_SIZE, mKeyword, mCatogory)
        .subscribe(new Observer<NewsResponse>(){
            private Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(NewsResponse value){
                List<NewsEntity> newsList = value.getNewsList();
                //                To handle the data here, for exmple
                dbHelper.insertNewsList(newsList);
                mNewsListView.setNewsList(newsList);
            }
            @Override
            public void onError(Throwable e) {
                System.out.println("Error");
            }

            @Override
            public void onComplete() {
                System.out.println("刷新完成");
            }
        });
    }

    @Override
    public void keyword(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void switch2NewsDetail(NewsEntity news) {
//        Intent intent = new Intent(mNewsListView, NewsDetailActivity.class);
    }
}
