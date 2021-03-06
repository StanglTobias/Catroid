/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2018 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import org.catrobat.catroid.R;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.ui.adapter.PrototypeBrickAdapter;
import org.catrobat.catroid.ui.recyclerview.fragment.ScriptFragment;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;

public class UserBrickListFragment extends ListFragment implements View.OnClickListener {

	public static final String USER_BRICK_LIST_FRAGMENT_TAG =
			AddBrickFragment.class.getSimpleName();

	private ScriptFragment scriptFragment;

	private ImageButton addUserBrickButton;
	private PrototypeBrickAdapter adapter;

	public static UserBrickListFragment newInstance(ScriptFragment scriptFragment) {
		UserBrickListFragment fragment = new UserBrickListFragment();

		fragment.scriptFragment = scriptFragment;
		return fragment;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if (activity != null) {
			ActionBar actionBar = activity.getSupportActionBar();
			if (actionBar != null) {
				actionBar.setTitle(R.string.categories);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_brick_list, container, false);

		addUserBrickButton = view.findViewById(R.id.button_add_user_brick);
		addUserBrickButton.setOnClickListener(this);

		setupUserDefinedBrickListView();

		AppCompatActivity activity = (AppCompatActivity) getActivity();
		if (activity != null) {
			ActionBar actionBar = activity.getSupportActionBar();
			if (actionBar != null) {
				actionBar.setTitle(R.string.category_user_bricks);
			}
		}

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setOnItemClickListener((parent, view, position, id) -> { });
	}

	private void setupUserDefinedBrickListView() {
		Context context = getActivity();
		CategoryBricksFactory categoryBricksFactory = new CategoryBricksFactory();

		if (context != null) {
			List<Brick> brickList =
					categoryBricksFactory.getBricks(getString(R.string.category_user_bricks), false, context);
			adapter = new PrototypeBrickAdapter(brickList);
			setListAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		AddUserBrickFragment addUserBrickFragment =
				AddUserBrickFragment.newInstance(scriptFragment);

		UserDefinedBrick userDefinedBrick = new UserDefinedBrick();
		Bundle bundle = new Bundle();
		bundle.putSerializable(UserDefinedBrick.USER_BRICK_BUNDLE_ARGUMENT, userDefinedBrick);
		addUserBrickFragment.setArguments(bundle);

		FragmentManager fragmentManager = getFragmentManager();
		if (fragmentManager != null) {
			fragmentManager.beginTransaction()
					.add(R.id.fragment_container, addUserBrickFragment, AddUserBrickFragment.TAG)
					.addToBackStack(AddUserBrickFragment.TAG)
					.commit();
		}
	}
}
