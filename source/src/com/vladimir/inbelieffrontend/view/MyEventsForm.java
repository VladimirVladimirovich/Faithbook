package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.Event;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class MyEventsForm extends javax.swing.JDialog {
    private DefaultListModel eventsListModel;
    private List<Event> eventList;
    private List<String> eventCreatorsList;

    public MyEventsForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        eventsListModel = new DefaultListModel();
        initComponents();
        
        setUserEventsList(false);
    }
    
    public void setUserEventsList(boolean update) {
        try {
            if(update) {
                eventsListModel.clear();
                eventInfoTextArea.setText("");
            }
            
            int userId = InBeliefBackend.getInstance().getLoggedUserId();
            
            eventList = InBeliefBackend.getInstance().getUserEventsList(userId);
            eventCreatorsList = new ArrayList<>();
            
            for(Event event : eventList) {
                UserProfileData userProfileData = InBeliefBackend.getInstance().getUserProfileData(event.getCreatorId());
            
                String creatorName = userProfileData.getName() + " " + userProfileData.getLastName();
                
                eventCreatorsList.add(creatorName);
            }

            for (Event event: eventList) {
                eventsListModel.addElement(event.getTitle() + " (" + event.getDate().toString() + ")");
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void setSelectedEventInfo(int eventIndex) {
        try {
            Event event = eventList.get(eventIndex);
            
            String creatorName = eventCreatorsList.get(eventIndex);

            String [] religions = { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam",
                "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" };
            
            String info = "Title: " + event.getTitle() +
                         "\nCreator: " + creatorName +
                         "\nDate: " + event.getDate().toString() +
                         "\nReligion: " + religions[event.getReligionId() - 1] + 
                         "\nCity: " + event.getTown() + 
                         "\nMeeting point: " + event.getPlace() + 
                         "\nDescription and meeting point:\n   " + event.getDescription();

            eventInfoTextArea.setText("");
            eventInfoTextArea.append(info);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void unsubscribeFromEvent(int eventIndex) {
        Event event = eventList.get(eventIndex);
        
        int userId = InBeliefBackend.getInstance().getLoggedUserId();
        
        String message = "Do you really want to unsubcribe from '" + 
                event.getTitle() + "' event?";
        
        switch( showConfirmDialog(message, "Unsubscribe from event") ) {
            case JOptionPane.YES_OPTION:
                boolean unsubscribe = InBeliefBackend.getInstance().unsubscribeFromEvent(event, userId);
                System.out.println("unsubscribeFromEvent: " + unsubscribe);
                setUserEventsList(true);
                break;
            case JOptionPane.NO_OPTION:
                break;
            default:
                break;
        }
    }
    
    public void deleteEvent(int eventIndex) {
        try {
            Event event = eventList.get(eventIndex);

            int userId = InBeliefBackend.getInstance().getLoggedUserId();

            String message = "Do you really want to delete '" + 
                    event.getTitle() + "' event?";

            switch( showConfirmDialog(message, "Delete event") ) {
                case JOptionPane.YES_OPTION:
                    boolean deleteEvent = InBeliefBackend.getInstance().deleteEvent(event, userId);
                    
                    if(!deleteEvent)
                        showWarningMessage("You are not a creator of this event so you can't delete it!");
                    else
                       setUserEventsList(true); 
                    
                    System.out.println("deleteEvent: " + deleteEvent);
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
        }
        catch(Exception e) {
            showWarningMessage("You are not a creator of this event so you can't delete it!");
            System.out.println("Exceptiont: " + e.getMessage());
        }
    }
    
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public int showConfirmDialog(String message, String title) {
        return JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        eventsList = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        eventInfoTextArea = new javax.swing.JTextArea();
        backToMenuButton = new javax.swing.JButton();
        unsubscribeFromEventButton = new javax.swing.JButton();
        deleteEventButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("My events");
        setResizable(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Events list");

        eventsList.setModel(eventsListModel);
        eventsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        eventsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                eventsListValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(eventsList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Information");

        eventInfoTextArea.setEditable(false);
        eventInfoTextArea.setColumns(20);
        eventInfoTextArea.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        eventInfoTextArea.setRows(5);
        jScrollPane3.setViewportView(eventInfoTextArea);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(129, 129, 129))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        backToMenuButton.setText("Back");
        backToMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMenuButtonActionPerformed(evt);
            }
        });

        unsubscribeFromEventButton.setText("Unsubscribe from event");
        unsubscribeFromEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unsubscribeFromEventButtonActionPerformed(evt);
            }
        });

        deleteEventButton.setText("Delete event");
        deleteEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteEventButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(unsubscribeFromEventButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unsubscribeFromEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backToMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToMenuButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backToMenuButtonActionPerformed

    private void eventsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_eventsListValueChanged
        setSelectedEventInfo(eventsList.getSelectedIndex());
    }//GEN-LAST:event_eventsListValueChanged

    private void unsubscribeFromEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unsubscribeFromEventButtonActionPerformed
        if(eventsList.isSelectionEmpty())
            showWarningMessage("You must highlight an event you want to unsubscribe from!");
        else
            unsubscribeFromEvent(eventsList.getSelectedIndex());
    }//GEN-LAST:event_unsubscribeFromEventButtonActionPerformed

    private void deleteEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteEventButtonActionPerformed
        if(eventsList.isSelectionEmpty())
            showWarningMessage("You must highlight an event you want to delete!");
        else
            deleteEvent(eventsList.getSelectedIndex());
    }//GEN-LAST:event_deleteEventButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MyEventsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyEventsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyEventsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyEventsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MyEventsForm dialog = new MyEventsForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton backToMenuButton;
    private javax.swing.JButton deleteEventButton;
    private javax.swing.JTextArea eventInfoTextArea;
    private javax.swing.JList eventsList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton unsubscribeFromEventButton;
    // End of variables declaration//GEN-END:variables
}
