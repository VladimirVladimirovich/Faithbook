package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.FriendshipRequest;
import com.dtsey.inbeliefbackend.data.Sex;
import com.dtsey.inbeliefbackend.data.UserLoginData;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import com.dtsey.inbeliefbackend.exception.NoSuchUserException;
import com.dtsey.inbeliefbackend.data.search.FindUserCriteria;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;
import com.dtsey.inbeliefbackend.utils.DatabaseConnectionManager;
import com.dtsey.inbeliefbackend.utils.DatabaseUtils;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class MyFriendsForm extends javax.swing.JDialog {
    private DefaultListModel friendsListModel;
    private List<UserProfileData> userProfileDataList;

    public MyFriendsForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        friendsListModel = new DefaultListModel();
        initComponents();
        
        setUserFriendsList(false);
        setNewFriendshipRequests();
    }
    
    public void setUserFriendsList(boolean update) {
        try {
            if(update) {
                friendsListModel.clear();
                friendProfileTextArea.setText("");
            }
            
            int userId = InBeliefBackend.getInstance().getLoggedUserId();
            userProfileDataList = InBeliefBackend.getInstance().getUserFriends(userId);

            for (UserProfileData userProfileData : userProfileDataList) {
                friendsListModel.addElement(userProfileData.getName() + " " + userProfileData.getLastName());
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void setNewFriendshipRequests() {
        try {
            int userId = InBeliefBackend.getInstance().getLoggedUserId();
            List<FriendshipRequest> friendshipRequestList = InBeliefBackend.getInstance().getFriendshipRequestListForUser(userId);

            newReqestsLabel.setText("New requests: " + friendshipRequestList.size());
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void setSelectedFriendProfile() {
        try {
            int userId = InBeliefBackend.getInstance().getLoggedUserId();

            String [] religions = { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam",
                "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" };

            UserProfileData userProfileData = userProfileDataList.get(friendsList.getSelectedIndex());

            String profile = "Name: " + userProfileData.getName() +
                         "\nSirname: " + userProfileData.getLastName() +
                         "\nReligion: " + religions[userProfileData.getReligion() - 1] + 
                         "\nAge: " + userProfileData.getAge() + 
                         "\nSex: " + ((userProfileData.getSex().name().equals("MALE")) ? "male" : "female") + 
                         "\nCity: " + userProfileData.getTown() + 
                         "\nPhone: " + userProfileData.getPhone() + 
                         "\nE-mail: " + userProfileData.getEmail() + 
                         "\nAbout info: " + userProfileData.getDescription();

            friendProfileTextArea.setText("");
            friendProfileTextArea.append(profile);
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public boolean processNewFriendshipReqests() {
        try {
            int userId = InBeliefBackend.getInstance().getLoggedUserId();
            List<FriendshipRequest> friendshipRequestList = InBeliefBackend.getInstance().getFriendshipRequestListForUser(userId);
            
            if(friendshipRequestList.isEmpty()) {
                showInfoMessage("No new requests!");
                return false;
            }
            else {
                for (FriendshipRequest friendshipRequest : friendshipRequestList)
                    processCurrentFriendshipReqest(friendshipRequest);
                
                return true;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public void processCurrentFriendshipReqest(FriendshipRequest friendshipRequest) {
        try {
            int initiatorId = friendshipRequest.getInitiatorId();
            UserProfileData userProfileData = InBeliefBackend.getInstance().getUserProfileData(initiatorId);
                    
            String message = userProfileData.getName() + " " + userProfileData.getLastName() + 
                    " wants to become your " + ((userProfileData.getSex().name().equals("MALE")) ? "brother" : "sister") +
                    ". Accept this request?";
            
            switch(showProcessFriendshipRequestDialog(message)) {
                case JOptionPane.YES_OPTION:
                    boolean acceptFriendship = InBeliefBackend.getInstance().acceptFriendshipRequest(friendshipRequest);
                    System.out.println("acceptFriendshipRequest: " + acceptFriendship);
                    break;
                case JOptionPane.NO_OPTION:
                    boolean cancelFriendship = InBeliefBackend.getInstance().cancelFriendshipRequest(friendshipRequest);
                    System.out.println("cancelFriendshipRequest: " + cancelFriendship);
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void stopFriendship(int userIndex) {
        UserProfileData userProfileData = userProfileDataList.get(userIndex);
        
        int userId = InBeliefBackend.getInstance().getLoggedUserId();
        int targetId = userProfileData.getId();
        
        String message = "Do you really want to stop friendship with " + 
                userProfileData.getName() + " " + userProfileData.getLastName() + "?";
        
        switch( showStopFriendshipDialog(message) ) {
            case JOptionPane.YES_OPTION:
                boolean stopFriendship = InBeliefBackend.getInstance().deleteFriendshipRelation(userId, targetId);
                System.out.println("stopFriendship: " + stopFriendship);
                setUserFriendsList(true);
                break;
            case JOptionPane.NO_OPTION:
                break;
            default:
                break;
        }
    }
    
    public void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    public void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public int showProcessFriendshipRequestDialog(String message) {
        Object[] options = {"Accept", "Cancel"};
        
        return JOptionPane.showOptionDialog(this, message, "New request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    public int showStopFriendshipDialog(String message) {
        return JOptionPane.showOptionDialog(this, message, "Stop friendship", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        friendProfileTextArea = new javax.swing.JTextArea();
        backToMenuButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        newReqestsLabel = new javax.swing.JLabel();
        processNewReqestsButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        stopFriendshipButton = new javax.swing.JButton();

        jButton1.setText("В меню");

        jButton2.setText("jButton2");

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("My brothers and sisters");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Brothers and sisters list");

        friendsList.setModel(friendsListModel);
        friendsList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        friendsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                friendsListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(friendsList);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel2)
                .addGap(23, 23, 23))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Profile");

        friendProfileTextArea.setEditable(false);
        friendProfileTextArea.setColumns(20);
        friendProfileTextArea.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        friendProfileTextArea.setRows(5);
        jScrollPane2.setViewportView(friendProfileTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(136, 136, 136))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        backToMenuButton.setText("Back");
        backToMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMenuButtonActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        newReqestsLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        processNewReqestsButton.setText("Answer requests");
        processNewReqestsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processNewReqestsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(newReqestsLabel))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(processNewReqestsButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(newReqestsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(processNewReqestsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Friendship requests");
        jLabel4.setToolTipText("");

        stopFriendshipButton.setText("Stop friendship");
        stopFriendshipButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopFriendshipButtonActionPerformed(evt);
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
                        .addComponent(stopFriendshipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel4)))
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stopFriendshipButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backToMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToMenuButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backToMenuButtonActionPerformed

    private void friendsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_friendsListValueChanged
        setSelectedFriendProfile();
    }//GEN-LAST:event_friendsListValueChanged

    private void processNewReqestsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processNewReqestsButtonActionPerformed
        if(processNewFriendshipReqests()) {
            setUserFriendsList(true);
            setNewFriendshipRequests();
        }
    }//GEN-LAST:event_processNewReqestsButtonActionPerformed

    private void stopFriendshipButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopFriendshipButtonActionPerformed
        if(friendsList.isSelectionEmpty())
            showWarningMessage("You must highlight a person you want to stop friendship with!");
        else
            stopFriendship(friendsList.getSelectedIndex());
    }//GEN-LAST:event_stopFriendshipButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MyFriendsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MyFriendsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MyFriendsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MyFriendsForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MyFriendsForm dialog = new MyFriendsForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextArea friendProfileTextArea;
    private javax.swing.JList friendsList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel newReqestsLabel;
    private javax.swing.JButton processNewReqestsButton;
    private javax.swing.JButton stopFriendshipButton;
    // End of variables declaration//GEN-END:variables
}
