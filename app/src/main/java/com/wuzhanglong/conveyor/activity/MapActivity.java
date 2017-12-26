package com.wuzhanglong.conveyor.activity;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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
import com.wuzhanglong.conveyor.R;
import com.wuzhanglong.library.ItemDecoration.DividerDecoration;
import com.wuzhanglong.library.activity.BaseActivity;
import com.wuzhanglong.library.adapter.RecyclerBaseAdapter;
import com.wuzhanglong.library.mode.BaseVO;
import com.wuzhanglong.library.utils.DividerUtil;
import com.wuzhanglong.library.utils.WidthHigthUtil;

import java.util.List;

import cn.bingoogolapple.baseadapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.baseadapter.BGAViewHolderHelper;
import pub.devrel.easypermissions.EasyPermissions;


public class MapActivity extends BaseActivity implements View.OnClickListener, TextView.OnEditorActionListener, AMap.OnMyLocationChangeListener
        , PoiSearch.OnPoiSearchListener, AMap.OnCameraChangeListener, BGAOnRVItemClickListener, TextWatcher,   EasyPermissions.PermissionCallbacks {
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
    private Marker mMarker;


    @Override
    public void baseSetContentView() {
        contentInflateView(R.layout.address_map_activity);
    }

    @Override
    public void initView() {
        mBaseHeadLayout.setVisibility(View.GONE);
        mBackTv = getViewById(R.id.back_tv);
        mSearchEt = getViewById(R.id.search_et);
        mSearchEt.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mSearchEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        mRecyclerView = getViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAddressAdapter = new AddressAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAddressAdapter);
        DividerDecoration divider = DividerUtil.linnerDivider(this, R.dimen.dp_1, R.color.C3);
        mRecyclerView.addItemDecoration(divider);

        initMap();
        initPermissions();
    }

    @Override
    public void bindViewsListener() {
        mBackTv.setOnClickListener(this);
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
            default:
                break;
        }
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
        getDataByLatlog(location.getLatitude(), location.getLongitude());
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
            getDataByLatlog(latLng.latitude, latLng.longitude);
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

        LatLng latLng1 = new LatLng(vo.getLatLonPoint().getLatitude(), vo.getLatLonPoint().getLongitude());


        if(mMarker!=null)
            mMarker.remove();

        mMarker = mAMap.addMarker(new MarkerOptions()
                .position(latLng1)
                .title(vo.getTitle())
                .snippet(vo.getSnippet())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.mark))
                .draggable(true));
//


        mAMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this,latLng1,vo.getTitle()));
        mMarker.showInfoWindow();
        //设置中心点和缩放比例
        mAMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng1));
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(15));
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
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
        String[] perms = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
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

        MarkerOptions markerOption = new MarkerOptions();
//        LatLng latLng = new LatLng(Double.parseDouble(list.get(i).getLat()),Double.parseDouble(list.get(i).getLng()));
//        markerOption.position(latLng);
//            markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");

        markerOption.draggable(true);//设置Marker可拖动
//        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                .decodeResource(getResources(), R.drawable.mark)));
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOption.setFlat(true);//设置marker平贴地图效果
        Marker marker = mAMap.addMarker(markerOption);
        marker.setPositionByPixels(WidthHigthUtil.getScreenWidth(this) / 2, WidthHigthUtil.getScreenHigh(this) / 2);
//        mAMap.moveCamera(CameraUpdateFactory.zoomTo(55));
    }


    class CustomInfoWindowAdapter implements AMap.InfoWindowAdapter{

        private Context context;
        private LatLng mLatLng;
        private String mAddress;

        public CustomInfoWindowAdapter(Context context,LatLng latLng,String address) {
            this.context = context;
            this.mLatLng=latLng;
            mAddress=address;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            View view = LayoutInflater.from(context).inflate(R.layout.map_info_window_layout, null);
            TextView addresTv=view.findViewById(R.id.address_tv);
            addresTv.setText(mAddress);
            LinearLayout layout=view.findViewById(R.id.maker_layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            setViewContent(marker,view);
            return view;
        }
        //这个方法根据自己的实体信息来进行相应控件的赋值
        private void setViewContent(Marker marker,View view) {
            //实例：
        }

        //提供了一个给默认信息窗口定制内容的方法。如果用自定义的布局，不用管这个方法。
        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }

}
