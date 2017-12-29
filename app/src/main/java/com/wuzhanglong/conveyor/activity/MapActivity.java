package com.wuzhanglong.conveyor.activity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.BuildBean;
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.conveyor.application.AppApplication;
import com.wuzhanglong.conveyor.constant.Constant;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.http.HttpGetDataUtil;
import com.wuzhanglong.library.interfaces.PostCallback;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.BaseCommonUtils;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.utils.MapUtil;
import com.wuzhanglong.library.utils.WidthHigthUtil;

import java.util.HashMap;
import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MapActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener, AMap.OnMyLocationChangeListener
        , PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener, BGAOnRVItemClickListener, TextWatcher, EasyPermissions.PermissionCallbacks, PostCallback {
    private MapView mMapView = null;
    private AMap mAMap;
    private MyLocationStyle myLocationStyle;
    private EditText mSearchEt;
    private String mKeyword;
    private TextView mBackTv;
    private PoiSearch mPoiSearch;
    private AddressAdapter mAddressAdapter;
    private RecyclerView mRecyclerView;
    private boolean mFlag = false, mMove = false;
    private Marker mPositionMarker, mReportMarker;
    private LinearLayout mTitleLayout;
    private TextView mOkTv;
    private LatLng mLatLng;
    private String mRouteName;
    private String mStarNum;
    private String mEndNum;
    private String mIsCover = "0";
    private CustomInfoWindowAdapter mCustomInfoWindowAdapter;


    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.address_map_activity);
    }

    @Override
    public void initView() {
        mBaseHeadLayout.setVisibility(View.GONE);
        mBackTv = getViewById(R.id.back_tv);
        mOkTv = getViewById(R.id.ok_tv);
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAddressAdapter = new AddressAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAddressAdapter);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);
        mTitleLayout = getViewById(R.id.title_view);
        initMap();
        initPermissions();
    }

    @Override
    public void bindViewsListener() {
        mBackTv.setOnClickListener(this);
        mOkTv.setOnClickListener(this);
        mSearchEt.setOnEditorActionListener(this);
        mSearchEt.addTextChangedListener(this);
        mAMap.setOnMyLocationChangeListener(this);
        mAMap.setOnCameraChangeListener(this);
        mAddressAdapter.setOnRVItemClickListener(this);
    }

    @Override
    public void getData() {
        showView();
    }

    @Override
    public void hasData(BaseVO vo) {

    }

    @Override
    public void noData(BaseVO vo) {

    }

    @Override
    public void noNet() {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_tv:
                this.finish();
                break;
            case R.id.ok_tv:
                showDialog();

                break;
            default:
                break;
        }
    }

    public void showDialog() {
        DialogUIUtils.init(MapActivity.this);
        View rootView = View.inflate(MapActivity.this, R.layout.custom_dialog_layout, null);
        final EditText routeNameEt = rootView.findViewById(R.id.route_name_tv);
        final EditText startNumEt = rootView.findViewById(R.id.start_num_tv);
        final EditText endNumEt = rootView.findViewById(R.id.end_num_tv);
        TextView cancelTv = rootView.findViewById(R.id.cancle_tv);
        TextView okTv = rootView.findViewById(R.id.ok_tv);
        final BuildBean buildBean = DialogUIUtils.showCustomAlert(MapActivity.this, rootView);
        buildBean.show();
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUIUtils.dismiss(buildBean);
            }
        });
        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRouteName = routeNameEt.getText().toString();
                mStarNum = startNumEt.getText().toString();
                mEndNum = endNumEt.getText().toString();
                if (TextUtils.isEmpty(mRouteName)) {
                    showCustomToast("请填写线路名称");
                    return;
                }
                if (TextUtils.isEmpty(mStarNum)) {
                    showCustomToast("请填写起始编号");
                    return;
                }
                if (TextUtils.isEmpty(mEndNum)) {
                    showCustomToast("请填写结束编号");
                    return;
                }

                new SweetAlertDialog(MapActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定要塔标吗?")
//                            .setContentText("删除成功")
                        .setConfirmText("确定")
                        .setCancelText("取消")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                commit();
                                sDialog.dismissWithAnimation();//直接消失
                            }
                        })
                        .show();
                DialogUIUtils.dismiss(buildBean);

            }
        });

    }

    public void commit() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("ftoken", AppApplication.getInstance().getUserInfoVO().getData().getFtoken());
        map.put("userid", AppApplication.getInstance().getUserInfoVO().getData().getUserid());
        map.put("road_name", mRouteName);
        map.put("startnum", mStarNum);
        map.put("endnum", mEndNum);

        map.put("sign_lat", mLatLng.latitude + "");
        map.put("sign_lng", mLatLng.longitude + "");
        map.put("is_cover", mIsCover);
        HttpGetDataUtil.post(MapActivity.this, Constant.POSITION_COMMIT_URL, map, this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (keyEvent != null) {
            mKeyword = textView.getText().toString();
            getDataByKeyword();
        }
        return false;
    }

    @Override
    public void onMyLocationChange(Location location) {
        System.out.println("==================>");
//        LatLng marker1 = new LatLng( location.getLatitude(),location.getLongitude());
//        //设置中心点和缩放比例
//        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
//        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
//        getDataByLatlog(location.getLatitude(), location.getLongitude());
    }

    //根据关键字收索附近地理位置
    public void getDataByKeyword() {
//        GeocodeQuery query = new GeocodeQuery(mKeyword, "");
//        mGeocoderSearch.getFromLocationNameAsyn(query);
        PoiSearch.Query query = new PoiSearch.Query(mKeyword, "", "");
        mPoiSearch = new PoiSearch(this, query);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.searchPOIAsyn();
    }

    //根据经纬度搜索附近地理位置
    public void getDataByLatlog(double lat, double lon) {
//        LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
//        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 500, GeocodeSearch.AMAP);
//        mGeocoderSearch.getFromLocationAsyn(query);
        PoiSearch.Query query = new PoiSearch.Query("", "", "");
        mPoiSearch = new PoiSearch(this, query);
        mPoiSearch.setOnPoiSearchListener(this);
        mPoiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(lat, lon), 3000));//设置周边搜索的中心点以及半径
        mPoiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        List<PoiItem> list = poiResult.getPois();
        mRecyclerView.setVisibility(View.VISIBLE);
        mAddressAdapter.updateData(list);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
//        showProgressDialog();
        mMove = true;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//        dismissProgressDialog();
        LatLng latLng = cameraPosition.target;
        if (mFlag) {
//            getDataByLatlog(latLng.latitude, latLng.longitude);
            mLatLng = latLng;
        } else {
            mFlag = true;
            LatLng latLng1 = new LatLng(latLng.latitude, latLng.longitude);
            //设置中心点和缩放比例
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng1));
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    @Override
    public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        if (mAddressAdapter.getData().size() == 0)
            return;
        PoiItem vo = (PoiItem) mAddressAdapter.getData().get(i);
//        String[] params = new String[4];
//        params[0] = vo.getTitle();
//        params[1] = vo.getSnippet();
//        params[2] = vo.getLatLonPoint().getLatitude() + "";
//        params[3] = vo.getLatLonPoint().getLongitude() + "";
//        MapUtil.guide(MapActivity.this,params[2] , params[3],params[1]);

        LatLng latLng = new LatLng(vo.getLatLonPoint().getLatitude(), vo.getLatLonPoint().getLongitude());
        mReportMarker.remove();
        addPositonMarker(latLng, vo.getTitle());


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (!"".equals(s.toString())) {
            mMove = false;
            mKeyword = s.toString();
            getDataByKeyword();
        } else {
            mRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void success(BaseVO vo) {
        if ("500".equals(vo.getCode())) {
            mIsCover = "1";
            new SweetAlertDialog(MapActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("确定要覆盖塔标吗?")
//                            .setContentText("删除成功")
                    .setConfirmText("确定")
                    .setCancelText("取消")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            commit();
                            sDialog.dismissWithAnimation();//直接消失
                        }
                    })
                    .show();
        }

    }

    class AddressAdapter extends RecyclerBaseAdapter<PoiItem> {
        public AddressAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.map_address_adapter);
        }

        @Override
        public void initData(BGAViewHolderHelper helper, int position, Object model) {
            PoiItem poiItem = (PoiItem) model;
            helper.setText(R.id.name_tv, poiItem.getTitle());
            helper.setText(R.id.desc_tv, poiItem.getSnippet());
            if (position == 0 && !mMove) {
                LatLng latLng1 = new LatLng(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude());
                //设置中心点和缩放比例
                mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng1));
                mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));

            }
        }
    }


    @Override
    public void onPermissionsGranted(int i, List<String> list) {
        initMap();
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        showCustomToast("请允许权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    public void initPermissions() {
        String[] perms = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 县市定位
            initMap();
        } else {
            EasyPermissions.requestPermissions(this, "请允许打开权限权限", 1, perms);
        }

    }


    public void initMap() {

        //获取地图控件引用
        mMapView = getViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(mSavedInstanceState);

        if (mAMap == null) {
            mAMap = mMapView.getMap();
        }
//        mAMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
//        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        mAMap.getUiSettings().setZoomControlsEnabled(false);


        // 县市定位
        //显示当前定位位置
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        myLocationStyle.showMyLocation(true);
        mAMap.setMyLocationStyle(myLocationStyle);
        mCustomInfoWindowAdapter = new CustomInfoWindowAdapter(this);
        mAMap.setInfoWindowAdapter(mCustomInfoWindowAdapter);


//        LatLng latLng = new LatLng(Double.parseDouble(list.get(i).getLat()),Double.parseDouble(list.get(i).getLng()));
//        markerOption.position(latLng);
//            markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
        String type = this.getIntent().getStringExtra("type");

        if ("1".equals(type)) {
            mTitleLayout.setVisibility(View.VISIBLE);
            mBaseHeadLayout.setVisibility(View.GONE);
            if ("1".equals(AppApplication.getInstance().getUserInfoVO().getData().getIssign())) {
                mOkTv.setVisibility(View.VISIBLE);
                mPositionMarker.remove();
                addReportMarker();
            } else {
                mOkTv.setVisibility(View.INVISIBLE);
            }
        } else if ("3".equals(type)) {
            mTitleLayout.setVisibility(View.GONE);
            mBaseHeadLayout.setVisibility(View.VISIBLE);
            mBaseTitleTv.setText("塔标位置");
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.draggable(true);//设置Marker可拖动
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.mark)));
            // 将Marker设置为贴地显示，可以双指下拉地图查看效果
            markerOption.setFlat(true);//设置marker平贴地图效果
//            markerOption.title("塔标");
            final String lat = this.getIntent().getStringExtra("lat");
            final String lng = this.getIntent().getStringExtra("lng");
            final String title = this.getIntent().getStringExtra("title");

            LatLng latLng = new LatLng(BaseCommonUtils.paserDouble(lat), BaseCommonUtils.paserDouble(lng));
            markerOption.position(latLng);//maker不随地图动
            markerOption.title(title);
            Marker marker = mAMap.addMarker(markerOption);
//            mAMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this, latLng, title, 3));
            mCustomInfoWindowAdapter.setType(3);
            marker.showInfoWindow();
            //设置中心点和缩放比例
            mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));

//            mAMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
//                @Override
//                public void onInfoWindowClick(Marker marker) {
//                    MapUtil.guide(MapActivity.this, lat, lng, title);
//                }
//            });
        }

    }

    public void addPositonMarker(LatLng latLng, String title) {
        if (mPositionMarker != null)
            mPositionMarker.remove();
        mPositionMarker = mAMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(title)
//                .snippet(vo.getSnippet())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                .draggable(true));
        mCustomInfoWindowAdapter.setType(2);
        mPositionMarker.showInfoWindow();
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    public void addReportMarker() {
        if (mReportMarker != null)
            mReportMarker.remove();
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.draggable(true);//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.mark)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        markerOption.title("塔标");
        mReportMarker = mAMap.addMarker(markerOption);
        mReportMarker.setPositionByPixels(WidthHigthUtil.getScreenWidth(this) / 2, WidthHigthUtil.getScreenHigh(this) / 2);//marker 随地图移动而动
        mCustomInfoWindowAdapter.setType(1);
        mReportMarker.showInfoWindow();
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }


    class CustomInfoWindowAdapter implements AMap.InfoWindowAdapter {

        private Context context;

        private int type = 1;

        public CustomInfoWindowAdapter(Context context) {
            this.context = context;

            this.type = type;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public View getInfoWindow(final Marker marker) {

            if (type == 2) {
                View view = LayoutInflater.from(context).inflate(R.layout.map_info_window_layout, null);
                TextView addresTv = view.findViewById(R.id.address_tv);
                addresTv.setText(marker.getTitle());
                LinearLayout layout = view.findViewById(R.id.maker_layout);
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MapUtil.guide(MapActivity.this, mLatLng.latitude + "", mLatLng.longitude + "", marker.getTitle());
                    }
                });
                setViewContent(marker, view);
                return view;
            } else if (type == 1) {
                View view = LayoutInflater.from(context).inflate(R.layout.tabiao_layout, null);
                TextView titleTv = view.findViewById(R.id.title_tv);
                titleTv.setText(marker.getTitle());
                titleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if ("1".equals(AppApplication.getInstance().getUserInfoVO().getData().getIssign())) {
                            showDialog();
                        }
                    }
                });
                setViewContent(marker, view);
                return view;
            } else {
                View view = LayoutInflater.from(context).inflate(R.layout.tabiao_layout, null);
                TextView titleTv = view.findViewById(R.id.title_tv);
                titleTv.setText(marker.getTitle());
                titleTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("确定要使用导航吗?")
//                            .setContentText("删除成功")
                                .setConfirmText("确定")
                                .setCancelText("取消")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        MapUtil.guide(MapActivity.this, mLatLng.latitude + "", mLatLng.longitude + "", marker.getTitle());
                                        sDialog.dismissWithAnimation();//直接消失
                                    }
                                })
                                .show();
                    }
                });
                setViewContent(marker, view);

                return view;
            }

        }

        //这个方法根据自己的实体信息来进行相应控件的赋值
        private void setViewContent(Marker marker, View view) {
            //实例：
            System.out.println("=========");
        }

        //提供了一个给默认信息窗口定制内容的方法。如果用自定义的布局，不用管这个方法。
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

}
