/*
* Copyright 2009-2011 Brian Pellin.
*
* This file is part of KeePassDroid.
*
* KeePassDroid is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* KeePassDroid is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with KeePassDroid. If not, see <http://www.gnu.org/licenses/>.
*
*/
package com.keepassdroid.tests.search;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.platform.app.InstrumentationRegistry;

import com.android.keepass.R;
import com.keepassdroid.Database;
import com.keepassdroid.database.PwGroup;
import com.keepassdroid.tests.database.TestData;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SearchTest {
	
	private Database mDb;

	@Before
	public void setUp() throws Exception {
		Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();

		mDb = TestData.GetDb1(ctx, true);
	}

	@Test
	public void testSearch() {
		PwGroup results = mDb.Search("Amazon");
		assertTrue("Search result not found.", results.childEntries.size() > 0);
		
	}

	@Test
	public void testBackupIncluded() {
		updateOmitSetting(false);
		PwGroup results = mDb.Search("BackupOnly");
		
		assertTrue("Search result not found.", results.childEntries.size() > 0);
	}

	@Test
	public void testBackupExcluded() {
		updateOmitSetting(true);
		PwGroup results = mDb.Search("BackupOnly");
		
		assertFalse("Search result found, but should not have been.", results.childEntries.size() > 0);
	}
	
	private void updateOmitSetting(boolean setting) {
		Context ctx = InstrumentationRegistry.getInstrumentation().getTargetContext();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		SharedPreferences.Editor editor = prefs.edit();
		
		editor.putBoolean(ctx.getString(R.string.omitbackup_key), setting);
		editor.commit();
		
	}
}
