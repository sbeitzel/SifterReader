package com.BreakingBytes.SifterReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class IssueDetail extends Activity {
	
	public static final String CATEGORY_NAME = "category_name";
	public static final String DESCRIPTION = "description";
	public static final String MILESTONE_NAME = "milestone_name";
	public static final String OPENER_NAME = "opener_name";
	public static final String ASSIGNEE_NAME = "assignee_name";
	public static final String COMMENT_COUNT = "comment_count";
	public static final String CREATED_AT = "created_at";
	public static final String UPDATED_AT = "updated_at";
	public static final String ISSUE_COMMENTS_URL = "url";
	private SifterHelper mSifterHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issue_detail);

		mSifterHelper = new SifterHelper(this);
		
		TextView issueNumber = (TextView) findViewById(R.id.issue_number);
		TextView categoryName = (TextView) findViewById(R.id.issue_category_name);
		TextView priority = (TextView) findViewById(R.id.issue_priority);
		TextView subject = (TextView) findViewById(R.id.issue_subject);
		TextView description = (TextView) findViewById(R.id.issue_description);
		TextView milestoneName = (TextView) findViewById(R.id.issue_milestone_name);
		TextView openerName = (TextView) findViewById(R.id.issue_opener_name);
		TextView assigneeName = (TextView) findViewById(R.id.issue_assignee_name);
		TextView status = (TextView) findViewById(R.id.issue_status);
		TextView commentCount = (TextView) findViewById(R.id.issue_comment_count);
		TextView createdAt = (TextView) findViewById(R.id.issue_created_at);
		TextView updatedAt = (TextView) findViewById(R.id.issue_updated_at);
		TextView issueCommentsURL = (TextView) findViewById(R.id.issue_comments_url);

		Bundle extras = getIntent().getExtras();
		if (extras == null)
			return;
		try {
			JSONObject issue = new JSONObject(extras.getString(IssuesActivity.ISSUE));
			if (checkFields(issue)) {
				issueNumber.setText(issue.getString(IssuesActivity.NUMBER));
				categoryName.setText(issue.getString(CATEGORY_NAME));
				priority.setText(issue.getString(IssuesActivity.PRIORITY));
				subject.setText(issue.getString(IssuesActivity.SUBJECT));
				description.setText(issue.getString(DESCRIPTION));
				milestoneName.setText(issue.getString(MILESTONE_NAME));
				openerName.setText(issue.getString(OPENER_NAME));
				assigneeName.setText(issue.getString(ASSIGNEE_NAME));
				status.setText(issue.getString(IssuesActivity.STATUS));
				commentCount.setText(issue.getString(COMMENT_COUNT));
				createdAt.setText(issue.getString(CREATED_AT));
				updatedAt.setText(issue.getString(UPDATED_AT));
				issueCommentsURL.setText(issue.getString(ISSUE_COMMENTS_URL));
				Linkify.addLinks(issueCommentsURL, Linkify.WEB_URLS);
			}
		} catch (JSONException e) {
			e.printStackTrace();
			mSifterHelper.onException(e.toString()); // return not needed
		}
	}

    /**
     * The point of this method seems to be to make sure that the given JSONObject
     * has the fields we require in order to draw an issue. It should return false
     * if a required field is absent, but it should not return false just because a
     * non-required field is present.
     *
     * @param category the JSONObject to be inspected
     * @return <tt>true</tt> if all the required fields are present
     * @throws JSONException if there's a problem accessing the field names in the given object
     */
	private boolean checkFields(JSONObject category) throws JSONException {
        String API_ISSUES_URL = "api_url";
        JSONArray fieldNames = category.names();
        int numKeys = fieldNames.length();
        Set<String> foundNames = new HashSet<String>();
        for (int j = 0;j < numKeys; j++) {
            String fieldName = fieldNames.getString(j);
            foundNames.add(fieldName);
        }
        boolean allPresent = foundNames.contains(IssuesActivity.NUMBER) &&
                foundNames.contains(CATEGORY_NAME) &&
                foundNames.contains(IssuesActivity.PRIORITY) &&
                foundNames.contains(IssuesActivity.SUBJECT) &&
                foundNames.contains(DESCRIPTION) &&
                foundNames.contains(MILESTONE_NAME) &&
                foundNames.contains(OPENER_NAME) &&
                foundNames.contains(ASSIGNEE_NAME) &&
                foundNames.contains(IssuesActivity.STATUS) &&
                foundNames.contains(COMMENT_COUNT) &&
                foundNames.contains(CREATED_AT) &&
                foundNames.contains(UPDATED_AT) &&
                foundNames.contains(ISSUE_COMMENTS_URL) &&
                foundNames.contains(API_ISSUES_URL);

        return allPresent; // TODO If we're returning false, we ought to indicate which required fields are missing
    }
}
