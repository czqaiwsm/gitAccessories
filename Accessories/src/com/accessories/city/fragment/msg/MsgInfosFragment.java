package com.accessories.city.fragment.msg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.accessories.city.R;
import com.accessories.city.adapter.ClassTypeAdpter;
import com.accessories.city.bean.CateListBean;
import com.accessories.city.bean.CateSubTypeEntity;
import com.accessories.city.fragment.BaseFragment;
import com.accessories.city.help.RequsetListener;
import com.accessories.city.parse.CateListParse;
import com.accessories.city.utils.URLConstants;
import com.accessories.city.view.CustomListView;
import com.accessories.city.view.GridViewForScrollView;
import com.volley.req.net.HttpURL;
import com.volley.req.net.RequestManager;
import com.volley.req.net.RequestParam;
import com.volley.req.parser.JsonParserBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MsgInfosFragment extends BaseFragment implements RequsetListener {

    private CustomListView callListView;//
    private GridViewForScrollView viewForScrollView = null;
    private View converView;
    private ImageView banner;
    private List<CateSubTypeEntity> list = null;
    private ClassTypeAdpter adapter;

    private boolean prepare = false;
    private boolean isVisible = false;

    private int type = 0;//1 汽车配件 2 汽车用品 3 商用车配件 4

    public MsgInfosFragment(int type){
        this.type = type;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // startReqTask(this);
        // mLoadHandler.sendEmptyMessageDelayed(Constant.NET_SUCCESS, 100);// 停止加载框
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_msg_infos, container, false);
        converView = inflater.inflate(R.layout.center_layout, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        callListView = (CustomListView)view.findViewById(R.id.callListView);
        initView(converView);
        if (type == 2){
            setTitleText("汽车用品");
        }else if(type == 3){
            setTitleText("商用车配件");
        }
        prepare = true;

    }

    private void onLoade(){
        if(prepare && isVisible()){
            requestTask(1);
            prepare = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isVisible = !hidden;
        if(hidden){
            dismissLoadingDilog();
        }
        onLoade();
    }

    private void initView(View view) {
        viewForScrollView = (GridViewForScrollView) view.findViewById(R.id.callGridView);
        banner = (ImageView)view.findViewById(R.id.banner);
        ArrayAdapter mArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, new Object[]{});
//        callListView.setRefreshBackGround(getResources().getColor(R.color.bg));
        callListView.setAdapter(mArrayAdapter);
        callListView.addHeaderView(converView);
        callListView.setCanLoadMore(false);

        callListView.setOnRefreshListener(new CustomListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData(1);
            }
        });
        setSingle();
    }

    private void setSingle(){
        list = new ArrayList<CateSubTypeEntity>();
        adapter = new ClassTypeAdpter(getActivity(),list);
        viewForScrollView.setAdapter(adapter);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void requestData(int requestCode) {
        HttpURL url = new HttpURL();
        Map postParams = new HashMap<String,String>();
        url.setmBaseUrl(URLConstants.CATEGORYLIST);
        postParams.put("type", ""+type);
        RequestParam param = new RequestParam();
        param.setmParserClassName(CateListParse.class.getName());
        param.setmPostMap(postParams);
        param.setmHttpURL(url);
        param.setPostRequestMethod();
        RequestManager.getRequestData(getActivity(), createReqSuccessListener(requestCode), createMyReqErrorListener(), param);
    }
    @Override
    public void handleRspSuccess(int reqCode,Object obj) {
        switch (reqCode){
            case 1:
                callListView.onRefreshComplete();
                JsonParserBase<CateListBean> jsonParserBase = (JsonParserBase<CateListBean>) obj;
                ArrayList<CateSubTypeEntity> chooseTeachBean = jsonParserBase.getObj().getArray();
                    refresh(chooseTeachBean);
                break;
        }
    }

    @Override
    protected void failRespone() {
        super.failRespone();
        callListView.onRefreshComplete();
    }

    @Override
    protected void errorRespone() {
        super.errorRespone();
        failRespone();
    }
    /**
     * 页数为1时使用
     *
     * @param teacherInfos
     */
    private void refresh(ArrayList<CateSubTypeEntity> teacherInfos) {
        list.clear();
        if(teacherInfos != null){
            list.addAll(teacherInfos);
        }
        adapter.notifyDataSetChanged();
    }

    public enum CateType{
        

    }

}
