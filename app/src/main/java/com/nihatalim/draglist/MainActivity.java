package com.nihatalim.draglist;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.nihatalim.draglist.model.User;
import com.nihatalim.draglist.view.UserHolder;
import com.nihatalim.dragmanagement.business.DragManager;
import com.nihatalim.dragmanagement.helpers.ViewData;
import com.nihatalim.dragmanagement.interfaces.OnDrag;
import com.nihatalim.genericrecycle.business.GenericRecycleAdapter;
import com.nihatalim.genericrecycle.interfaces.OnBind;
import com.nihatalim.genericrecycle.interfaces.OnCreate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView1, recyclerView2;
    private GenericRecycleAdapter<UserHolder, User> recycleAdapter1, recycleAdapter2;
    private List<User> userList1, userList2;

    private DragManager dragManager1 = null;
    private DragManager dragManager2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inıt visual elements
        this.recyclerView1 = findViewById(R.id.recycler1);
        this.recyclerView2 = findViewById(R.id.recycler2);

        // UserLists filled.
        this.userList1 = fillList1();
        this.userList2 = fillList2();

        // Create new Generic Recycle Adapter with userlist, context and layout file for items
        this.recycleAdapter1 = new GenericRecycleAdapter<>(this.userList1, getContext(), R.layout.user_item);
        this.recycleAdapter2 = new GenericRecycleAdapter<>(this.userList2, getContext(), R.layout.user_item);

        // DragManager Initialize OnDrag
        this.dragManager1 = DragManager.init(this.recyclerView2).OnDrag(new OnDrag<ViewData>() {
            @Override
            public void Drop(View view, DragEvent dragEvent, ViewData data) {
                int position = (int) data.getData();
                recycleAdapter2.getObjectList().add(recycleAdapter1.getObjectList().get(position));
                recycleAdapter1.getObjectList().remove(position);
                recycleAdapter1.notifyItemRemoved(position);
                recycleAdapter2.notifyItemInserted(recycleAdapter2.getItemCount());
            }

            @Override
            public void DragEntered(View view, DragEvent dragEvent, ViewData data) {
                recyclerView1.setBackgroundColor(Color.CYAN);
            }

            @Override
            public void DragExited(View view, DragEvent dragEvent, ViewData data) {
                recyclerView1.setBackgroundColor(Color.BLUE);
            }
        });
        this.dragManager2 = DragManager.init(this.recyclerView1).OnDrag(new OnDrag<ViewData>() {
            @Override
            public void Drop(View view, DragEvent dragEvent, ViewData data) {
                int position = (int) data.getData();
                recycleAdapter1.getObjectList().add(recycleAdapter2.getObjectList().get(position));
                recycleAdapter2.getObjectList().remove(position);
                recycleAdapter2.notifyItemRemoved(position);
                recycleAdapter1.notifyItemInserted(recycleAdapter1.getItemCount());
            }

            @Override
            public void DragEntered(View view, DragEvent dragEvent, ViewData data) {
                recyclerView1.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void DragExited(View view, DragEvent dragEvent, ViewData data) {
                recyclerView1.setBackgroundColor(Color.YELLOW);
            }
        });

        // OnCreate Interface field for recycleAdapter1
        this.recycleAdapter1.setOnCreateInterface(new OnCreate<UserHolder>() {
            @Override
            public UserHolder onCreate(ViewGroup viewGroup, int i, View view) {
                final UserHolder holder = new UserHolder(view);

                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return dragManager1.drag(view,holder.getPosition());
                    }
                });

                return holder;
            }
        });

        // OnBind Interface field for recycleAdapter1
        this.recycleAdapter1.setOnBindInterface(new OnBind<UserHolder>() {
            @Override
            public void OnBind(UserHolder userHolder, int i) {
                userHolder.textName.setText(recycleAdapter1.getObjectList().get(i).getName());
            }
        });


        // OnCreate Interface field for recycleAdapter2
        this.recycleAdapter2.setOnCreateInterface(new OnCreate<UserHolder>() {
            @Override
            public UserHolder onCreate(ViewGroup viewGroup, int i, View view) {
                final UserHolder holder = new UserHolder(view);
                view.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return dragManager2.drag(view,holder.getPosition());
                    }
                });

                return holder;
            }
        });

        // OnBind Interface field for recycleAdapter2
        this.recycleAdapter2.setOnBindInterface(new OnBind<UserHolder>() {
            @Override
            public void OnBind(UserHolder userHolder, int i) {
                userHolder.textName.setText(recycleAdapter2.getObjectList().get(i).getName());
            }
        });

        // Generic Adapter Build recycler views
        this.recycleAdapter1.build(this.recyclerView1);
        this.recycleAdapter2.build(this.recyclerView2);

        // Build DragManager
        this.dragManager1.build();
        this.dragManager2.build();
    }

    private List<User> fillList1() {
        List<User> users = new ArrayList<>();
        users.add(new User(1, "Nihat"));
        users.add(new User(2, "Murat"));
        users.add(new User(3, "Kamil"));
        users.add(new User(4, "Tarkan"));
        users.add(new User(5, "Cemil"));
        return users;
    }

    private List<User> fillList2() {
        List<User> users = new ArrayList<>();
        users.add(new User(6, "Firuze"));
        users.add(new User(7, "Leman"));
        users.add(new User(8, "Hakkı"));
        users.add(new User(9, "Serkan"));
        users.add(new User(10, "Lale"));
        return users;
    }

    private Context getContext() {
        return this;
    }
}
