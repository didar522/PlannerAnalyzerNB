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
import java.util.Date;
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
        
        infoSolution1Panel.setVisible(false);
        infoSolution2Panel.setVisible(false);
        infoSolution3Panel.setVisible(false);
        
        jLabel9.setVisible(false);
        jLabel10.setVisible(false);
        jLabel11.setVisible(false);
        
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        jLabel14.setVisible(false);
        
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        
        
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
        jTextField4totalCapacity = new javax.swing.JTextField();
        jXDatePicker1startDate = new org.jdesktop.swingx.JXDatePicker();
        jXDatePicker1endDate = new org.jdesktop.swingx.JXDatePicker();
        replanInterval = new javax.swing.JComboBox<>();
        jComboBox1ftrRatio = new javax.swing.JComboBox<>();
        jComboBox2bugRatio = new javax.swing.JComboBox<>();
        jComboBox3impRatio = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableSolution1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableSolution2 = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableSolution3 = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        infoSolution1Panel = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        infoSolution2Panel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        infoSolution3Panel = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        jSplitPane1.setDividerLocation(300);

        jButton1.setText("Generate Plan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        replanInterval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "4", "6", "8", "10", "12", "14", "16" }));

        jComboBox1ftrRatio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" }));

        jComboBox2bugRatio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" }));

        jComboBox3impRatio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100" }));

        jLabel1.setText("Release Start Date");

        jLabel2.setText("Release End Date");

        jLabel3.setText("Re plan Interval");

        jLabel4.setText("Available Capacity");

        jLabel5.setText("Capacity for feature");

        jLabel6.setText("Capacity for Bug");

        jLabel7.setText("Capacity for Improvement");

        jCheckBox1.setText("ACO");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("SMPSO");

        jCheckBox3.setText("NSGA II");

        jLabel8.setText("Choice of algorithms");

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox1)
                    .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jXDatePicker1endDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jXDatePicker1startDate, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jComboBox3impRatio, javax.swing.GroupLayout.Alignment.LEADING, 0, 123, Short.MAX_VALUE)
                        .addComponent(jComboBox2bugRatio, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1ftrRatio, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4totalCapacity, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(replanInterval, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, leftPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jXDatePicker1startDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jXDatePicker1endDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(4, 4, 4)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(replanInterval, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4totalCapacity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(leftPanelLayout.createSequentialGroup()
                        .addComponent(jComboBox1ftrRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2bugRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3impRatio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addGap(12, 12, 12)
                .addComponent(jButton1)
                .addContainerGap(323, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(leftPanel);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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

        jTabbedPane4.addTab("Solution 1", jScrollPane2);

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

        jTabbedPane4.addTab("Solution 2", jScrollPane3);

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

        jTabbedPane4.addTab("Solution 3", jScrollPane4);
        jTabbedPane4.addTab("Comparison", jScrollPane5);

        infoSolution1Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Solution 1 Details"));
        infoSolution1Panel.setPreferredSize(new java.awt.Dimension(300, 80));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 255));
        jLabel9.setText("jLabel9");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("jLabel10");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("jLabel11");

        javax.swing.GroupLayout infoSolution1PanelLayout = new javax.swing.GroupLayout(infoSolution1Panel);
        infoSolution1Panel.setLayout(infoSolution1PanelLayout);
        infoSolution1PanelLayout.setHorizontalGroup(
            infoSolution1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution1PanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(infoSolution1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        infoSolution1PanelLayout.setVerticalGroup(
            infoSolution1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution1PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        infoSolution2Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Solution 2 Details"));

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 255));
        jLabel12.setText("jLabel12");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 255));
        jLabel13.setText("jLabel13");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 255));
        jLabel14.setText("jLabel14");

        javax.swing.GroupLayout infoSolution2PanelLayout = new javax.swing.GroupLayout(infoSolution2Panel);
        infoSolution2Panel.setLayout(infoSolution2PanelLayout);
        infoSolution2PanelLayout.setHorizontalGroup(
            infoSolution2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution2PanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(infoSolution2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12))
                .addContainerGap(124, Short.MAX_VALUE))
        );
        infoSolution2PanelLayout.setVerticalGroup(
            infoSolution2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution2PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        infoSolution3Panel.setBorder(javax.swing.BorderFactory.createTitledBorder("Solution 3 Details"));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 51, 255));
        jLabel15.setText("jLabel15");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 255));
        jLabel16.setText("jLabel16");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 255));
        jLabel17.setText("jLabel17");

        javax.swing.GroupLayout infoSolution3PanelLayout = new javax.swing.GroupLayout(infoSolution3Panel);
        infoSolution3Panel.setLayout(infoSolution3PanelLayout);
        infoSolution3PanelLayout.setHorizontalGroup(
            infoSolution3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoSolution3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17))
                .addContainerGap(137, Short.MAX_VALUE))
        );
        infoSolution3PanelLayout.setVerticalGroup(
            infoSolution3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoSolution3PanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(infoSolution1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoSolution2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(infoSolution3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jTabbedPane4)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(infoSolution2Panel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoSolution1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(infoSolution3Panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                .addGap(44, 44, 44))
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
        
        Date releaseStart = jXDatePicker1startDate.getDate();
        System.out.println(releaseStart.toString());
        Date releaseEnd = jXDatePicker1endDate.getDate(); 
        System.out.println(releaseStart.toString());
        int capacity = Integer.parseInt(jTextField4totalCapacity.getText()); System.out.println(capacity);
        int featureRatio = Integer.parseInt((String)jComboBox1ftrRatio.getSelectedItem()); System.out.println(featureRatio);
        
        int bugRatio = Integer.parseInt((String)jComboBox2bugRatio.getSelectedItem()); System.out.println(bugRatio);
        int impRatio = Integer.parseInt((String)jComboBox3impRatio.getSelectedItem()); System.out.println(impRatio);
        int intreplanInterval = Integer.parseInt((String)replanInterval.getSelectedItem()); System.out.println(intreplanInterval);
        
        System.out.println(releaseStart.toString() + releaseStart.toString());
        System.out.println(capacity + " "+ featureRatio + " "+ bugRatio + " "+impRatio +" "+intreplanInterval);
        
        homePlanner obj_homePlanner;
		dataReleaseDates();
		
//		releaseStart=obj_ReleaseInfoCollection.getReleaseDate(4);
//		releaseEnd=obj_ReleaseInfoCollection.getReleaseDate(6);
//		obj_homePlanner = new homePlanner(releaseStart, releaseEnd, 6, allIssueData, 50000, 50, 30, 20); 
                
                obj_homePlanner = new homePlanner(releaseStart, releaseEnd, intreplanInterval, allIssueData, capacity, featureRatio, bugRatio, impRatio);
        try {
            list_resultPlanner = obj_homePlanner.runPlanner();
        } catch (Exception ex) {
            System.out.println("gui.Planner.plannerJPanel.jButton1ActionPerformed()");
            Logger.getLogger(plannerJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        infoSolution1Panel.setVisible(true);
        infoSolution2Panel.setVisible(true);
        infoSolution3Panel.setVisible(true);
        
        jLabel9.setText("Total value: "+ Integer.toString(list_resultPlanner.get(0).totalValue));
        jLabel9.setVisible(true);
        jLabel10.setText("Total Distance: "+ Double.toString(list_resultPlanner.get(0).distance));
        jLabel10.setVisible(true);
        jLabel11.setText("Effort ratio (ftr, Bug, Imp ): "+ Double.toString(list_resultPlanner.get(0).prpftrRatio) + " "+Double.toString(list_resultPlanner.get(0).prpbugRatio)+" "+Double.toString(list_resultPlanner.get(0).prpimpRatio));
        jLabel11.setVisible(true);
        
//        if (list_resultPlanner.size()>1){
        
            jLabel12.setText("Total value: "+ Integer.toString(list_resultPlanner.get(1).totalValue));
            jLabel12.setVisible(true);
            jLabel13.setText("Total Distance: "+ Double.toString(list_resultPlanner.get(1).distance));
            jLabel13.setVisible(true);
            jLabel14.setText("Effort ratio (ftr, Bug, Imp ): "+ Double.toString(list_resultPlanner.get(1).prpftrRatio) + " "+Double.toString(list_resultPlanner.get(1).prpbugRatio)+" "+Double.toString(list_resultPlanner.get(1).prpimpRatio));
            jLabel14.setVisible(true);
//        }
        
//        if (list_resultPlanner.size()>2){
        
            jLabel15.setText("Total value: "+ Integer.toString(list_resultPlanner.get(2).totalValue));
            jLabel15.setVisible(true);
            jLabel16.setText("Total Distance: "+ Double.toString(list_resultPlanner.get(2).distance));
            jLabel16.setVisible(true);
            jLabel17.setText("Effort ratio (ftr, Bug, Imp ): "+ Double.toString(list_resultPlanner.get(2).prpftrRatio) + " "+Double.toString(list_resultPlanner.get(2).prpbugRatio)+" "+Double.toString(list_resultPlanner.get(2).prpimpRatio));
            jLabel17.setVisible(true);
//        }
       
        
        
        String Query1 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 1\";";
        String Query2 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 2\";";
        String Query3 = "SELECT * FROM OfferedIssueData WHERE Offered LIKE \"offered in 3\";";
        display1ExcelData (Query1); 
        display2ExcelData (Query2); 
        display3ExcelData (Query3); 
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

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
    private javax.swing.JPanel infoSolution1Panel;
    private javax.swing.JPanel infoSolution2Panel;
    private javax.swing.JPanel infoSolution3Panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JComboBox<String> jComboBox1ftrRatio;
    private javax.swing.JComboBox<String> jComboBox2bugRatio;
    private javax.swing.JComboBox<String> jComboBox3impRatio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JTextField jTextField4totalCapacity;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1endDate;
    private org.jdesktop.swingx.JXDatePicker jXDatePicker1startDate;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JComboBox<String> replanInterval;
    // End of variables declaration//GEN-END:variables

ArrayList<DataIssueTemplate> allIssueData; 
ResultSet resultSet = null;
sqliteAllResults obj_sqliteAllResults; 




}
