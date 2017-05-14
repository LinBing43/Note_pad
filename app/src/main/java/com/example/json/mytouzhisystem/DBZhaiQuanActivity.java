package com.example.json.mytouzhisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.json.mytouzhisystem.Bean.DBUserInvestment;
import com.example.json.mytouzhisystem.Bean.DataSaveEvent;
import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;
import com.example.json.mytouzhisystem.Utils.RxBus;
import com.example.json.mytouzhisystem.constants.ConstKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;
//笔记分类4 备忘录
public class DBZhaiQuanActivity extends BaseActivity {

    @BindView(R.id.btnDBZhaiQuanActivityAdd)
    Button btnDBZhaiQuanActivityAdd;
    @BindView(R.id.lvDBZhaiQuanActivity)
    ListView lvDBZhaiQuanActivity;
    private Subscription rxSubscription;
    private List<DBUserInvestment> resultDaoList = new ArrayList<>();
    private MyKindAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbzhai_quan);
        ButterKnife.bind(this);
        refreshMediaUpdateEvent();
        getListViewData();
        setListViewClick();
    }

    public void getListViewData(){

        resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("备忘录");
        if (resultDaoList!=null&&resultDaoList.size()>0){
            lvDBZhaiQuanActivity.setVisibility(View.VISIBLE);
            myAdapter = new MyKindAdapter(DBZhaiQuanActivity.this);
            myAdapter.setLists(resultDaoList);
            lvDBZhaiQuanActivity.setAdapter(myAdapter);
        }else {
            lvDBZhaiQuanActivity.setVisibility(View.GONE);
        }
    }


    //设置点击事件
    @OnClick({R.id.btnDBZhaiQuanActivityAdd})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击添加点击事件
            case R.id.btnDBZhaiQuanActivityAdd:
                Intent intent = new Intent(DBZhaiQuanActivity.this, AddActivity.class);
                intent.putExtra("name", "备忘录");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void refreshMediaUpdateEvent() {
        rxSubscription = RxBus.getDefault()
                .toObservable(DataSaveEvent.class)
                .subscribe(new Action1<DataSaveEvent>() {
                    @Override
                    public void call(DataSaveEvent dataSaveEvent) {
                        if (ConstKey.SAVE_DATA_SUCCESS.equals(dataSaveEvent.getDataSaveEvent())) {
                            resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("备忘录");
                            if (myAdapter ==null){
                                myAdapter = new MyKindAdapter(DBZhaiQuanActivity.this);
                            }
                            if (resultDaoList!=null&&resultDaoList.size()>0){
                                lvDBZhaiQuanActivity.setVisibility(View.VISIBLE);
                                myAdapter.setLists(resultDaoList);
                                lvDBZhaiQuanActivity.setAdapter(myAdapter);
                            }else {
                                lvDBZhaiQuanActivity.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRxBus();
    }

    private void unregisterRxBus() {
        if (rxSubscription != null && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }

    public void setListViewClick(){
        lvDBZhaiQuanActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DBZhaiQuanActivity.this,NotXiangQingActivity.class);
                intent.putExtra("NotID",  resultDaoList.get(position).getCreatTimeAsId());
                intent.putExtra("name", "备忘录");
                startActivity(intent);
            }
        });
    }
}
