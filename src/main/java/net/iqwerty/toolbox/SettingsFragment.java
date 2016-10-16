package net.iqwerty.toolbox;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

	private OnFragmentInteractionListener mListener;

	private final String[] COLOR_KEYS = {
			Config.SETTING_OVERLAY_COLOR_ALPHA,
			Config.SETTING_OVERLAY_COLOR_RED,
			Config.SETTING_OVERLAY_COLOR_GREEN,
			Config.SETTING_OVERLAY_COLOR_BLUE
	};

	private final int[] RES_IDS = {
			R.id.setting_overlay_color_alpha,
			R.id.setting_overlay_color_red,
			R.id.setting_overlay_color_green,
			R.id.setting_overlay_color_blue
	};

	private final int MAX_ALPHA = 200;

	public SettingsFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment SettingsFragment.
	 */
	public static SettingsFragment newInstance() {
		SettingsFragment fragment = new SettingsFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		restoreSettings();

		setupSaveButton();
	}

	private void restoreSettings() {
		restoreOverlayColor();
	}

	private void restoreOverlayColor() {
		ArrayList<TextInputEditText> edit = getFilterEditTexts();
		for (int i = 0; i < edit.size(); i++) {
			edit.get(i).setText(restoreOverlayColorFor(COLOR_KEYS[i]));
		}
	}

	private String restoreOverlayColorFor(final String color) {
		return Integer.toString(Util.restoreOverlayColorFor(color, getContext()));
	}

	private ArrayList<TextInputEditText> getFilterEditTexts() {
		ArrayList<TextInputEditText> list = new ArrayList<>();

		for (int i = 0; i < RES_IDS.length; i++) {
			list.add(getEditTextById(RES_IDS[i]));
		}
		return list;
	}

	private TextInputEditText getEditTextById(final int id) {
		return (TextInputEditText) getView().findViewById(id);
	}

	private void setupSaveButton() {
		Button save = (Button) getView().findViewById(R.id.setting_save);
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Logger.log("Saving settings");
				saveSettings();
			}
		});
	}

	private void saveSettings() {
		saveFilterColor();
	}

	private void saveFilterColor() {
		ArrayList<TextInputEditText> edit = getFilterEditTexts();
		for (int i = 0; i < edit.size(); i++) {
			int value = Integer.valueOf(edit.get(i).getText().toString());

			if (COLOR_KEYS[i].equals(Config.SETTING_OVERLAY_COLOR_ALPHA)) {
				if (value >= MAX_ALPHA) {
					Toast.makeText(getContext(), getText(R.string.settings_alpha_max), Toast.LENGTH_SHORT).show();
					edit.get(i).setText(String.valueOf(MAX_ALPHA));
					value = MAX_ALPHA;
				}
			}
			Preferences.getInstance().saveInt(getContext(), COLOR_KEYS[i], value);
		}
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof OnFragmentInteractionListener) {
			mListener = (OnFragmentInteractionListener) context;
		} else {
			throw new RuntimeException(context.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		void onFragmentInteraction(Uri uri);
	}
}
