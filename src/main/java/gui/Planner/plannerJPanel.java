/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Planner;

import dataTemplates.DataIssueTemplate;
import db.sqliteAllResults;
import guiImport.importJDialog;
import homeBackend.homePlanner;
import static homeBackend.mainRunning.allIssueData;
import static homeBackend.mainRunning.dataReleaseDates;
import static homeBackend.mainRunning.list_resultPlanner;
import static homeBackend.mainRunning.obj_ReleaseInfoCollection;
import static homeBackend.mainRunning.releaseEnd;
import static homeBackend.mainRunning.releaseStart;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Didar
 */
public class plannerJPanel extends javax.swing.JPanel {

    /**
     * Creates new form plannerJPanel
     */
    public plannerJPanel(ArrayList<DataIssueTemplate> tmpallIssueData) {
        initComponents();
        this.allIssueData = tmpallIssueData; 
        System.out.println("from the jpanel "+allIssueData.get(10).getStrKey());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        leftPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        SolutionInfoPanel = new javax.swing.JPanel();
        SolutionTabsjPanel2 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSolution1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSolution2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableSolution3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();

        jSplitPane1.setDividerLocation(250);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jButton1)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addContainerGap(815, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(28, 28, 28))
        );

        jSplitPane1.setLeftComponent(leftPanel);

        SolutionInfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout SolutionInfoPanelLayout = new javax.swing.GroupLayout(SolutionInfoPanel);
        SolutionInfoPanel.setLayout(SolutionInfoPanelLayout);
        SolutionInfoPanelLayout.setHorizontalGroup(
            SolutionInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        SolutionInfoPanelLayout.setVerticalGroup(
            SolutionInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 140, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout SolutionTabsjPanel2Layout = new javax.swing.GroupLayout(SolutionTabsjPanel2);
        SolutionTabsjPanel2.setLayout(SolutionTabsjPanel2Layout);
        SolutionTabsjPanel2Layout.setHorizontalGroup(
            SolutionTabsjPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 52, Short.MAX_VALUE)
        );
        SolutionTabsjPanel2Layout.setVerticalGroup(
            SolutionTabsjPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTableSolution1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTableSolution1);

        jTabbedPane4.addTab("tab1", jScrollPane2);

        jTableSolution2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTableSolution2);

        jTabbedPane4.addTab("tab2", jScrollPane3);

        jTableSolution3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(jTableSolution3);

        jTabbedPane4.addTab("tab3", jScrollPane4);
        jTabbedPane4.addTab("tab4", jScrollPane5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane4)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(918, 918, 918)
                        .addComponent(SolutionTabsjPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(SolutionInfoPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SolutionInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SolutionTabsjPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        homePlanner obj_homePlanner;
		dataReleaseDates();
		
		releaseStart=obj_ReleaseInfoCollection.getReleaseDate(2);
		releaseEnd=obj_ReleaseInfoCollection.getReleaseDate(3);
		
		obj_homePlanner = new homePlanner(releaseStart, releaseEnd, 4, allIssueData, 15000, 20, 30, 50); 
        try {
            list_resultPlanner = obj_homePlanner.runPlanner();
        } catch (Exception ex) {
            System.out.println("gui.Planner.plannerJPanel.jButton1ActionPerformed()");
            Logger.getLogger(plannerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String Query1 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 1\";";
        String Query2 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 2\";";
        String Query3 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 3\";";
        display1ExcelData (Query1); 
        display2ExcelData (Query2); 
        display3ExcelData (Query1); 
    }//GEN-LAST:event_jButton1ActionPerformed

public void display1ExcelData (String strQuery){
        try {    
            resultSet=obj_sqliteAllResults.getResults(strQuery, "DB/BSQPLanner.DB.sqlite");
            jTableSolution1.setModel(DbUtils.resultSetToTableModel(resultSet));
            obj_sqliteAllResults.closeConnection();
            
        } catch (Exception ex) {
            System.out.println("gui.Planner.plannerJPanel.display1ExcelData()");
            Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public void display2ExcelData (String strQuery){
        try {    
            resultSet=obj_sqliteAllResults.getResults(strQuery, "DB/BSQPLanner.DB.sqlite");
            jTableSolution2.setModel(DbUtils.resultSetToTableModel(resultSet));
            obj_sqliteAllResults.closeConnection();
            
        } catch (Exception ex) {
            System.out.println("gui.Planner.plannerJPanel.display2ExcelData()");
            Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

public void display3ExcelData (String strQuery){
        try {    
            resultSet=obj_sqliteAllResults.getResults(strQuery, "DB/BSQPLanner.DB.sqlite");
            jTableSolution3.setModel(DbUtils.resultSetToTableModel(resultSet));
            obj_sqliteAllResults.closeConnection();
            
        } catch (Exception ex) {
            System.out.println("gui.Planner.plannerJPanel.display3ExcelData()");
            Logger.getLogger(importJDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SolutionInfoPanel;
    private javax.swing.JPanel SolutionTabsjPanel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTableSolution1;
    private javax.swing.JTable jTableSolution2;
    private javax.swing.JTable jTableSolution3;
    private javax.swing.JPanel leftPanel;
    // End of variables declaration//GEN-END:variables

ArrayList<DataIssueTemplate> allIssueData; 
ResultSet resultSet = null;
sqliteAllResults obj_sqliteAllResults; 




}
