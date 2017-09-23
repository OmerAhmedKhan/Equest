package com.equest;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class severityScoreEntity {
	static Map<String, Map> severity = null;
	Map<String,String> subjectSeverity;
	static String[] sectionNames;
	static int sectionSize;
	public severityScoreEntity()
	{
		if(severity==null)
			severity=new HashMap<String, Map>();
		subjectSeverity=new HashMap<String, String>();
	
	}
	public static void setSectionSize(int size)
	{
		sectionSize=size;
		sectionNames=new String[size];
	}
	public static int retSectionSize()
	{
		return sectionSize;
	}
	public static void setSectionName(String name,int index)
	{
		sectionNames[index]=name.trim();
	}
	public static String retSectionName(int index)
	{
		return sectionNames[index];
	}
	public void setEasy(String subject,String correct,String total)
	{
		Log.e("setEasy","subject: " + subject);
		subjectSeverity.put("easyCorrect",correct.trim());
		subjectSeverity.put("easyTotal",total.trim());
		severity.put(subject.trim(), subjectSeverity);
	}
	
	public void setMedium(String subject,String correct,String total)
	{
		subjectSeverity.put("mediumCorrect",correct.trim());
		subjectSeverity.put("mediumTotal",total.trim());
		severity.put(subject.trim(), subjectSeverity);
	}

	public void setHard(String subject,String correct,String total)
	{
		subjectSeverity.put("hardCorrect",correct.trim());
		subjectSeverity.put("hardTotal",total.trim());
		severity.put(subject.trim(), subjectSeverity);
	}
	
	
	public String retEasyCorrect(String subject)
	{
		Log.e("","---------------------------------");
		Log.e("subject",""+subject);
		Log.e("severity",""+severity);
		Log.e("severity.get(subject)",""+severity.get(subject.trim()));
		
		Map subSeverity = severity.get(subject.trim());
		Log.e("subSeverity.get(easyCorrect)",""+subSeverity.get("easyCorrect"));
		Log.e("","---------------------------------");
		return subSeverity.get("easyCorrect").toString();
	}
	
	public String retEasyTotal(String subject)
	{
		return (String) severity.get(subject).get("easyTotal");
	}
	public String retMediumCorrect(String subject)
	{
		return (String) severity.get(subject).get("mediumCorrect");
	}
	public String retMediumTotal(String subject)
	{
		return (String) severity.get(subject).get("mediumTotal");
	}
	public String retHardCorrect(String subject)
	{
		return (String) severity.get(subject).get("hardCorrect");
	}
	public String retHardTotal(String subject)
	{
		return (String) severity.get(subject).get("hardTotal");
	}
	@Override
	public String toString() {
		return "severityScoreEntity [severity=" + severity
				+ ", subjectSeverity=" + subjectSeverity + "]";
	}


}
