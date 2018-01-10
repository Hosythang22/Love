package hs.thang.com.love.settingscreen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import hs.thang.com.love.settingscreen.view.FontPickerDialog;
import hs.thang.com.love.settingscreen.viewmodel.FontPicker;
import hs.thang.com.love.util.TextViewC;
import hs.thang.com.love.R;

/**
 * Created by sev_user on 1/10/2018.
 */

public class FontPickerAdapter extends RecyclerView.Adapter<FontPickerAdapter.FontPickerViewHolder> {

    private ArrayList<Integer> mFontIds;
    private WeakReference<FontPickerDialog> mDialog;
    private Context mContext;
    private ArrayList<String> mFontNames;
    private FontPicker.OnChooseFontListener mOnChooseFontListener;

    public FontPickerAdapter(Context context, ArrayList<Integer> fontIds, WeakReference<FontPickerDialog> dialog,
                             FontPicker.OnChooseFontListener listener) {
        mFontIds = fontIds;
        mDialog = dialog;
        mContext = context;
        mOnChooseFontListener = listener;
        initFontNames();
    }

    private void initFontNames() {
        mFontNames = new ArrayList<>();
        mFontNames.add("AmaticSC Bold");
        mFontNames.add("Aristonne");
        mFontNames.add("BaseFiolexGirl");
        mFontNames.add("BaseFutara");
        mFontNames.add("BeautifulEveryTime");
        mFontNames.add("Comfortaa-Regular");
        mFontNames.add("DancingScript-Regular");
        mFontNames.add("ComicSans");
        mFontNames.add("HeraBig");
        mFontNames.add("Kaileen_Bold");
        mFontNames.add("Kingthings_Petrock");
        mFontNames.add("Lobster-Regular");
        mFontNames.add("Pacifico-Regular");
        mFontNames.add("PatrickHand-Regular");
        mFontNames.add("Play-Regular");
        mFontNames.add("RixLoveFool");
        mFontNames.add("Roboto");
        mFontNames.add("SofiaBold");
        mFontNames.add("Valentine");
        mFontNames.add("VNI-Vari");
        mFontNames.add("ZemkeHand");
        mFontNames.add("AmaticSC-Bold");
    }


    @Override
    public FontPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.font_item, parent, false);
        return new FontPickerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FontPickerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mFontIds != null) {
            return mFontIds.size();
        }
        return 0;
    }

    private void dismissDialog() {
        if (mDialog == null) {
            return;
        }
        FontPickerDialog dialog = mDialog.get();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    class FontPickerViewHolder extends RecyclerView.ViewHolder {

        private TextViewC mTextView;

        private View mBackground;

        FontPickerViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextViewC) itemView.findViewById(R.id.font_id);
            mBackground = itemView.findViewById(R.id.backgound);
        }

        public void bind(int position) {
            int font = mFontIds.get(position);
            mTextView.setFont(font);
            mTextView.setText(mFontNames.get(position));
            mBackground.setOnClickListener(v -> {
                if (mOnChooseFontListener != null && mDialog != null) {
                    mOnChooseFontListener.onChooseFont(position + 1, position + 1);
                    dismissDialog();
                }
            });
        }
    }
}
