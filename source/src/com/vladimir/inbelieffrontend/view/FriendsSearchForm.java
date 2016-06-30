package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.FriendshipRequest;
import com.dtsey.inbeliefbackend.data.Sex;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import com.dtsey.inbeliefbackend.exception.NoSuchUserException;
import com.dtsey.inbeliefbackend.data.search.FindUserCriteria;

import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class FriendsSearchForm extends javax.swing.JDialog {
    private DefaultListModel friendsListModel;
    
    private List<UserProfileData> userProfileDataList;
    
    public FriendsSearchForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        friendsListModel = new DefaultListModel();
        initComponents();
    }
    
    public boolean checkSearchCriteriasFields() {
        try {
            if( firstNameTextField.getText().isEmpty() &&
                lastNameTextField.getText().isEmpty() &&
                ageTextField.getText().isEmpty() &&
                cityTextField.getText().isEmpty() &&
                (religionComboBox.getSelectedIndex() == -1) &&
                !maleRadioButton.isSelected() &&
                !femaleRadioButton.isSelected() &&
                !anySexRadioButton.isSelected() )
            {
                showWarningMessage("At least one search criterion must be completed!");
                return false;
            }
            else if(ageTextField.getText().isEmpty()) {
                return true;
            }
            else if (Integer.parseInt(ageTextField.getText().toString()) <= 0 || Integer.parseInt(ageTextField.getText().toString()) > 100) {
                showWarningMessage("Enter a valid age!");
                return false;
            }
            else {
                return true;
            }
        }
        catch(NumberFormatException e) {
            showWarningMessage("Enter a valid age!");
            return false;
        }
    }
    
    public void findUsersByCriteria() {
        FindUserCriteria byName = null, byLastname = null, byReligion = null, byAge = null, bySex = null, byCity = null;
        FindUserCriteria[] findUserCriterias = {byName, byLastname, byReligion, byAge, bySex, byCity};
        
        int criteriasCount = 0;
        
        if(!firstNameTextField.getText().isEmpty()) {
            findUserCriterias[0] = FindUserCriteria.NAME.setName(firstNameTextField.getText());
            criteriasCount++;
        }
        if(!lastNameTextField.getText().isEmpty()) {
            findUserCriterias[1] = FindUserCriteria.LASTNAME.setLastname(lastNameTextField.getText());
            criteriasCount++;
        }
        if(religionComboBox.getSelectedIndex() != -1) {
            findUserCriterias[2] = FindUserCriteria.RELIGION.setReligion(religionComboBox.getSelectedIndex() + 1);
            criteriasCount++;
        }
        if(!ageTextField.getText().isEmpty()) {
            findUserCriterias[3] = FindUserCriteria.AGE.setAge(Integer.parseInt(ageTextField.getText().toString()));
            criteriasCount++;
        }
        if(maleRadioButton.isSelected()) {
            findUserCriterias[4] = FindUserCriteria.SEX.setSex(Sex.MALE);
            criteriasCount++;
        }
        if(femaleRadioButton.isSelected()) {
            findUserCriterias[4] = FindUserCriteria.SEX.setSex(Sex.FEMALE);
            criteriasCount++;
        }
        if(!cityTextField.getText().isEmpty()) {
            findUserCriterias[5] = FindUserCriteria.TOWN.setTown(cityTextField.getText());
            criteriasCount++;
        }
        
        if(criteriasCount == 0 && (anySexRadioButton.isSelected())) {
            userProfileDataList = InBeliefBackend.getInstance().findAllUsers();
            fillSearchResultList();
            return;
        }
            
        FindUserCriteria[] findUserSpecifiedCriterias = new FindUserCriteria [criteriasCount];
        
        for(int i = 0, j = 0; i < findUserCriterias.length; i++) {
            if(findUserCriterias[i] != null) {
                findUserSpecifiedCriterias[j] = findUserCriterias[i];
                j++;
            }
        }
        
        userProfileDataList = InBeliefBackend.getInstance().findUsersByCriteria(findUserSpecifiedCriterias);
        
        if(userProfileDataList.isEmpty()) {
            showInfoMessage("No results found! Try to specify another search criteria.");
        }
        
        fillSearchResultList();
    }
    
    public void fillSearchResultList() {
        friendsListModel.clear();
        
        for(UserProfileData userProfileData : userProfileDataList) {
            friendsListModel.addElement(userProfileData.getName() + " " + userProfileData.getLastName());
        }
    }
    
    public void showSelectedProfile(int userIndex) {
        UserProfileData userProfileData = userProfileDataList.get(userIndex);
        
        String profile = "Name: " + userProfileData.getName() +
                         "\nSirname: " + userProfileData.getLastName() +
                         "\nReligion: " + religionComboBox.getItemAt(userProfileData.getReligion() - 1).toString() + 
                         "\nAge: " + userProfileData.getAge() + 
                         "\nSex: " + ((userProfileData.getSex().name().equals("MALE")) ? "male" : "female") + 
                         "\nCity: " + userProfileData.getTown() + 
                         "\nPhone: " + userProfileData.getPhone() + 
                         "\nE-mail: " + userProfileData.getEmail() + 
                         "\nAbout info: " + userProfileData.getDescription();
        
        showInfoMessage(profile);
    }
    
    public void sendFriendshipRequest(int userIndex) {
         try {
            int initiatorId = InBeliefBackend.getInstance().getLoggedUserId();
            int targetId = userProfileDataList.get(userIndex).getId();
        
            boolean createFriendshipResult = InBeliefBackend.getInstance().addFriendshipRequest(new FriendshipRequest(initiatorId, targetId));
            
            if(createFriendshipResult)
                showInfoMessage("Request was send successfully!");
            
            System.out.println("createFriendshipResult: " + createFriendshipResult) ;
         }
         catch (Exception e) {
            showWarningMessage("You have already sent a request to this user!"); 
            System.out.println("Error: " + e.getMessage());
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        maritalStatusLabel = new javax.swing.JLabel();
        religionComboBox = new javax.swing.JComboBox();
        ageLabel = new javax.swing.JLabel();
        ageTextField = new javax.swing.JTextField();
        sexLabel = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        anySexRadioButton = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        addUserToFriends = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        foundedUsersList = new javax.swing.JList();
        showUserProfile = new javax.swing.JButton();
        backToMenuButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        jToolBar1.setRollover(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Поиск братьев и сестер");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        firstNameLabel.setText("Name:"); // NOI18N

        surnameLabel.setText("Surname:"); // NOI18N

        maritalStatusLabel.setText("Religion:"); // NOI18N

        religionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam", "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" }));
        religionComboBox.setSelectedIndex(-1);

        ageLabel.setText("Age:"); // NOI18N

        sexLabel.setText("Sex:"); // NOI18N

        buttonGroup1.add(maleRadioButton);
        maleRadioButton.setText("male"); // NOI18N

        buttonGroup1.add(femaleRadioButton);
        femaleRadioButton.setText("female"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Search criteria");

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        jLabel4.setText("City:");

        buttonGroup1.add(anySexRadioButton);
        anySexRadioButton.setText("any");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstNameLabel)
                            .addComponent(surnameLabel)
                            .addComponent(maritalStatusLabel)
                            .addComponent(ageLabel)
                            .addComponent(sexLabel)
                            .addComponent(jLabel4))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(religionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ageTextField)
                            .addComponent(lastNameTextField)
                            .addComponent(firstNameTextField)
                            .addComponent(cityTextField)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(maleRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(femaleRadioButton)
                                .addGap(10, 10, 10)
                                .addComponent(anySexRadioButton)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(77, 77, 77))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(87, 87, 87))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameLabel)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maritalStatusLabel)
                    .addComponent(religionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageLabel)
                    .addComponent(ageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexLabel)
                    .addComponent(femaleRadioButton)
                    .addComponent(maleRadioButton)
                    .addComponent(anySexRadioButton))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Search result");

        addUserToFriends.setText("<html><center>Add to brothers and sisters</center>");
        addUserToFriends.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserToFriendsActionPerformed(evt);
            }
        });

        foundedUsersList.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        foundedUsersList.setModel(friendsListModel);
        foundedUsersList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(foundedUsersList);

        showUserProfile.setText("<html><center>Show profile</center>");
        showUserProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUserProfileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(showUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addUserToFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(showUserProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addUserToFriends, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        backToMenuButton.setText("Back");
        backToMenuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToMenuButtonActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("<html>Specify search criteria (1 - 6) and click \"Search\" button. Highlight a person in the \"Search results\" list and click the corresponding button to add him to \"My brothers and sisters\" or view his profile.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(backToMenuButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backToMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToMenuButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_backToMenuButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        if(checkSearchCriteriasFields())
            findUsersByCriteria();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void showUserProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showUserProfileActionPerformed
        if(foundedUsersList.getSelectedIndex() != -1)
            showSelectedProfile(foundedUsersList.getSelectedIndex());
    }//GEN-LAST:event_showUserProfileActionPerformed

    private void addUserToFriendsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserToFriendsActionPerformed
        if(foundedUsersList.getSelectedIndex() != -1)
            sendFriendshipRequest(foundedUsersList.getSelectedIndex());
    }//GEN-LAST:event_addUserToFriendsActionPerformed

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
            java.util.logging.Logger.getLogger(FriendsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FriendsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FriendsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FriendsSearchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FriendsSearchForm dialog = new FriendsSearchForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton addUserToFriends;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField ageTextField;
    private javax.swing.JRadioButton anySexRadioButton;
    private javax.swing.JButton backToMenuButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JList foundedUsersList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JComboBox religionComboBox;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JButton showUserProfile;
    private javax.swing.JLabel surnameLabel;
    // End of variables declaration//GEN-END:variables
}
