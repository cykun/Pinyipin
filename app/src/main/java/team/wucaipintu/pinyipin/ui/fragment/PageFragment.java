package team.wucaipintu.pinyipin.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.PostItem;
import team.wucaipintu.pinyipin.ui.Listener.RecyclerItemClickListener;
import team.wucaipintu.pinyipin.ui.activity.PostDetailActivity;
import team.wucaipintu.pinyipin.ui.adapter.PostAdapter;

public class PageFragment extends Fragment {
    private List<PostItem> postItemList;
    private RecyclerView recyclerView;
    private TwinklingRefreshLayout refreshLayout;
    private PostAdapter postAdapter;
    public String type;
    private int userId;
    private String nikeName;
    private boolean isFirstLoad = true;

    public static PageFragment getInstance(String type,int userId,String nikeName) {
        PageFragment pageFragment = new PageFragment();
        pageFragment.type = type;
        pageFragment.userId=userId;
        pageFragment.nikeName=nikeName;
        return pageFragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        refreshLayout = view.findViewById(R.id.refreshLayout);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new SpacesItemDecoration(16));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(getActivity(),"hhahaha"+postItemList.get(position).getPostId(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                intent.putExtra("postId",postItemList.get(position).getPostId());
                intent.putExtra("userId",userId);
                intent.putExtra("nikeName",nikeName);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        if (isFirstLoad) {
            postItemList = new ArrayList<>();
            if (type.equals("推荐")) {
                loadDataFromDataBase();
            }
            isFirstLoad = false;
        }
        initListener();
        postAdapter = new PostAdapter(postItemList);
        recyclerView.setAdapter(postAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //设置监听
    public void initListener() {
        refreshLayout.setTargetView(recyclerView);
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                //super.onRefresh(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                //super.onLoadMore(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                postAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
            }
        });
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://39.108.37.77:8080/pinyipin/post/item");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("accept", "*/*");
                    httpURLConnection.setRequestProperty("user-agent",
                            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                    String param;
                    if(type.equals("推荐")){
                        param="datatime=2017/11/25 12:15";
                    }else {
                        param="type="+type+"&datatime=2017/11/25 12:15";
                    }
                    httpURLConnection.connect();
                    PrintWriter writer=new PrintWriter(httpURLConnection.getOutputStream());
                    writer.print(param);
                    writer.flush();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String line;
                    String data="";
                    while ((line=reader.readLine())!=null){
                        data+=line;
                    }
                    if (data != null) {
                        Gson gson = new Gson();
                        Type postListType = new TypeToken<ArrayList<PostItem>>() {
                        }.getType();
                        ArrayList<PostItem> items = gson.fromJson(data, postListType);
                        //recyclerView.postInvalidate();
                        for (PostItem item : items) {
                            postItemList.add(0, item);
                            //postAdapter.notifyItemInserted(0);
                        }
                        //postAdapter.notifyItemRangeChanged(0, postItemList.size());
                        //
                    }
                }catch (IOException e){
                    System.out.print(e.getMessage());
                }
            }
        }).start();
    }
    //启动时初始化数据操作
    public void loadDataFromDataBase() {

    }

    //两个item之间添加空格
    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0)
                outRect.top = space;
        }
    }
}
