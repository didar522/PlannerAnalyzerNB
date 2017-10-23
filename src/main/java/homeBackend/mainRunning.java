package homeBackend;

import dataTemplates.ReleaseInfoCollection;
import dataTemplates.DataIssueTemplate;
import dataTemplates.ReleaseCalendarTemplate;
import dataTemplates.resultTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import dataPreprocess.DataReadExcelFiles;
import java.io.File;

public class mainRunning {

	//-----------Input
	
	public static String strFilePath = "C:/Users/Didar/Desktop/N4/";
	//	System.getProperty("user.dir")+ "/src/resources/InputOutput/";
	public static String strFileName = "N4.xlsx"; 
	public static String strSheetName = strFileName; 
        
        
//        public static String strFilePath;
//	public static String strFileName; 
//	public static String strSheetName; 
	public static int intStartingRowofData = 1;

	public static int daysForReplan = 0; 
	public static Date releaseStart=null, releaseEnd = null; 
	
	//-----------Input
	
	public static ReleaseInfoCollection obj_ReleaseInfoCollection;
	public static ArrayList<ReleaseCalendarTemplate> releaseInfo = new ArrayList<ReleaseCalendarTemplate> ();
	public static ArrayList <resultTemplate> list_resultAnalyzer; 
	
	public static HashMap<String, Integer> excelFileIndex = new HashMap <String, Integer> ();  
	public static ArrayList<DataIssueTemplate> allIssueData = new ArrayList<DataIssueTemplate>();  
	
	public static ArrayList<resultTemplate> list_resultPlanner; 
	
	
	
	public static void main (String args[]) throws Exception {
		
		ReadingExcelsheet (allIssueData,intStartingRowofData,strFilePath,strFileName,strSheetName); 
		
//		System.out.println(allIssueData.get(3).getStrKey());
		daysForReplan =2; 
//		
		runningAnalyzer (daysForReplan); 
//		runningPlanner ();

                
		
	}
        
        public mainRunning (){
            
        }
        
        public static void tokenizeFilePath (String fileAddress){
            File f = new File(fileAddress);
            strFileName = f.getName();
            strSheetName = strFileName; 
            strFilePath = f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("\\")+1); 
            
            System.out.println (strFilePath+"--------"+ strFileName ); 
                    
                    
        }
	
	
	public static void runningPlanner () throws Exception {
		homePlanner obj_homePlanner;
		dataReleaseDates();
		
		releaseStart=obj_ReleaseInfoCollection.getReleaseDate(3);
		releaseEnd=obj_ReleaseInfoCollection.getReleaseDate(6);
		
		obj_homePlanner = new homePlanner(releaseStart, releaseEnd, 4, allIssueData, 150000, 50, 30, 20); 
		list_resultPlanner = obj_homePlanner.runPlanner(true);
	}
	
	
	public static void runningAnalyzer (int daysForReplan) throws IOException{
		
		homeAnalyzer obj_homeAnalyzer;   
		dataReleaseDates();
		
		
		for (int releasenum=2;releasenum<3;releasenum++){
			 
				releaseStart=obj_ReleaseInfoCollection.getReleaseDate(releasenum);
				releaseEnd=obj_ReleaseInfoCollection.getReleaseDate(releasenum+1);
				list_resultAnalyzer= new ArrayList <resultTemplate> (); 
				
				for (int replanIterator=2;replanIterator<15;replanIterator=replanIterator+daysForReplan){
					obj_homeAnalyzer = new homeAnalyzer (releaseStart, releaseEnd, replanIterator, allIssueData ); 
					list_resultAnalyzer.add(obj_homeAnalyzer.runPlanner());
				}
				
				displayResults (releasenum, list_resultAnalyzer);
		}
	}
	
	
	
	public static void dataReleaseDates(){
		
		obj_ReleaseInfoCollection = new ReleaseInfoCollection (releaseInfo); 
		
		//------------Releases from BSQ-Mail project 
		
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-02-05", true, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-05-14", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-05-18", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-05-26", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-06-16", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-05-30", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-07-14", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-07-14", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-07-28", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-08-16", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-08-25", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-09-01", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-09-07", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-09-09", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-09-14", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-10-14", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-11-10", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2016-12-22", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2017-03-30", false, "NR");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2017-04-27", false, "R");
		obj_ReleaseInfoCollection.fillUpReleaseInfo("2017-05-11", false, "NR");

	}
	
	public static void displayResults (int releasenum, ArrayList <resultTemplate> tmplist_resultFormat) throws FileNotFoundException{
		PrintStream out = new PrintStream(new FileOutputStream(strFilePath+"output"+releasenum+".txt"));
		System.setOut(out);
		
		System.out.println(
				"releasenum1,"+
				"iterator.daysForReplan,"+
				"iterator.totalValue,"+
				"iterator.totalActualValue,"+
				 "iterator.actftrRatio,"+
				 "iterator.actbugRatio,"+
				 "iterator.actimpRatio,"+
				 "iterator.totalFtrTimeSpent,"+
				 "iterator.totalBugTimeSpent,"+
				 "iterator.totalImpTimeSpent,"+
				 "iterator.prpftrRatio,"+
				 "iterator.prpbugRatio,"+
				 "iterator.prpimpRatio,"+
				 "iterator.distance");
		
		for (resultTemplate iterator: tmplist_resultFormat){

			System.out.println(
					releasenum+ ","+
					iterator.daysForReplan+ ","+
					iterator.totalValue + ","+
					iterator.totalActualValue+ ","+
					 iterator.actftrRatio+ ","+
					 iterator.actbugRatio+ ","+
					 iterator.actimpRatio+ ","+
					 iterator.totalFtrTimeSpent+ ","+
					 iterator.totalBugTimeSpent+ ","+
					 iterator.totalImpTimeSpent+ ","+
					 iterator.prpftrRatio+ ","+
					 iterator.prpbugRatio+ ","+
					 iterator.prpimpRatio+ ","+
					 iterator.distance);
		
		}
	}
	
	public static void ReadingExcelsheet (ArrayList<DataIssueTemplate> tmp_IssueData, 
			int tmp_intStartingRowofData, 
			String tmp_strFilePath, 
			String tmp_strFileName, 
			String tmp_strSheetName){
		
		DataReadExcelFiles objDataReadExcelFiles = new DataReadExcelFiles(tmp_IssueData, tmp_intStartingRowofData, tmp_strFilePath, tmp_strFileName, tmp_strSheetName); 
		excelFileIndex = objDataReadExcelFiles.createColumnIndex(0); 
		objDataReadExcelFiles.readExcelFiles(false);
	}
	
}//End class




