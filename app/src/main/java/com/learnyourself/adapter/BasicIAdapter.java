package com.learnyourself.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.learnyourself.customview.TextProgressBar;
import com.learnyourself.db.DBHelper;
import com.learnyourself.grewords.App;
import com.learnyourself.grewords.R;
import com.learnyourself.object.HandleFragmentI;
import com.learnyourself.object.HandleFragmentII;
import com.learnyourself.object.HandleFragmentIII;
import com.learnyourself.util.PrefHelper;

/**
 * Created by Nha on 11/29/2015.
 */
public class BasicIAdapter extends RecyclerView.Adapter<BasicIAdapter.ViewHolder>{

    private static final int BASIC_I = 1;
    private static final int BASIC_II = 2;
    private static final int ADVANCE = 3;
    Context mContext;
    DBHelper dbHelper;

    HandleFragmentI handleFragmentI;
    HandleFragmentII handleFragmentII;
    HandleFragmentIII handleFragmentIII;
    int current_fragment;
    String section;
    int current_part;
    RecyclerView recyclerView;
    // 2

    public BasicIAdapter(Context context, int current_fragment, RecyclerView recyclerView) {
        this.mContext = context;
        dbHelper = App.getInstance().getDbHelper();
        this.current_fragment = current_fragment;
        section = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_SECTION, "null");
        if(section.equals("null")){
            section = "basic1";
            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_SECTION,section);
        }
        current_part = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_PART,0);
        if(current_part==0){
            current_part =1;
            App.getInstance().getPrefsHelper().savePref(PrefHelper.CURRENT_PART,current_part);
        }

        this.recyclerView = recyclerView;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basic1_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       String section = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_SECTION, "null");
        int current_part = App.getInstance().getPrefsHelper().getPref(PrefHelper.CURRENT_PART,0);
        holder.partNumber.setText("Part " + (position + 1));
        switch (current_fragment){
            case BASIC_I:
                if(handleFragmentI==null){
                    handleFragmentI = new HandleFragmentI(mContext,dbHelper);
                }
                handleFragmentI.handle(holder,position,section,current_part);
                break;
            case BASIC_II:
                if(handleFragmentII==null){
                    handleFragmentII = new HandleFragmentII(mContext,dbHelper);
                }
                handleFragmentII.handle(holder,position, section, current_part);
                break;
            case ADVANCE:
                if(handleFragmentIII==null){
                    handleFragmentIII = new HandleFragmentIII(mContext,dbHelper);
                }
                handleFragmentIII.handle(holder,position,section,current_part);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {

        switch (current_fragment){
            case BASIC_I:
                return 10;
            case BASIC_II:
                return 10;
            case ADVANCE:
                return 8;
            default:
                return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView partNumber;
        public ImageView image_lock;
        public ImageView reset_part;
        public TextProgressBar progressBar;
        public RelativeLayout card_layout;
        public TextSwitcher textSwitcher;
        public ImageView image_next_word;
        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = (TextProgressBar)itemView.findViewById(R.id.progressBar);
            partNumber = (TextView) itemView.findViewById(R.id.partNumber);
            image_lock = (ImageView)itemView.findViewById(R.id.image_lock);
            reset_part = (ImageView)itemView.findViewById(R.id.reset_part);
            card_layout = (RelativeLayout)itemView.findViewById(R.id.card_layout);
            textSwitcher = (TextSwitcher)itemView.findViewById(R.id.text_switcher_word_show);
            textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {

                public View makeView() {
                    // TODO Auto-generated method stub
                    // create new textView and set the properties like clolr, size etc
                    TextView myText = new TextView(mContext);
                    myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                    myText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                            mContext.getResources().getDimension(R.dimen.word_size));
                    myText.setTextColor(Color.parseColor("#FFFFFF"));
                    myText.setTypeface(myText.getTypeface(), Typeface.BOLD);
                    return myText;
                }
            });

            image_next_word = (ImageView)itemView.findViewById(R.id.image_next_word);
        }
    }
}
