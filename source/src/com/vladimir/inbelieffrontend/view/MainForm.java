package com.vladimir.inbelieffrontend.view;

import com.dtsey.inbeliefbackend.InBeliefBackend;
import com.dtsey.inbeliefbackend.data.Sex;
import com.dtsey.inbeliefbackend.data.UserLoginData;
import com.dtsey.inbeliefbackend.data.UserProfileData;
import com.dtsey.inbeliefbackend.exception.NoSuchUserException;
import com.dtsey.inbeliefbackend.exception.UserAlreadyRegisteredException;
import com.dtsey.inbeliefbackend.utils.DatabaseConnectionManager;
import com.dtsey.inbeliefbackend.utils.DatabaseUtils;
import java.sql.Connection;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class MainForm extends javax.swing.JFrame {
    private LoginForm logInForm = null;
    private MyFriendsForm myFriendsForm = null;
    private FriendsSearchForm myFriendsSearchForm = null;
    private MyEventsForm myEventsForm = null;
    private CreateEventForm createEventForm = null;
    private EventsSearchForm eventsSearchForm = null;
    private MyFriendsEventsForm myFriendsEventsForm = null;

    public MainForm(int userId) {
        initComponents();
        
        setUserProfileData();
    }

    public void setUserProfileData() {
        try {
            Connection connection = DatabaseConnectionManager.getConnection();
        
            UserProfileData userProfileData = DatabaseUtils.getUserProfileData(connection, InBeliefBackend.getInstance().getLoggedUserId());
            
            this.setTitle(userProfileData.getName() + " " + userProfileData.getLastName());
            
            this.firstNameTextField.setText(userProfileData.getName());
            this.lastNameTextField.setText(userProfileData.getLastName());
            this.religionComboBox.setSelectedIndex(userProfileData.getReligion() - 1);
            this.ageTextField.setText(String.valueOf(userProfileData.getAge()));
            
            if(userProfileData.getSex().equals(Sex.MALE))
                this.maleRadioButton.setSelected(true);
            else
                this.femaleRadioButton.setSelected(true);
            
            this.cityTextField.setText(userProfileData.getTown());
            this.phoneTextField.setText(userProfileData.getPhone());
            this.emailTextField.setText(userProfileData.getEmail());
            this.aboutTextArea.setText(userProfileData.getDescription());
        }
        catch (NoSuchUserException e) {
            System.out.println("NoSuchUserException: " + e.getMessage());
        }
        catch(SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    public void editUserProfileData() {
        try {
            String name = firstNameTextField.getText();
            String lastname = lastNameTextField.getText();
            Sex sex;
            
            if(maleRadioButton.isSelected()) { 
                sex = Sex.MALE;
            }
            else {
                sex = Sex.FEMALE;
            }
            
            int religionId = religionComboBox.getSelectedIndex() + 1;
            int age = Integer.valueOf(ageTextField.getText());
            String phone = phoneTextField.getText();
            String city = cityTextField.getText();
            String email = emailTextField.getText();
            String description = aboutTextArea.getText();

            UserProfileData userProfileData = new UserProfileData(name, lastname, sex, religionId, age, phone, city, email, description);

            boolean isUpdated = InBeliefBackend.getInstance().changeUserProfileData(InBeliefBackend.getInstance().getLoggedUserId(), userProfileData);
            
            if(isUpdated)
                showInfoMessage("Your profile was updated successfully!");
            else
                showWarningMessage("Your profile was not updated!");
                
            System.out.println("isRegisteredSuccessfully: " + isUpdated);
        }
        catch (NoSuchUserException e) {
            showWarningMessage("Error!");
            System.out.println("NoSuchUserException: " + e.getMessage());
        }
    }
    
    public boolean checkUserProfileDataFields() {
        try {
            if( firstNameTextField.getText().isEmpty() ||
                lastNameTextField.getText().isEmpty() ||
                ageTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || 
                emailTextField.getText().isEmpty() ||
                cityTextField.getText().isEmpty() ||
                aboutTextArea.getText().isEmpty() )
            {
                showWarningMessage("All fields must be completed!");
                return false;
            }
            else if (Integer.parseInt(ageTextField.getText().toString()) <= 0 || Integer.parseInt(ageTextField.getText().toString()) > 100) {
                showWarningMessage("Enter a valid age!");
                return false;
            }
            else
                return true;
        }
        catch(NumberFormatException e) {
            showWarningMessage("Enter a valid age!");
            return false;
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
        personalPanel = new javax.swing.JPanel();
        firstNameLabel = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        surnameLabel = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        religionComboBox = new javax.swing.JComboBox();
        sexLabel = new javax.swing.JLabel();
        maritalStatusLabel = new javax.swing.JLabel();
        maleRadioButton = new javax.swing.JRadioButton();
        femaleRadioButton = new javax.swing.JRadioButton();
        ageLabel = new javax.swing.JLabel();
        ageTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        phoneTextField = new javax.swing.JTextField();
        emailTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        aboutTextArea = new javax.swing.JTextArea();
        editUserProfileButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        myFriendsSearchButton = new javax.swing.JButton();
        myFriendsButton = new javax.swing.JButton();
        eventsSearchButton = new javax.swing.JButton();
        createEventButton = new javax.swing.JButton();
        myEventsButton = new javax.swing.JButton();
        myFriendsEventsButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        personalPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        firstNameLabel.setText("Name:"); // NOI18N

        surnameLabel.setText("Sirname:"); // NOI18N

        religionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Buddhism", "Daoism", "Catholicism", "Confucianism", "Hinduism", "Islam", "Judaism", "Pastafarianism", "Orthodox Christianity", "Protestantism", "Secular humanism" }));

        sexLabel.setText("Sex:"); // NOI18N

        maritalStatusLabel.setText("Religion:"); // NOI18N

        buttonGroup1.add(maleRadioButton);
        maleRadioButton.setSelected(true);
        maleRadioButton.setText("male"); // NOI18N

        buttonGroup1.add(femaleRadioButton);
        femaleRadioButton.setText("female"); // NOI18N

        ageLabel.setText("Age:"); // NOI18N

        jLabel1.setText("Phone:");

        jLabel2.setText("E-mail:");

        jLabel3.setText("About me:");

        aboutTextArea.setColumns(20);
        aboutTextArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        aboutTextArea.setRows(5);
        jScrollPane1.setViewportView(aboutTextArea);

        editUserProfileButton.setText("Save changes");
        editUserProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editUserProfileButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("My profile");

        jLabel7.setText("City:");

        javax.swing.GroupLayout personalPanelLayout = new javax.swing.GroupLayout(personalPanel);
        personalPanel.setLayout(personalPanelLayout);
        personalPanelLayout.setHorizontalGroup(
            personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(116, 116, 116))
            .addGroup(personalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addComponent(sexLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(personalPanelLayout.createSequentialGroup()
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstNameLabel)
                            .addComponent(surnameLabel)
                            .addComponent(maritalStatusLabel)
                            .addComponent(ageLabel))
                        .addGap(12, 12, 12)
                        .addComponent(maleRadioButton)
                        .addGap(10, 10, 10)
                        .addComponent(femaleRadioButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalPanelLayout.createSequentialGroup()
                        .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(personalPanelLayout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, personalPanelLayout.createSequentialGroup()
                                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel7))
                                .addGap(20, 20, 20)
                                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phoneTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(emailTextField)
                                    .addComponent(cityTextField)
                                    .addComponent(ageTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(religionComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lastNameTextField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(firstNameTextField)))
                            .addGroup(personalPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editUserProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))))
        );
        personalPanelLayout.setVerticalGroup(
            personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, personalPanelLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameLabel)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameLabel)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(maritalStatusLabel)
                    .addComponent(religionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ageLabel)
                    .addComponent(ageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sexLabel)
                    .addComponent(femaleRadioButton)
                    .addComponent(maleRadioButton))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(personalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(editUserProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        myFriendsSearchButton.setText("<html>\n<center>Brothers and sisters search</center>");
        myFriendsSearchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        myFriendsSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myFriendsSearchButtonActionPerformed(evt);
            }
        });

        myFriendsButton.setText("<html><center>My brothers and sisters</center>");
        myFriendsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myFriendsButtonActionPerformed(evt);
            }
        });

        eventsSearchButton.setText("Events search");
        eventsSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventsSearchButtonActionPerformed(evt);
            }
        });

        createEventButton.setText("Create event");
        createEventButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createEventButtonActionPerformed(evt);
            }
        });

        myEventsButton.setText("<html><center>My events</center>");
        myEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myEventsButtonActionPerformed(evt);
            }
        });

        myFriendsEventsButton.setText("<html><center>Brother's and sister's<br> events</center>");
        myFriendsEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myFriendsEventsButtonActionPerformed(evt);
            }
        });

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Menu");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(myFriendsEventsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                    .addComponent(myFriendsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(myFriendsSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(myEventsButton)
                    .addComponent(createEventButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eventsSearchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(42, 42, 42))
                    .addComponent(exitButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(myFriendsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(myFriendsSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(myEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(createEventButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(eventsSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(myFriendsEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(exitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(personalPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(personalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void myFriendsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myFriendsButtonActionPerformed
        myFriendsForm = new MyFriendsForm(this, true);
        myFriendsForm.setVisible(true);
    }//GEN-LAST:event_myFriendsButtonActionPerformed

    private void myFriendsSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myFriendsSearchButtonActionPerformed
        myFriendsSearchForm = new FriendsSearchForm(this, true);
        myFriendsSearchForm.setVisible(true);
    }//GEN-LAST:event_myFriendsSearchButtonActionPerformed

    private void myEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myEventsButtonActionPerformed
        myEventsForm = new MyEventsForm(this, true);
        myEventsForm.setVisible(true);
    }//GEN-LAST:event_myEventsButtonActionPerformed

    private void createEventButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createEventButtonActionPerformed
        createEventForm = new CreateEventForm(this, true);
        createEventForm.setVisible(true);
    }//GEN-LAST:event_createEventButtonActionPerformed

    private void eventsSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventsSearchButtonActionPerformed
        eventsSearchForm = new EventsSearchForm(this, true);
        eventsSearchForm.setVisible(true);
    }//GEN-LAST:event_eventsSearchButtonActionPerformed

    private void myFriendsEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myFriendsEventsButtonActionPerformed
        myFriendsEventsForm = new MyFriendsEventsForm(this, true);
        myFriendsEventsForm.setVisible(true);
    }//GEN-LAST:event_myFriendsEventsButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        InBeliefBackend.getInstance().logout();
        
        this.dispose();
        
        logInForm = new LoginForm();
        logInForm.setVisible(true);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void editUserProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editUserProfileButtonActionPerformed
        if(checkUserProfileDataFields())
            editUserProfileData();
    }//GEN-LAST:event_editUserProfileButtonActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            //private String string;
            private int userId;
            
            public void run() {
                new MainForm(userId).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea aboutTextArea;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JTextField ageTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JButton createEventButton;
    private javax.swing.JButton editUserProfileButton;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JButton eventsSearchButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JRadioButton femaleRadioButton;
    private javax.swing.JLabel firstNameLabel;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JRadioButton maleRadioButton;
    private javax.swing.JLabel maritalStatusLabel;
    private javax.swing.JButton myEventsButton;
    private javax.swing.JButton myFriendsButton;
    private javax.swing.JButton myFriendsEventsButton;
    private javax.swing.JButton myFriendsSearchButton;
    private javax.swing.JPanel personalPanel;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JComboBox religionComboBox;
    private javax.swing.JLabel sexLabel;
    private javax.swing.JLabel surnameLabel;
    // End of variables declaration//GEN-END:variables
}
