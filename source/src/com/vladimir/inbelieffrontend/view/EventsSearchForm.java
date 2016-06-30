package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.Event;
import com.dtsey.inbeliefbackend.data.EventSubscription;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import com.dtsey.inbeliefbackend.data.search.FindEventCriteria;
import java.sql.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class EventsSearchForm extends javax.swing.JDialog {
    private DefaultListModel eventsListModel;
    
    private List<Event> eventList;

    public EventsSearchForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        eventsListModel = new DefaultListModel();
        initComponents();
    }
    
    public boolean checkSearchCriteriasFields() {
        try {
            if( titleTextField.getText().isEmpty() &&
                dateTextField.getText().isEmpty() &&
                cityTextField.getText().isEmpty() &&
                (religionComboBox.getSelectedIndex() == -1))
            {
                showWarningMessage("At least one search criterion must be completed!");
                return false;
            }
            else if(!dateTextField.getText().isEmpty()){
                Date parseDate = java.sql.Date.valueOf(dateTextField.getText());
                return true;
            }
            else
                return true;
        }   
        catch(IllegalArgumentException e) {
            showWarningMessage("Enter date in 'yyyy-mm-dd' format!");
            return false;
        }
    }
    
    public void findEventsByCriteria() {
        FindEventCriteria byTitle = null, byDate = null, byReligion = null, byCity = null;
        FindEventCriteria[] findEventCriterias = { byTitle, byDate, byReligion, byCity };
        
        int criteriasCount = 0;
        
        if(!titleTextField.getText().isEmpty()) {
            findEventCriterias[0] = FindEventCriteria.TITLE.setTitle(titleTextField.getText());
            criteriasCount++;
        }
        if(!dateTextField.getText().isEmpty()) {
            Date parseDate = java.sql.Date.valueOf(dateTextField.getText());
            findEventCriterias[1] = FindEventCriteria.DATE.setDate(parseDate);
            criteriasCount++;
        }
        if(religionComboBox.getSelectedIndex() != -1) {
            findEventCriterias[2] = FindEventCriteria.RELIGION.setReligion(religionComboBox.getSelectedIndex() + 1);
            criteriasCount++;
        }
        if(!cityTextField.getText().isEmpty()) {
            findEventCriterias[3] = FindEventCriteria.TOWN.setTown(cityTextField.getText());
            criteriasCount++;
        }
        
        FindEventCriteria[] findEventSpecifiedCriterias = new FindEventCriteria [criteriasCount];
    
        for(int i = 0, j = 0; i < findEventCriterias.length; i++) {
            if(findEventCriterias[i] != null) {
                findEventSpecifiedCriterias[j] = findEventCriterias[i];
                j++;
            }
        }
        
        eventList = InBeliefBackend.getInstance().getEventsByCriteria(findEventSpecifiedCriterias);
    
        if(eventList.isEmpty()) {
            showInfoMessage("No results found! Try to specify another search criteria.");
        }
        
        fillSearchResultList();
    }
    
    public void fillSearchResultList() {
        eventsListModel.clear();
        
        for(Event event : eventList) {
            eventsListModel.addElement(event.getTitle() + " (" + event.getDate().toString() + ")");
        }
    }
    
    public void showSelectedEventInfo(int eventIndex) {
        try {
            Event event = eventList.get(eventIndex);
            UserProfileData userProfileData = InBeliefBackend.getInstance().getUserProfileData(event.getCreatorId());
            String creatorName = userProfileData.getName() + " " + userProfileData.getLastName();

            String info = "Title: " + event.getTitle() +
                             "\nCreator: " + creatorName +
                             "\nDate: " + event.getDate().toString() +
                             "\nReligion: " + religionComboBox.getItemAt(event.getReligionId() - 1).toString() + 
                             "\nCity: " + event.getTown() + 
                             "\nMeeting point: " + event.getPlace() + 
                             "\nDescription and meeting time: " + event.getDescription();

            showInfoMessage(info);
        }
        catch(Exception e) {
             System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void addSelectedEvent(int eventIndex) {
        try {
            Event event = eventList.get(eventIndex);

            int eventId = event.getId();
            int subscriberId = InBeliefBackend.getInstance().getLoggedUserId();

            EventSubscription eventSubscription = new EventSubscription(subscriberId, eventId);

            boolean subscribeResult = InBeliefBackend.getInstance().subscribeForEvent(eventSubscription);

            if(subscribeResult)
                showInfoMessage("You have subscribed to this event successfully!");

            System.out.println("subscribeResult: " + subscribeResult);
        }
        catch(Exception e) {
            showWarningMessage("You have already subscribed to this event!");
            System.out.println("Exeption: " + e.getMessage());
        }
    }
    
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        titleTextField = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        dateTextField = new javax.swing.JTextField();
        maritalStatusLabel = new javax.swing.JLabel();
        religionComboBox = new javax.swing.JComboBox();
        ageLabel = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        searchEventsButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addEventButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        foundedEventsList = new javax.swing.JList();
        showEventInfoButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        backToMenuButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Events search");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        firstNameLabel.setText("Title:"); // NOI18N
        firstNameLabel.setToolTipText("");

        surnameLabel.setText("Date:"); // NOI18N

        dateTextField.setToolTipText("Only 'yyyy-mm-dd' format!");

        maritalStatusLabel.setText("Religion:"); // NOI18N

        religionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam", "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" }));
        religionComboBox.setSelectedIndex(-1);

        ageLabel.setText("City:"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search criteria");

        searchEventsButton.setText("Search");
        searchEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchEventsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstNameLabel)
                    .addComponent(surnameLabel)
                    .addComponent(maritalStatusLabel)
                    .addComponent(ageLabel))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(religionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cityTextField)
                    .addComponent(dateTextField)
                    .addComponent(titleTextField))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(searchEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(78, 78, 78))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameLabel)
                    .addComponent(dateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maritalStatusLabel)
                    .addComponent(religionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageLabel)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(searchEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Search result");

        addEventButton.setText("Subscribe to event");
        addEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addEventButtonActionPerformed(evt);
            }
        });

        foundedEventsList.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        foundedEventsList.setModel(eventsListModel);
        foundedEventsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(foundedEventsList);

        showEventInfoButton.setText("<html>Show info");
        showEventInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showEventInfoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(showEventInfoButton)
                        .addGap(18, 18, 18)
                        .addComponent(addEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(showEventInfoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("<html>Specify search criteria (1 - 4) and click \"Search\" button. Highlight an event in the \"Search results\" list and click the corresponding button to add event to \"My events\" or view its info.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        backToMenuButton.setText("Back");
        backToMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMenuButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backToMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToMenuButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backToMenuButtonActionPerformed

    private void searchEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchEventsButtonActionPerformed
        if(checkSearchCriteriasFields())
            findEventsByCriteria();
    }//GEN-LAST:event_searchEventsButtonActionPerformed

    private void showEventInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showEventInfoButtonActionPerformed
        if(foundedEventsList.getSelectedIndex() != -1)
            showSelectedEventInfo(foundedEventsList.getSelectedIndex());
    }//GEN-LAST:event_showEventInfoButtonActionPerformed

    private void addEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addEventButtonActionPerformed
        if(foundedEventsList.getSelectedIndex() != -1)
            addSelectedEvent(foundedEventsList.getSelectedIndex());
    }//GEN-LAST:event_addEventButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EventsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EventsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EventsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EventsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EventsSearchForm dialog = new EventsSearchForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addEventButton;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JButton backToMenuButton;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JTextField dateTextField;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JList foundedEventsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JComboBox religionComboBox;
    private javax.swing.JButton searchEventsButton;
    private javax.swing.JButton showEventInfoButton;
    private javax.swing.JLabel surnameLabel;
    private javax.swing.JTextField titleTextField;
    // End of variables declaration//GEN-END:variables
}
