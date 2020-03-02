package com.example.xiaomi;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.arch.core.executor.DefaultTaskExecutor;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.xiaomi.model.Dbservice;
import com.example.xiaomi.model.Note;
import com.example.xiaomi.model.NoteBook;
import com.example.xiaomi.model.UserInfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    TextView userName ;
    TextView editY ;
    TextView editN ;
    EditText  editInfo ;
    TextView userSaying ;
    CircleImageView circleImageView;
    MutableLiveData<List<UserInfo>> mutableLiveData;
    LinearLayout boy;
    LinearLayout girl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Dbservice dbservice = Dbservice.getInstance();

        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            mutableLiveData.postValue(DataSupport.findAll(UserInfo.class));
        }


        //实现toolbar替换默认的Actionbar
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.container);
        NavigationView navigationView = findViewById(R.id.navDrawer
        );

        navigationView.setItemIconTintList(null);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                navController.getGraph())
                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);

        //设置修改个人信息
        View header = navigationView.getHeaderView(0);
        userName = header.findViewById(R.id.userName);
        userName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(MainActivity.this, R.layout.edit_info, null);
                dialog.setView(dialogView);
                dialog.show();
                editInfo =dialogView.findViewById(R.id.infoEdit);
                editN=dialogView.findViewById(R.id.infoN);
                editY = dialogView.findViewById(R.id.infoY);
                editN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfo userInfo= new UserInfo();
                        userInfo.setName(editInfo.getText().toString());
                        userInfo.update(1);
                        mutableLiveData.postValue(DataSupport.findAll(UserInfo.class));
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });
        userSaying = header.findViewById(R.id.userSay);
        userSaying.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(MainActivity.this, R.layout.edit_say, null);
                dialog.setView(dialogView);
                dialog.show();
                editInfo =dialogView.findViewById(R.id.infoEdit);
                editN=dialogView.findViewById(R.id.infoN);
                editY = dialogView.findViewById(R.id.infoY);
                editN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfo userInfo= new UserInfo();
                        userInfo.setSaying(editInfo.getText().toString());
                        userInfo.update(1);
                        mutableLiveData.postValue(DataSupport.findAll(UserInfo.class));
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });
        //修改头像0boy 1girl
        circleImageView = header.findViewById(R.id.icon_image);
        circleImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(MainActivity.this, R.layout.profilechoose, null);
                dialog.setView(dialogView);
                dialog.show();
                boy = dialogView.findViewById(R.id.boy);
                girl = dialogView.findViewById(R.id.girl);
                boy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfo userInfo = new UserInfo();
                        userInfo.setProfile("0");
                        userInfo.update(1);
                        mutableLiveData.postValue(DataSupport.findAll(UserInfo.class));
                        dialog.dismiss();
                    }
                });
                girl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        UserInfo userInfo = new UserInfo();
                        userInfo.setProfile("1");
                        userInfo.update(1);
                        mutableLiveData.postValue(DataSupport.findAll(UserInfo.class));
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });




        mutableLiveData.observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                if(userInfos.size()!=0){
                    circleImageView.setImageResource(getImage(userInfos.get(0).getProfile()));
                    userName.setText(userInfos.get(0).getName());
                    userSaying.setText(userInfos.get(0).getSaying());
                }
            }
        });

    }

    private int getImage(String profile) {
        switch(profile){
            case "0":return R.drawable.boy;
            case "1":return R.drawable.girl;
            default:return R.drawable.girl;
        }
    }

    //设置actionbar中的菜单显示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolhead, menu);

        return true;
    }

    //处理左上角的菜单被点击的时候的回调
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //若是通过toolbar创建的菜单可以通过重写该方法获得导航
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logRec:
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
