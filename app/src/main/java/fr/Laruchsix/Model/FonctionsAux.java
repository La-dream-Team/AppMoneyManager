package fr.Laruchsix.Model;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import fr.Laruchsix.Model.Devise;
import fr.Laruchsix.Model.Person;

import static android.content.Context.MODE_PRIVATE;

public class FonctionsAux {
    @SuppressLint("ClickableViewAccessibility")
    public static void smartScroll(final ScrollView scroll, final ListView list){
        scroll.requestDisallowInterceptTouchEvent(true);
        list.setOnTouchListener(new View.OnTouchListener() {
            private boolean isListTop = false, isListBottom = false;
            private float delta = 0, oldY = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        delta = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        delta = event.getY() - oldY;
                        oldY = event.getY();

                        isListTop = false;
                        isListBottom = false;

                        View first = list.getChildAt(0);
                        View last = list.getChildAt(list.getChildCount()-1);
                        if(first != null && list.getFirstVisiblePosition() == 0 && first.getTop() == 0 && delta > 0.0f){
                            isListTop = true;
                        }
                        if(last != null && list.getLastVisiblePosition() == list.getCount()-1 && last.getBottom() <= list.getHeight() && delta < 0.0f){
                            isListBottom = true;
                        }

                        if( (isListTop && delta > 0.0f) || (isListBottom && delta < 0.0f) ){
                            scroll.post(new Runnable() {
                                public void run() {
                                    scroll.smoothScrollBy(0, -(int)delta);
                                }
                            });
                        }

                        break;
                    default: break;
                }
                scroll.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }
}
